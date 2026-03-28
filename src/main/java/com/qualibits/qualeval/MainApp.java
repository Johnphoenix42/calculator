package com.qualibits.qualeval;

import com.qualibits.qualeval.buttons.TermButton;
import com.qualibits.qualeval.mode.ModeModel;
import com.qualibits.qualeval.mode.ModeView;
import com.qualibits.qualeval.operator.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

    public static final String APP_NAME = "J Eval";
    public static final Background ROOT_BACKGROUND = new Background(new BackgroundFill(
            new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.color(0.1,0.1, 0.1)),
                    new Stop(1, Color.color(0.18, 0.2, 0.18))),
                null, null)
            );

    private final StackPane rootPane;
    private final TextField expressionScreen;
    private final TextField computeScreen;
    private final OverlayPane overlayPane;

    private final ExpressionParser expressionParser;
    private final LinkedList<Term> expressionQueue;
    private final TermsLibrary<Term> termsLibrary;
    private final LinkedList<ExecutionStackEntry> executionStack;

    ModeModel modeData;

    public MainApp() {
        rootPane = new StackPane();
        computeScreen = new TextField();
        expressionScreen = new TextField();
        expressionScreen.setEditable(false);
        expressionScreen.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.NONE, new CornerRadii(5), BorderStroke.DEFAULT_WIDTHS)));
        overlayPane = new OverlayPane(rootPane);
        expressionQueue = new LinkedList<>();
        termsLibrary = new TermsLibrary<>();
        executionStack = new LinkedList<>();

        expressionParser = new ExpressionParser(expressionQueue);
        ExpressionParser.setExecutionMemory(executionStack);
        modeData = new ModeModel();

        TermButton.setComputeScreen(computeScreen);
    }

    LinkedList<TermButton<? extends Term>> getCalcButtons(int col, int row) {
        LinkedList<TermButton<?>> buttonList = new LinkedList<>();
        buttonList.add(new TermButton<>(ButtonName.OPEN_PARENTHESIS.getName(), event -> {
            ParenthesisOperator parenthesisOperator = (ParenthesisOperator) termsLibrary.getTL().get(ButtonName.OPEN_PARENTHESIS);
            expressionScreen.setText(printExpressionQueue(parenthesisOperator));
        }, termsLibrary.getTL().get(ButtonName.OPEN_PARENTHESIS), col, row - 2));
        buttonList.add(new TermButton<>(ButtonName.CLOSE_PARENTHESIS.getName(), event -> {
            ParenthesisOperator parenthesisOperator = (ParenthesisOperator) termsLibrary.getTL().get(ButtonName.CLOSE_PARENTHESIS);
            expressionScreen.setText(printExpressionQueue(parenthesisOperator));
        }, termsLibrary.getTL().get(ButtonName.CLOSE_PARENTHESIS), 1 + col, row - 2));
        buttonList.add(new TermButton<>(ButtonName.SINH.getName(), event -> {
            TrigOperator trigOperator = (TrigOperator) termsLibrary.getTL().get(ButtonName.SINH);
            trigOperator.setModeData(modeData);
            expressionScreen.setText(printExpressionQueue(trigOperator));
        }, termsLibrary.getTL().get(ButtonName.SINH), col + 2, row - 2));
        buttonList.add(new TermButton<>(ButtonName.COSH.getName(), event -> {
            TrigOperator trigOperator = (TrigOperator) termsLibrary.getTL().get(ButtonName.COSH);
            trigOperator.setModeData(modeData);
            expressionScreen.setText(printExpressionQueue(trigOperator));
        }, termsLibrary.getTL().get(ButtonName.COSH), col + 3, row-2));
        buttonList.add(new TermButton<>(ButtonName.TANH.getName(), event -> {
            TrigOperator trigOperator = (TrigOperator) termsLibrary.getTL().get(ButtonName.TANH);
            trigOperator.setModeData(modeData);
            expressionScreen.setText(printExpressionQueue(trigOperator));
        }, termsLibrary.getTL().get(ButtonName.TANH), col + 4, row-2));

        buttonList.add(new TermButton<>(ButtonName.HYPOTENUSE.getName(), event -> {
            HypotenuseOperator hypotenuseOperator = (HypotenuseOperator) termsLibrary.getTL().get(ButtonName.HYPOTENUSE);
            expressionScreen.setText(printExpressionQueue(hypotenuseOperator));
        }, termsLibrary.getTL().get(ButtonName.HYPOTENUSE), col, row - 1));
        buttonList.add(new TermButton<>(ButtonName.FACTORIAL.getName(), event -> {
            FactorialOperator factorial = (FactorialOperator) termsLibrary.getTL().get(ButtonName.FACTORIAL);
            expressionScreen.setText(printExpressionQueue(factorial));
        }, termsLibrary.getTL().get(ButtonName.FACTORIAL), col + 1, row - 1));
        buttonList.add(new TermButton<>(ButtonName.ARC_SIN.getName(), event -> {
            TrigOperator trigOperator = (TrigOperator) termsLibrary.getTL().get(ButtonName.ARC_SIN);
            trigOperator.setModeData(modeData);
            expressionScreen.setText(printExpressionQueue(trigOperator));
        }, termsLibrary.getTL().get(ButtonName.ARC_SIN), col + 2, row-1));
        buttonList.add(new TermButton<>(ButtonName.ARC_COS.getName(), event -> {
            TrigOperator trigOperator = (TrigOperator) termsLibrary.getTL().get(ButtonName.ARC_COS);
            trigOperator.setModeData(modeData);
            expressionScreen.setText(printExpressionQueue(trigOperator));
        }, termsLibrary.getTL().get(ButtonName.ARC_COS), col + 3, row-1));
        buttonList.add(new TermButton<>(ButtonName.ARC_TAN.getName(), event -> {
            TrigOperator trigOperator = (TrigOperator) termsLibrary.getTL().get(ButtonName.ARC_TAN);
            trigOperator.setModeData(modeData);
            expressionScreen.setText(printExpressionQueue(trigOperator));
        }, termsLibrary.getTL().get(ButtonName.ARC_TAN), 4 + col, row - 1));
        buttonList.add(new TermButton<>(ButtonName.SIN.getName(), event -> {
            TrigOperator trigOperator = (TrigOperator) termsLibrary.getTL().get(ButtonName.SIN);
            trigOperator.setModeData(modeData);
            expressionScreen.setText(printExpressionQueue(trigOperator));
        }, termsLibrary.getTL().get(ButtonName.SIN), col + 2, row));
        buttonList.add(new TermButton<>(ButtonName.COS.getName(), event -> {
            TrigOperator trigOperator = (TrigOperator) termsLibrary.getTL().get(ButtonName.COS);
            trigOperator.setModeData(modeData);
            expressionScreen.setText(printExpressionQueue(trigOperator));
        }, termsLibrary.getTL().get(ButtonName.COS), col + 3, row));
        buttonList.add(new TermButton<>(ButtonName.TAN.getName(), event -> {
            TrigOperator trigOperator = (TrigOperator) termsLibrary.getTL().get(ButtonName.TAN);
            trigOperator.setModeData(modeData);
            expressionScreen.setText(printExpressionQueue(trigOperator));
        }, termsLibrary.getTL().get(ButtonName.TAN), col + 4, row));

        buttonList.add(new TermButton<>(ButtonName.X_POWER_Y.getName(), event -> {
            ExponentOperator xPowerY = (ExponentOperator) termsLibrary.getTL().get(ButtonName.X_POWER_Y);
            expressionScreen.setText(printExpressionQueue(xPowerY));
        }, termsLibrary.getTL().get(ButtonName.X_POWER_Y), col, row));
        buttonList.add(new TermButton<>(ButtonName.INVERSE.getName(), event -> {
            expressionScreen.setText(printExpressionQueue(termsLibrary.getTL().get(ButtonName.INVERSE)));
        }, termsLibrary.getTL().get(ButtonName.INVERSE), col + 1, row));

        buttonList.add(new TermButton<>(ButtonName.SQUARE.getName(), event -> {
            Operator square = (Operator) termsLibrary.getTL().get(ButtonName.SQUARE);
            expressionScreen.setText(printExpressionQueue(square));
        }, termsLibrary.getTL().get(ButtonName.SQUARE), col, row + 1));
        buttonList.add(new TermButton<>(ButtonName.PI.getName(), event -> {
            Operand pi = (Operand) termsLibrary.getTL().get(ButtonName.PI);
            expressionScreen.setText(printExpressionQueue(pi));
            computeScreen.setText(pi.toString());
        }, termsLibrary.getTL().get(ButtonName.PI), col + 1, row + 1));
        buttonList.add(new TermButton<>(ButtonName.EULER.getName(), event -> {
            Operand euler = (Operand) termsLibrary.getTL().get(ButtonName.EULER);
            expressionScreen.setText(printExpressionQueue(euler));
            computeScreen.setText(euler.toString());
        }, termsLibrary.getTL().get(ButtonName.EULER), 2 + col, row + 1));
        buttonList.add(new TermButton<>(ButtonName.MODULO.getName(), event -> {
            Operator mod = (Operator) termsLibrary.getTL().get(ButtonName.MODULO);
            expressionScreen.setText(printExpressionQueue(mod));
        }, termsLibrary.getTL().get(ButtonName.MODULO), 3 + col, row + 1));
        buttonList.add(new TermButton<>(ButtonName.DIVISION.getName(), event -> {
            Operator division = (Operator) termsLibrary.getTL().get(ButtonName.DIVISION);
            expressionScreen.setText(printExpressionQueue(division));
        }, termsLibrary.getTL().get(ButtonName.DIVISION), 4 + col, row + 1));

        buttonList.add(new TermButton<>(ButtonName.SQUARE_ROOT.getName(), event -> {
            Operator sqrt = (Operator) termsLibrary.getTL().get(ButtonName.SQUARE_ROOT);
            expressionScreen.setText(printExpressionQueue(sqrt));
        }, termsLibrary.getTL().get(ButtonName.SQUARE_ROOT), col, row + 2));
        buttonList.add(new TermButton<>(ButtonName.TEN_POWER_X.getName(), event -> {
            Operator tenPowerX = (Operator) termsLibrary.getTL().get(ButtonName.TEN_POWER_X);
            expressionScreen.setText(printExpressionQueue(tenPowerX));
        }, termsLibrary.getTL().get(ButtonName.TEN_POWER_X), col, row + 3));

        buttonList.add(new TermButton<>(ButtonName.LOG.getName(), event -> {
            Operator log = (Operator) termsLibrary.getTL().get(ButtonName.LOG);
            expressionScreen.setText(printExpressionQueue(log));
        }, termsLibrary.getTL().get(ButtonName.LOG), col, row + 4));
        buttonList.add(new TermButton<>(ButtonName.LN.getName(), event -> {
            Operator ln = (Operator) termsLibrary.getTL().get(ButtonName.LN);
            expressionScreen.setText(printExpressionQueue(ln));
        }, termsLibrary.getTL().get(ButtonName.LN), col, row + 5));


        buttonList.add(new TermButton<>(ButtonName.SEVEN.getName(), termsLibrary.getTL().get(ButtonName.SEVEN), 1 + col, row + 2));
        buttonList.add(new TermButton<>(ButtonName.EIGHT.getName(), termsLibrary.getTL().get(ButtonName.EIGHT), 2 + col, row + 2));
        buttonList.add(new TermButton<>(ButtonName.NINE.getName(), termsLibrary.getTL().get(ButtonName.NINE), 3 + col, row + 2));
        buttonList.add(new TermButton<>(ButtonName.FOUR.getName(), termsLibrary.getTL().get(ButtonName.FOUR), 1 + col, row + 3));
        buttonList.add(new TermButton<>(ButtonName.FIVE.getName(), termsLibrary.getTL().get(ButtonName.FIVE), 2 + col, row + 3));
        buttonList.add(new TermButton<>(ButtonName.SIX.getName(), termsLibrary.getTL().get(ButtonName.SIX), 3 + col, row + 3));
        buttonList.add(new TermButton<>(ButtonName.ONE.getName(), termsLibrary.getTL().get(ButtonName.ONE), 1 + col, row + 4));
        buttonList.add(new TermButton<>(ButtonName.TWO.getName(), termsLibrary.getTL().get(ButtonName.TWO), 2 + col, row + 4));
        buttonList.add(new TermButton<>(ButtonName.THREE.getName(), termsLibrary.getTL().get(ButtonName.THREE), 3 + col, row + 4));
        buttonList.add(new TermButton<>(ButtonName.POINT.getName(), termsLibrary.getTL().get(ButtonName.POINT), 1 + col, row + 5));
        buttonList.add(new TermButton<>(ButtonName.ZERO.getName(), termsLibrary.getTL().get(ButtonName.ZERO), 2 + col, row + 5));
        buttonList.add(new TermButton<>(ButtonName.ANS.getName(), event -> {
            ExecutionStackEntry lastEntry = executionStack.peek();
            if (lastEntry == null) return;
            Operand lastAnsTerm = new Operand(lastEntry.answer().getValue());
            computeScreen.setText(lastAnsTerm.toString());
            printExpressionQueue(lastAnsTerm);
        }, termsLibrary.getTL().get(ButtonName.ANS), 3 + col, row + 5));

        buttonList.add(new TermButton<>(ButtonName.MULTIPLICATION.getName(), event -> {
            MultiplicationOperator product = (MultiplicationOperator) termsLibrary.getTL().get(ButtonName.MULTIPLICATION);
            expressionScreen.setText(printExpressionQueue(product));
        }, termsLibrary.getTL().get(ButtonName.MULTIPLICATION), 4 + col, row + 2));
        buttonList.add(new TermButton<>(ButtonName.SUBTRACTION.getName(), event -> {
            SubtractionOperator minus = (SubtractionOperator) termsLibrary.getTL().get(ButtonName.SUBTRACTION);
            expressionScreen.setText(printExpressionQueue(minus));
        }, termsLibrary.getTL().get(ButtonName.SUBTRACTION), 4 + col, row + 3));
        buttonList.add(new TermButton<>(ButtonName.ADDITION.getName(), event -> {
            AdditionOperator add = (AdditionOperator) termsLibrary.getTL().get(ButtonName.ADDITION);
            expressionScreen.setText(printExpressionQueue(add));
        }, termsLibrary.getTL().get(ButtonName.ADDITION), 4 + col, row + 4));

        buttonList.add(new TermButton<>("=", event -> {
            expressionScreen.setText(printExpressionQueue(null));
            Operand answer = expressionParser.evaluateExpressionQueue();
            ModeModel.AnswerRadix radixMode = modeData.getAnswerRadix();
            String answerString = answer.toString();
            executionStack.push(new ExecutionStackEntry(expressionQueue, answer));
            switch (radixMode) {
                case BINARY: computeScreen.setText(Operand.convertToBinary(answer.getValue(), 6));
                break;
                case OCTAL: computeScreen.setText(Operand.convertToOctal(answer.getValue(), 6));
                    break;
                case HEXADECIMAL: computeScreen.setText(Operand.convertToHexadecimal(answer.getValue(), 6));
                break;
                default: computeScreen.setText(answerString);
            }
            expressionQueue.clear();
            //executionStack.push(answerString);
        }, null, 4 + col, row + 5));
        return buttonList;
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

    private LinkedList<TermButton<?>> createControlButton() {
        LinkedList<TermButton<?>> controlButtons = new LinkedList<>();
        ModeView modeView = new ModeView(overlayPane, modeData);
        ModeView.setModeData(modeData);
        overlayPane.setView(modeView);

        TermButton<?> modeButton = new TermButton<>("Mode", e -> {
            overlayPane.show();
            overlayPane.addCloseButton();
            rootPane.getChildren().add(overlayPane);
        }, null, 0, 3);

        TermButton<?> clearButton = new TermButton<>("C", event -> {
            PartialOperand.setStringValue("");
            expressionScreen.setText("");
            computeScreen.setText("0");
            expressionQueue.clear();
        }, null, 3, 3);

        TermButton<?> backSpaceButton = new TermButton<>("X", event -> {
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
        }, null, 4, 3);

        controlButtons.add(modeButton);
        controlButtons.add(clearButton);
        controlButtons.add(backSpaceButton);

        return controlButtons;
    }

    public GridPane setupGrid() {
        GridPane gridPane = new GridPane();
        ColumnConstraints column1Constraints = new ColumnConstraints(30, 45, 60, Priority.ALWAYS, HPos.LEFT, false);
        column1Constraints.setFillWidth(true);
        for (int i = 0; i < 5; ++i) {
            ColumnConstraints columnConstraints = new ColumnConstraints(30, 45, 60, Priority.ALWAYS, HPos.LEFT, false);
            columnConstraints.setFillWidth(true);
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        for(int i = 0; i < 11; ++i) {
            RowConstraints rowConstraints = new RowConstraints(30, 40, 60);
            rowConstraints.setFillHeight(true);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        expressionScreen.setMaxWidth(Double.MAX_VALUE);
        expressionScreen.setAlignment(Pos.CENTER_RIGHT);
        expressionScreen.setBackground(new Background(new BackgroundFill(Color.gray(0.7), null, null)));
        gridPane.add(expressionScreen, 0, 0, 5, 1);

        GridPane modeScreenDisplayGrid = new GridPane();
        modeScreenDisplayGrid.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        Label angleLabel = new Label(modeData.getAngleMode().name().substring(0, 3));
        angleLabel.setFont(Font.font(10));
        modeScreenDisplayGrid.add(angleLabel, 0, 0);

        computeScreen.setEditable(false);
        computeScreen.setEffect(new Glow(0));
        computeScreen.setFont(new Font("Arial", 18));
        computeScreen.setPrefHeight(40);
        computeScreen.setMaxHeight(60);
        computeScreen.setAlignment(Pos.CENTER_RIGHT);
        expressionScreen.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.NONE, new CornerRadii(5), BorderStroke.DEFAULT_WIDTHS)));

        VBox computeDisplayScreen = new VBox(modeScreenDisplayGrid, computeScreen);
        GridPane.setFillHeight(computeDisplayScreen, true);
        GridPane.setVgrow(computeDisplayScreen, Priority.ALWAYS);
        gridPane.add(computeDisplayScreen, 0, 1, 5, 1);

        LinkedList<TermButton<?>> controlButtons = createControlButton();
        for (int i = 0; i < controlButtons.size(); ++i){
            gridPane.add(controlButtons.get(i), controlButtons.get(i).getColumn(), (i+10)/5);
        }

        for (TermButton<? extends Term> calculatorButton : getCalcButtons(0, 5)) {
            gridPane.add(calculatorButton, calculatorButton.getColumn(), calculatorButton.getRow(),
                    calculatorButton.getColSpan(), calculatorButton.getRowSpan());
        }
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(2);
        gridPane.setHgap(2);
        return gridPane;
    }

    public void start(Stage primaryStage) {
        rootPane.setBackground(ROOT_BACKGROUND);
        rootPane.setAlignment(Pos.CENTER);
        rootPane.getChildren().add(setupGrid());
        Scene mainScene = new Scene(rootPane, 350, 480, Color.GRAY);

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
