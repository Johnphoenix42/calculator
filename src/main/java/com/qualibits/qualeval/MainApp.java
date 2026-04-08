package com.qualibits.qualeval;

import com.qualibits.qualeval.buttons.BaseButton;
import com.qualibits.qualeval.buttons.ButtonName;
import com.qualibits.qualeval.buttons.TermButton;
import com.qualibits.qualeval.exec.ExecutionStackEntry;
import com.qualibits.qualeval.exec.ExpressionParser;
import com.qualibits.qualeval.mode.ModeModel;
import com.qualibits.qualeval.mode.ModeView;
import com.qualibits.qualeval.term.Operand;
import com.qualibits.qualeval.term.OperationType;
import com.qualibits.qualeval.term.PartialOperand;
import com.qualibits.qualeval.term.Term;
import com.qualibits.qualeval.term.operator.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class MainApp extends Application {

    public static final String APP_NAME = "Qual-Eval";
    public static final Background ROOT_BACKGROUND = new Background(new BackgroundFill(
            new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.color(0.1,0.1, 0.1)),
                    new Stop(1, Color.color(0.18, 0.2, 0.18))),
                null, null)
            );
    private static final int MAX_EXECUTION_STACK_SIZE = 32; // You could allow the user to set this from a settings menu in the future

    private final VBox rootPane;
    private final StackPane appStackPane;
    private final TextField expressionScreen;
    private final TextField computeScreen;
    private final OverlayPane overlayPane;

    private final ExpressionParser expressionParser;
    private final LinkedList<Term> expressionQueue;
    private final LinkedList<ExecutionStackEntry> executionStack;

    ModeModel modeData;

    private final TrigOperator sinHOperator, cosHOperator, tanHOperator, atanOperator, asinOperator, acosOperator, sinOperator, cosOperator, tanOperator;
    private final HypotenuseOperator hypotenuseOperator;
    private final ExponentOperator xPowerY, tenPowerX;
    private final InverseOperator inverseOperator;
    private final FactorialOperator factorialOperator;
    private final SquareOperator squareOperator;
    private final RootOperator rootOperator;
    private final ModulusOperator modulusOperator;
    private final MultiplicationOperator multiplicationOperator;
    private final DivisionOperator divisionOperator;
    private final AdditionOperator additionOperator;
    private final SubtractionOperator subtractionOperator;
    private final LogarithmOperator logarithmicOperator;
    private final NaturalLogOperator naturalLogOperator;
    private final ParenthesisOperator openParenthesis, closeParenthesis;
    private final Operand piOperand, eulerOperand;
    private final PartialOperand sevenOperand, eightOperand, nineOperand, fourOperand, fiveOperand, sixOperand, oneOperand, twoOperand, threeOperand, zeroOperand;
    private final DecimalPointOperator decimalPointOperator;

    public MainApp() {
        rootPane = new VBox();
        appStackPane = new StackPane();
        computeScreen = new TextField();
        expressionScreen = new TextField();
        expressionScreen.setEditable(false);
        expressionScreen.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.NONE, new CornerRadii(5), BorderStroke.DEFAULT_WIDTHS)));
        overlayPane = new OverlayPane(appStackPane);
        expressionQueue = new LinkedList<>();
        executionStack = new LinkedList<>();

        expressionParser = new ExpressionParser(expressionQueue);
        ExpressionParser.setExecutionMemory(executionStack);
        modeData = new ModeModel();

        TermButton.setComputeScreen(computeScreen);
        sinHOperator = new TrigOperator(TrigOperator.TrigOperatorType.SINH);
        cosHOperator = new TrigOperator(TrigOperator.TrigOperatorType.COSH);
        tanHOperator = new TrigOperator(TrigOperator.TrigOperatorType.TANH);
        atanOperator = new TrigOperator(TrigOperator.TrigOperatorType.ARC_TAN);
        asinOperator = new TrigOperator(TrigOperator.TrigOperatorType.ARC_SIN);
        acosOperator = new TrigOperator(TrigOperator.TrigOperatorType.ARC_COS);
        sinOperator = new TrigOperator(TrigOperator.TrigOperatorType.SIN);
        cosOperator = new TrigOperator(TrigOperator.TrigOperatorType.COS);
        tanOperator = new TrigOperator(TrigOperator.TrigOperatorType.TAN);
        hypotenuseOperator = new HypotenuseOperator();
        xPowerY = new ExponentOperator();
        tenPowerX = new ExponentOperator(10D);
        inverseOperator = new InverseOperator();
        factorialOperator = new FactorialOperator();
        squareOperator = new SquareOperator();
        rootOperator = new RootOperator(2);
        modulusOperator = new ModulusOperator();
        multiplicationOperator = new MultiplicationOperator();
        divisionOperator = new DivisionOperator();
        additionOperator = new AdditionOperator();
        subtractionOperator = new SubtractionOperator();
        logarithmicOperator = new LogarithmOperator(10);
        naturalLogOperator = new NaturalLogOperator();
        openParenthesis = new ParenthesisOperator(true);
        closeParenthesis = new ParenthesisOperator(false);

        piOperand = new Operand(Math.PI, "⫪");
        eulerOperand = new Operand(Math.E, "e");
        sevenOperand = new PartialOperand("7");
        eightOperand = new PartialOperand("8");
        nineOperand = new PartialOperand("9");
        fourOperand = new PartialOperand("4");
        fiveOperand = new PartialOperand("5");
        sixOperand = new PartialOperand("6");
        oneOperand = new PartialOperand("1");
        twoOperand = new PartialOperand("2");
        threeOperand = new PartialOperand("3");
        zeroOperand = new PartialOperand("0");
        decimalPointOperator = new DecimalPointOperator();
    }

    LinkedList<BaseButton> getCalcButtons(int col, int row) {
        LinkedList<BaseButton> buttonList = new LinkedList<>();
        buttonList.add(new TermButton<>(ButtonName.OPEN_PARENTHESIS, event -> expressionScreen.setText(printExpressionQueue(openParenthesis)), openParenthesis, col, row - 2));
        buttonList.add(new TermButton<>(ButtonName.CLOSE_PARENTHESIS, event -> expressionScreen.setText(printExpressionQueue(closeParenthesis)), closeParenthesis, 1 + col, row - 2));
        buttonList.add(new TermButton<>(ButtonName.SINH, event -> handleTrigButtonsPressed(sinHOperator), sinHOperator, col + 2, row - 2));
        buttonList.add(new TermButton<>(ButtonName.COSH, event -> handleTrigButtonsPressed(cosHOperator), cosHOperator, col + 3, row-2));
        buttonList.add(new TermButton<>(ButtonName.TANH, event -> handleTrigButtonsPressed(tanHOperator), tanHOperator, col + 4, row-2));

        buttonList.add(new TermButton<>(ButtonName.HYPOTENUSE, event -> expressionScreen.setText(printExpressionQueue(hypotenuseOperator)), hypotenuseOperator, col, row - 1));
        buttonList.add(new TermButton<>(ButtonName.FACTORIAL, event -> expressionScreen.setText(printExpressionQueue(factorialOperator)), factorialOperator, col + 1, row - 1));
        buttonList.add(new TermButton<>(ButtonName.ARC_SIN, event -> handleTrigButtonsPressed(asinOperator), asinOperator, col + 2, row-1));
        buttonList.add(new TermButton<>(ButtonName.ARC_COS, event -> handleTrigButtonsPressed(acosOperator), acosOperator, col + 3, row-1));
        buttonList.add(new TermButton<>(ButtonName.ARC_TAN, event -> handleTrigButtonsPressed(atanOperator), atanOperator, 4 + col, row - 1));
        buttonList.add(new TermButton<>(ButtonName.SIN, event -> handleTrigButtonsPressed(sinOperator), sinOperator, col + 2, row));
        buttonList.add(new TermButton<>(ButtonName.COS, event -> handleTrigButtonsPressed(cosOperator), cosOperator, col + 3, row));
        buttonList.add(new TermButton<>(ButtonName.TAN, event -> handleTrigButtonsPressed(tanOperator), tanOperator, col + 4, row));

        buttonList.add(new TermButton<>(ButtonName.X_POWER_Y, event -> expressionScreen.setText(printExpressionQueue(xPowerY)), xPowerY, col, row));
        buttonList.add(new TermButton<>(ButtonName.INVERSE, event -> expressionScreen.setText(printExpressionQueue(inverseOperator)), inverseOperator, col + 1, row));

        buttonList.add(new TermButton<>(ButtonName.SQUARE, event -> expressionScreen.setText(printExpressionQueue(squareOperator)), squareOperator, col, row + 1));
        buttonList.add(new TermButton<>(ButtonName.PI, event -> {
            expressionScreen.setText(printExpressionQueue(piOperand));
            computeScreen.setText(piOperand.toString());
        }, piOperand, col + 1, row + 1));
        buttonList.add(new TermButton<>(ButtonName.EULER, event -> {
            expressionScreen.setText(printExpressionQueue(eulerOperand));
            computeScreen.setText(eulerOperand.toString());
        }, eulerOperand, 2 + col, row + 1));
        buttonList.add(new TermButton<>(ButtonName.MODULO, event -> expressionScreen.setText(printExpressionQueue(modulusOperator)), modulusOperator, 3 + col, row + 1));
        buttonList.add(new TermButton<>(ButtonName.DIVISION, event -> expressionScreen.setText(printExpressionQueue(divisionOperator)), divisionOperator, 4 + col, row + 1));

        buttonList.add(new TermButton<>(ButtonName.SQUARE_ROOT, event -> expressionScreen.setText(printExpressionQueue(rootOperator)), rootOperator, col, row + 2));
        buttonList.add(new TermButton<>(ButtonName.TEN_POWER_X, event -> expressionScreen.setText(printExpressionQueue(tenPowerX)), tenPowerX, col, row + 3));

        buttonList.add(new TermButton<>(ButtonName.LOG, event -> expressionScreen.setText(printExpressionQueue(logarithmicOperator)), logarithmicOperator, col, row + 4));
        buttonList.add(new TermButton<>(ButtonName.LN, event -> expressionScreen.setText(printExpressionQueue(naturalLogOperator)), naturalLogOperator, col, row + 5));
        buttonList.add(new TermButton<>(ButtonName.POINT, decimalPointOperator, 1 + col, row + 5));
        addOperandButtons(buttonList, col, row);

        buttonList.add(new BaseButton(ButtonName.ANS, this::handleRecallLastAnswer, 3 + col, row + 5));

        buttonList.add(new TermButton<>(ButtonName.MULTIPLICATION, event -> expressionScreen.setText(printExpressionQueue(multiplicationOperator)), multiplicationOperator, 4 + col, row + 2));
        buttonList.add(new TermButton<>(ButtonName.SUBTRACTION, event -> expressionScreen.setText(printExpressionQueue(subtractionOperator)), subtractionOperator, 4 + col, row + 3));
        buttonList.add(new TermButton<>(ButtonName.ADDITION, event -> expressionScreen.setText(printExpressionQueue(additionOperator)), additionOperator, 4 + col, row + 4));

        buttonList.add(new BaseButton(ButtonName.EQUALS, this::handleEqualsPressed, 4 + col, row + 5));
        return buttonList;
    }

    private void handleTrigButtonsPressed(TrigOperator trigOperator){
        trigOperator.setModeData(modeData);
        expressionScreen.setText(printExpressionQueue(trigOperator));
    }

    private void handleRecallLastAnswer(ActionEvent event) {
        ExecutionStackEntry lastEntry = executionStack.peek();
        if (lastEntry == null) return;
        Operand lastAnsTerm = new Operand(lastEntry.answer().getValue());
        computeScreen.setText(lastAnsTerm.toString());
        printExpressionQueue(lastAnsTerm);
    }

    private void addOperandButtons(LinkedList<BaseButton> buttonList, int col, int row) {
        buttonList.add(new TermButton<>(ButtonName.SEVEN, sevenOperand, 1 + col, row + 2));
        buttonList.add(new TermButton<>(ButtonName.EIGHT, eightOperand, 2 + col, row + 2));
        buttonList.add(new TermButton<>(ButtonName.NINE, nineOperand, 3 + col, row + 2));
        buttonList.add(new TermButton<>(ButtonName.FOUR, fourOperand, 1 + col, row + 3));
        buttonList.add(new TermButton<>(ButtonName.FIVE, fiveOperand, 2 + col, row + 3));
        buttonList.add(new TermButton<>(ButtonName.SIX, sixOperand, 3 + col, row + 3));
        buttonList.add(new TermButton<>(ButtonName.ONE, oneOperand, 1 + col, row + 4));
        buttonList.add(new TermButton<>(ButtonName.TWO, twoOperand, 2 + col, row + 4));
        buttonList.add(new TermButton<>(ButtonName.THREE, threeOperand, 3 + col, row + 4));
        buttonList.add(new TermButton<>(ButtonName.ZERO, zeroOperand, 2 + col, row + 5));
    }

    private void handleEqualsPressed(ActionEvent event){
        expressionScreen.setText(printExpressionQueue(null));
        Operand answer = new Operand(Double.NaN);
        try {
            answer = expressionParser.evaluateExpressionQueue();
            ModeModel.AnswerRadix radixMode = modeData.getAnswerRadix();
            String answerString = answer.toString();
            switch (radixMode) {
                case BINARY:
                    computeScreen.setText(Operand.convertToBinary(answer.getValue(), 6));
                    break;
                case OCTAL:
                    computeScreen.setText(Operand.convertToOctal(answer.getValue(), 6));
                    break;
                case HEXADECIMAL:
                    computeScreen.setText(Operand.convertToHexadecimal(answer.getValue(), 6));
                    break;
                default:
                    computeScreen.setText(answerString);
            }
        } catch (ArithmeticException ae) {
            computeScreen.setText("Error: " + ae.getMessage());
        } catch (NumberFormatException ne) {
            computeScreen.setText("Error: Incorrect Format");
        }
        if (executionStack.size() >= MAX_EXECUTION_STACK_SIZE) executionStack.removeFirst();
        executionStack.push(new ExecutionStackEntry(new LinkedList<>(expressionQueue), answer));
        expressionQueue.clear();
    }

    /**
     * Prints an expression unto a String object from the operation queue.
     * @return a String containing the expression
     */
    private String printExpressionQueue(Term token) {
        StringBuilder expressionString = new StringBuilder();
        if (!PartialOperand.getStringValue().isEmpty()) {
            Operand operand = new Operand(PartialOperand.getStringValue());
            normalizeExpression(operand);
            expressionQueue.addLast(operand);
        }

        if (token != null) {
            normalizeExpression(token);
            expressionQueue.addLast(token);
        }
        for (Term term : expressionQueue){
            expressionString.append(term.toString());
        }
        PartialOperand.setStringValue("");
        return expressionString.toString();
    }

    private void normalizeExpression(Term token){
        try {
            Term lastOperation = expressionQueue.getLast();
            if (lastOperation == null) return;
            boolean shouldAppendProductOperator = (lastOperation instanceof Operand
                    || (lastOperation instanceof ParenthesisOperator && !((ParenthesisOperator) lastOperation).isOpen()))
                    && token.getOperationType() != OperationType.BINARY;
            if (shouldAppendProductOperator) expressionQueue.addLast(new MultiplicationOperator());
        }catch (NoSuchElementException ne) {
            System.err.println(ne.getMessage());
        }
    }

    private LinkedList<BaseButton> createControlButton() {
        LinkedList<BaseButton> controlButtons = new LinkedList<>();
        ModeView modeView = new ModeView(overlayPane, modeData);
        ModeView.setModeData(modeData);

        BaseButton modeButton = new BaseButton(ButtonName.MODE, e -> {
            //overlayPane.setView(modeView);
            overlayPane.show(modeView);
            overlayPane.addCloseButton();
        }, 0, 3, 2, 1);

        BaseButton clearButton = new BaseButton(ButtonName.CLEAR, event -> {
            PartialOperand.setStringValue("");
            expressionScreen.setText("");
            computeScreen.setText("0");
            expressionQueue.clear();
        }, 3, 3);

        BaseButton backSpaceButton = new BaseButton(ButtonName.CURTAIL, event -> {
            if (!PartialOperand.getStringValue().isEmpty()) {
                String text = PartialOperand.getStringValue();
                PartialOperand.setStringValue(text.substring(0, text.length() - 1));
                computeScreen.setText(PartialOperand.getStringValue().isEmpty() ? "0" : PartialOperand.getStringValue());
            } else {
                if(!expressionQueue.isEmpty()) {
                    expressionQueue.removeLast();
                    expressionScreen.setText(printExpressionQueue(null));
                    computeScreen.setText("0");
                }
            }
        }, 4, 3);

        controlButtons.add(modeButton);
        controlButtons.add(clearButton);
        controlButtons.add(backSpaceButton);

        return controlButtons;
    }

    public GridPane setupGrid() {
        GridPane gridPane = new GridPane();
        setConstraints(gridPane);

        expressionScreen.setMaxWidth(Double.MAX_VALUE);
        expressionScreen.setAlignment(Pos.CENTER_RIGHT);
        expressionScreen.setBackground(new Background(new BackgroundFill(Color.gray(0.7), null, null)));
        gridPane.add(expressionScreen, 0, 0, 5, 1);

        gridPane.add(addDisplayScreen(), 0, 1, 5, 1);
        for (BaseButton calculatorButton : getCalcButtons(0, 5)) {
            gridPane.add(calculatorButton, calculatorButton.getColumn(), calculatorButton.getRow(),
                    calculatorButton.getColSpan(), calculatorButton.getRowSpan());
        }

        LinkedList<BaseButton> controlButtons = createControlButton();
        for (int i = 0; i < controlButtons.size(); ++i){
            gridPane.add(controlButtons.get(i), controlButtons.get(i).getColumn(), (i+10)/5);
        }

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(2);
        gridPane.setHgap(2);
        return gridPane;
    }

    private void setConstraints(GridPane gridPane){
        for (int i = 0; i < 5; ++i) {
            ColumnConstraints columnConstraints = new ColumnConstraints(30, 45, 60, Priority.ALWAYS, HPos.LEFT, false);
            columnConstraints.setFillWidth(true);
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        for(int i = 0; i < 11; ++i) {
            RowConstraints rowConstraints = new RowConstraints(30, 40, 70, Priority.ALWAYS, VPos.CENTER, true);
            gridPane.getRowConstraints().add(rowConstraints);
        }
        gridPane.getRowConstraints().get(1).setMinHeight(60);
        gridPane.getRowConstraints().get(1).setPrefHeight(70);
    }

    private VBox addDisplayScreen(){
        GridPane modeScreenDisplayGrid = new GridPane();
        modeScreenDisplayGrid.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        Label angleLabel = new Label(modeData.getAngleMode().name().substring(0, 3));
        angleLabel.setFont(Font.font(10));
        modeScreenDisplayGrid.add(angleLabel, 0, 0);

        computeScreen.setEditable(false);
        computeScreen.setEffect(new Glow(0));
        computeScreen.setFont(new Font("Arial", 18));
        computeScreen.setPrefHeight(45);
        computeScreen.setMaxHeight(60);
        computeScreen.setAlignment(Pos.CENTER_RIGHT);
        expressionScreen.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.NONE, new CornerRadii(5), BorderStroke.DEFAULT_WIDTHS)));

        return new VBox(modeScreenDisplayGrid, computeScreen);
    }

    private Menu createMenu(String menuName) {
        Label label = new Label(menuName);
        label.setTextFill(Color.WHITE);
        Menu menu = new Menu("", label);
        menu.setStyle("-fx-focus-color: green");
        return menu;
    }

    public void start(Stage primaryStage) {
        //String css = Objects.requireNonNull(getClass().getResource("/main.css")).toExternalForm();
        Menu[] appMenu = {createMenu("Settings"), createMenu("About")};
        MenuBar menuBar = new MenuBar(appMenu);
        menuBar.setBackground(new Background(new BackgroundFill(
                new LinearGradient(0, 1, 1, 1, true, CycleMethod.REFLECT,
                        new Stop(0, Color.NAVY), new Stop(0.5, Color.web("#333"))), null, null)));

        rootPane.getChildren().addAll(menuBar, appStackPane);
        rootPane.setBackground(ROOT_BACKGROUND);
        appStackPane.setAlignment(Pos.CENTER_LEFT);
        appStackPane.getChildren().add(setupGrid());
        Scene mainScene = new Scene(rootPane, 350, 500, Color.GRAY);
        //mainScene.getStylesheets().add(css);

        primaryStage.setScene(mainScene);
        primaryStage.setTitle(APP_NAME);
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.toFront();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
