package calculator;

import calculator.buttons.CalculatorButton;
import calculator.mode.ModeModel;
import calculator.mode.ModeView;
import calculator.mode.OverlayView;
import calculator.operator.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class CalculatorApp extends Application {

    public static final String APP_NAME = "Jounin Calculator";
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

    private final LinkedList<Term> operationQueue;
    private final TermsLibrary<Term> termsLibrary;
    private final LinkedList<String> executionMemory;

    ModeModel modeData;

    public CalculatorApp() {
        rootPane = new StackPane();
        computeScreen = new TextField();
        expressionScreen = new TextField();
        expressionScreen.setEditable(false);
        expressionScreen.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.NONE, new CornerRadii(5), BorderStroke.DEFAULT_WIDTHS)));
        overlayPane = new OverlayPane();
        operationQueue = new LinkedList<>();
        termsLibrary = new TermsLibrary<>();
        executionMemory = new LinkedList<>();

        modeData = new ModeModel();

        CalculatorButton.setComputeScreen(computeScreen);
        CalculatorButton.setExpressionScreen(expressionScreen);
    }

    LinkedList<CalculatorButton<? extends Term>> getCalcButtons(int col, int row) {
        LinkedList<CalculatorButton<?>> buttonList = new LinkedList<>();
        buttonList.add(new CalculatorButton<>(ButtonName.OPEN_PARENTHESIS.getName(), event -> {
            ParenthesisOperator parenthesisOperator = (ParenthesisOperator) termsLibrary.getTL().get(ButtonName.OPEN_PARENTHESIS);
            expressionScreen.setText(printOperationQueue(parenthesisOperator));
        }, termsLibrary.getTL().get(ButtonName.OPEN_PARENTHESIS), col, row - 1));
        buttonList.add(new CalculatorButton<>(ButtonName.CLOSE_PARENTHESIS.getName(), event -> {
            ParenthesisOperator parenthesisOperator = (ParenthesisOperator) termsLibrary.getTL().get(ButtonName.CLOSE_PARENTHESIS);
            expressionScreen.setText(printOperationQueue(parenthesisOperator));
        }, termsLibrary.getTL().get(ButtonName.CLOSE_PARENTHESIS), col + 1, row - 1));
        buttonList.add(new CalculatorButton<>(ButtonName.FACTORIAL.getName(), event -> {
            FactorialOperator factorial = (FactorialOperator) termsLibrary.getTL().get(ButtonName.FACTORIAL);
            expressionScreen.setText(printOperationQueue(factorial));
        }, termsLibrary.getTL().get(ButtonName.FACTORIAL), 2 + col, row - 1));
        buttonList.add(new CalculatorButton<>(ButtonName.SIN.getName(), event -> {
            TrigOperator trigOperator = (TrigOperator) termsLibrary.getTL().get(ButtonName.SIN);
            expressionScreen.setText(printOperationQueue(trigOperator));
        }, termsLibrary.getTL().get(ButtonName.SIN), col + 2, row));
        buttonList.add(new CalculatorButton<>(ButtonName.COS.getName(), event -> {
            TrigOperator trigOperator = (TrigOperator) termsLibrary.getTL().get(ButtonName.COS);
            expressionScreen.setText(printOperationQueue(trigOperator));
        }, termsLibrary.getTL().get(ButtonName.COS), col + 3, row));
        buttonList.add(new CalculatorButton<>(ButtonName.TAN.getName(), event -> {
            TrigOperator trigOperator = (TrigOperator) termsLibrary.getTL().get(ButtonName.TAN);
            expressionScreen.setText(printOperationQueue(trigOperator));
        }, termsLibrary.getTL().get(ButtonName.TAN), col + 4, row));

        buttonList.add(new CalculatorButton<>(ButtonName.X_POWER_Y.getName(), event -> {
            ExponentOperator xPowerY = (ExponentOperator) termsLibrary.getTL().get(ButtonName.X_POWER_Y);
            expressionScreen.setText(printOperationQueue(xPowerY));
        }, termsLibrary.getTL().get(ButtonName.X_POWER_Y), col, row));
        buttonList.add(new CalculatorButton<>(ButtonName.INVERSE.getName(), event -> {
            expressionScreen.setText(printOperationQueue(termsLibrary.getTL().get(ButtonName.INVERSE)));
        }, termsLibrary.getTL().get(ButtonName.INVERSE), col + 1, row));
        buttonList.add(new CalculatorButton<>("C", event -> {
            PartialOperand.setStringValue("");
            expressionScreen.setText("");
            computeScreen.setText("0");
            operationQueue.clear();
        }, null, 3 + col, row - 1));
        buttonList.add(new CalculatorButton<>("X", event -> {
            if (!PartialOperand.getStringValue().isEmpty()) {
                String text = PartialOperand.getStringValue();
                PartialOperand.setStringValue(text.substring(0, text.length() - 1));
                computeScreen.setText(PartialOperand.getStringValue().isEmpty() ? "0" : PartialOperand.getStringValue());
            } else {
                if(!operationQueue.isEmpty()) {
                    operationQueue.removeLast();
                    expressionScreen.setText(printOperationQueue(null));
                    computeScreen.setText("0");
                }
            }
        }, null, 4 + col, row -1));

        buttonList.add(new CalculatorButton<>(ButtonName.SQUARE.getName(), event -> {
            Operator square = (Operator) termsLibrary.getTL().get(ButtonName.SQUARE);
            expressionScreen.setText(printOperationQueue(square));
        }, termsLibrary.getTL().get(ButtonName.SQUARE), col, row + 1));
        buttonList.add(new CalculatorButton<>(ButtonName.PI.getName(), event -> {
            Operand pi = (Operand) termsLibrary.getTL().get(ButtonName.PI);
            expressionScreen.setText(printOperationQueue(pi));
            computeScreen.setText(pi.toString());
        }, termsLibrary.getTL().get(ButtonName.PI), col + 1, row + 1));
        buttonList.add(new CalculatorButton<>(ButtonName.EULER.getName(), event -> {
            Operand euler = (Operand) termsLibrary.getTL().get(ButtonName.EULER);
            expressionScreen.setText(printOperationQueue(euler));
            computeScreen.setText(euler.toString());
        }, termsLibrary.getTL().get(ButtonName.EULER), 2 + col, row + 1));
        buttonList.add(new CalculatorButton<>(ButtonName.MODULO.getName(), event -> {
            Operator mod = (Operator) termsLibrary.getTL().get(ButtonName.MODULO);
            expressionScreen.setText(printOperationQueue(mod));
        }, termsLibrary.getTL().get(ButtonName.MODULO), 3 + col, row + 1));
        buttonList.add(new CalculatorButton<>(ButtonName.DIVISION.getName(), event -> {
            Operator division = (Operator) termsLibrary.getTL().get(ButtonName.DIVISION);
            expressionScreen.setText(printOperationQueue(division));
        }, termsLibrary.getTL().get(ButtonName.DIVISION), 4 + col, row + 1));

        buttonList.add(new CalculatorButton<>(ButtonName.SQUARE_ROOT.getName(), event -> {
            Operator sqrt = (Operator) termsLibrary.getTL().get(ButtonName.SQUARE_ROOT);
            expressionScreen.setText(printOperationQueue(sqrt));
        }, termsLibrary.getTL().get(ButtonName.SQUARE_ROOT), col, row + 2));
        buttonList.add(new CalculatorButton<>(ButtonName.TEN_POWER_X.getName(), event -> {
            Operator tenPowerX = (Operator) termsLibrary.getTL().get(ButtonName.TEN_POWER_X);
            expressionScreen.setText(printOperationQueue(tenPowerX));
        }, termsLibrary.getTL().get(ButtonName.TEN_POWER_X), col, row + 3));

        buttonList.add(new CalculatorButton<>(ButtonName.LOG.getName(), event -> {
            Operator log = (Operator) termsLibrary.getTL().get(ButtonName.LOG);
            expressionScreen.setText(printOperationQueue(log));
        }, termsLibrary.getTL().get(ButtonName.LOG), col, row + 4));
        buttonList.add(new CalculatorButton<>(ButtonName.LN.getName(), event -> {
            Operator ln = (Operator) termsLibrary.getTL().get(ButtonName.LN);
            expressionScreen.setText(printOperationQueue(ln));
        }, termsLibrary.getTL().get(ButtonName.LN), col, row + 5));


        buttonList.add(new CalculatorButton<>(ButtonName.SEVEN.getName(), termsLibrary.getTL().get(ButtonName.SEVEN), 1 + col, row + 2));
        buttonList.add(new CalculatorButton<>(ButtonName.EIGHT.getName(), termsLibrary.getTL().get(ButtonName.EIGHT), 2 + col, row + 2));
        buttonList.add(new CalculatorButton<>(ButtonName.NINE.getName(), termsLibrary.getTL().get(ButtonName.NINE), 3 + col, row + 2));
        buttonList.add(new CalculatorButton<>(ButtonName.FOUR.getName(), termsLibrary.getTL().get(ButtonName.FOUR), 1 + col, row + 3));
        buttonList.add(new CalculatorButton<>(ButtonName.FIVE.getName(), termsLibrary.getTL().get(ButtonName.FIVE), 2 + col, row + 3));
        buttonList.add(new CalculatorButton<>(ButtonName.SIX.getName(), termsLibrary.getTL().get(ButtonName.SIX), 3 + col, row + 3));
        buttonList.add(new CalculatorButton<>(ButtonName.ONE.getName(), termsLibrary.getTL().get(ButtonName.ONE), 1 + col, row + 4));
        buttonList.add(new CalculatorButton<>(ButtonName.TWO.getName(), termsLibrary.getTL().get(ButtonName.TWO), 2 + col, row + 4));
        buttonList.add(new CalculatorButton<>(ButtonName.THREE.getName(), termsLibrary.getTL().get(ButtonName.THREE), 3 + col, row + 4));
        buttonList.add(new CalculatorButton<>(ButtonName.POINT.getName(), termsLibrary.getTL().get(ButtonName.POINT), 1 + col, row + 5));
        buttonList.add(new CalculatorButton<>(ButtonName.ZERO.getName(), termsLibrary.getTL().get(ButtonName.ZERO), 2 + col, row + 5));
        buttonList.add(new CalculatorButton<>(ButtonName.ANS.getName(), event -> {
            String lastAns = executionMemory.peek();
            if (lastAns == null) return;
            Operand lastAnsTerm = new Operand(lastAns);
            computeScreen.setText(lastAnsTerm.toString());
            printOperationQueue(lastAnsTerm);
        }, termsLibrary.getTL().get(ButtonName.ANS), 3 + col, row + 5));

        buttonList.add(new CalculatorButton<>(ButtonName.MULTIPLICATION.getName(), event -> {
            MultiplicationOperator product = (MultiplicationOperator) termsLibrary.getTL().get(ButtonName.MULTIPLICATION);
            expressionScreen.setText(printOperationQueue(product));
        }, termsLibrary.getTL().get(ButtonName.MULTIPLICATION), 4 + col, row + 2));
        buttonList.add(new CalculatorButton<>(ButtonName.SUBTRACTION.getName(), event -> {
            SubtractionOperator minus = (SubtractionOperator) termsLibrary.getTL().get(ButtonName.SUBTRACTION);
            expressionScreen.setText(printOperationQueue(minus));
        }, termsLibrary.getTL().get(ButtonName.SUBTRACTION), 4 + col, row + 3));
        buttonList.add(new CalculatorButton<>(ButtonName.ADDITION.getName(), event -> {
            AdditionOperator add = (AdditionOperator) termsLibrary.getTL().get(ButtonName.ADDITION);
            expressionScreen.setText(printOperationQueue(add));
        }, termsLibrary.getTL().get(ButtonName.ADDITION), 4 + col, row + 4));

        buttonList.add(new CalculatorButton<>("=", event -> {
            expressionScreen.setText(printOperationQueue(null));
            Operand answer = evaluateExpressionQueue();
            String answerString = answer.toString();
            executionMemory.add(answerString);
            computeScreen.setText(answerString);
            operationQueue.clear();
            executionMemory.push(answerString);
        }, null, 4 + col, row + 5));
        return buttonList;
    }

    private Operand evaluateExpressionQueue() {
        ListIterator<Term> queueIterator = operationQueue.listIterator();

        String lastAnswer = executionMemory.peek();
        Operand result = new Operand(lastAnswer == null ? "0" : lastAnswer);

        while(queueIterator.hasNext()) {
            result = computeExpression(queueIterator.next(), queueIterator, result);
        }
        return result;
    }

    private Operand computeExpression(Term op, Iterator<Term> queueIterator, Operand result) {
        if (op instanceof Operand) return (Operand) op;
        if (op instanceof ParenthesisOperator) {
            ParenthesisOperator pop = (ParenthesisOperator) op;
            if (pop.isOpen()) return evaluateParenthesis(queueIterator.next(), queueIterator, result);
        }
        Operand param = computeExpression(queueIterator.next(), queueIterator, result);
        return op.compute(null, result, param);
    }

    private Operand evaluateParenthesis(Term op, Iterator<Term> queueIterator, Operand result) {
        if (op instanceof ParenthesisOperator && !((ParenthesisOperator)op).isOpen()) return result;
        Operand param = computeExpression(op, queueIterator, result);
        return evaluateParenthesis(queueIterator.next(), queueIterator, param);
    }

    /**
     * Prints an expression unto a String object from the operation queue.
     * @return a String containing the expression
     */
    private String printOperationQueue(Term token) {
        StringBuilder expressionString = new StringBuilder();
        if (!PartialOperand.getStringValue().isEmpty()) {
            Operand operand = new Operand(PartialOperand.getStringValue());
            normalizeExpression(operand);
            operationQueue.addLast(operand);
        }

        if (token != null) {
            normalizeExpression(token);
            operationQueue.addLast(token);
        }
        for (Term term : operationQueue){
            expressionString.append(term.toString());
        }
        PartialOperand.setStringValue("");
        return expressionString.toString();
    }

    private void normalizeExpression(Term token){
        try {
            Term lastOperation = operationQueue.getLast();
            if (lastOperation == null) return;
            boolean shouldAppendProductOperator = (lastOperation instanceof Operand
                    || (lastOperation instanceof ParenthesisOperator && !((ParenthesisOperator) lastOperation).isOpen()))
                    && token.getOperationType() != OperationType.BINARY;
            if (shouldAppendProductOperator) operationQueue.addLast(new MultiplicationOperator());
        }catch (NoSuchElementException ne) {
            System.err.println(ne.getMessage());
        }
    }

    private LinkedList<CalculatorButton<?>> createControlButton() {
        LinkedList<CalculatorButton<?>> controlButtons = new LinkedList<>();
        ModeView modeView = new ModeView(overlayPane, modeData);
        overlayPane.setView(modeView);

        CalculatorButton<?> modeButton = new CalculatorButton<>("Mode", e -> {
            overlayPane.show();
            overlayPane.addCloseButton();
            rootPane.getChildren().add(overlayPane);
        }, null, 0, 3);
        CalculatorButton<?> memoryStoreButton = new CalculatorButton<>("MS", e -> {

        }, null, 0, 3);
        CalculatorButton<?> memoryRecallButton = new CalculatorButton<>("MR", e -> {

        }, null, 0, 3);
        CalculatorButton<?> memoryListButton = new CalculatorButton<>("M⋁", e -> {

        }, null, 0, 3);

        controlButtons.add(modeButton);
        controlButtons.add(memoryStoreButton);
        controlButtons.add(memoryRecallButton);
        controlButtons.add(memoryListButton);
        return controlButtons;
    }

    public GridPane setupGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        ColumnConstraints column1Constraints = new ColumnConstraints(30, 45, 60, Priority.ALWAYS, HPos.LEFT, false);
        column1Constraints.setFillWidth(true);
        for (int i = 0; i < 5; ++i) {
            ColumnConstraints columnConstraints = new ColumnConstraints(30, 45, 60, Priority.ALWAYS, HPos.LEFT, false);
            columnConstraints.setFillWidth(true);
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        for(int i = 0; i < 10; ++i) {
            RowConstraints rowConstraints = new RowConstraints(30, 40, 50);
            rowConstraints.setFillHeight(true);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        expressionScreen.setMaxWidth(Double.MAX_VALUE);
        expressionScreen.setAlignment(Pos.CENTER_RIGHT);
        expressionScreen.setBackground(new Background(new BackgroundFill(Color.gray(0.7), null, null)));
        gridPane.add(expressionScreen, 0, 0, 5, 1);
        computeScreen.setEditable(false);
        computeScreen.setEffect(new Glow(0));
        computeScreen.setFont(new Font("Arial", 18));
        computeScreen.setPrefHeight(40);
        computeScreen.setMaxHeight(60);
        GridPane.setFillWidth(computeScreen, true);
        GridPane.setVgrow(computeScreen, Priority.ALWAYS);
        computeScreen.setAlignment(Pos.CENTER_RIGHT);
        gridPane.add(computeScreen, 0, 1, 5, 1);

        LinkedList<CalculatorButton<?>> controlButtons = createControlButton();
        for (int i = 0; i < controlButtons.size(); ++i){
            gridPane.add(controlButtons.get(i), (i+10)%5, (i+10)/5);
        }

        for (CalculatorButton<? extends Term> calculatorButton : getCalcButtons(0, 4)) {
            gridPane.add(calculatorButton, calculatorButton.getColumn(), calculatorButton.getRow(),
                    calculatorButton.getColSpan(), calculatorButton.getRowSpan());
        }
        gridPane.setGridLinesVisible(true);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(2);
        gridPane.setHgap(2);
        return gridPane;
    }

    public void start(Stage primaryStage) {
        rootPane.setBackground(ROOT_BACKGROUND);
        rootPane.setAlignment(Pos.CENTER);
        rootPane.getChildren().add(setupGrid());
        Scene mainScene = new Scene(rootPane, 350, 450, Color.GRAY);

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
