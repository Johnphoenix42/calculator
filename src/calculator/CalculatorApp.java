package calculator;

import calculator.operator.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import java.util.logging.Logger;

public class CalculatorApp extends Application {

    public static final String APP_NAME = "Jounin Calculator";
    private final TextField expressionScreen;
    private final TextField computeScreen;
    private final LinkedList<Term> operationQueue;
    private final TermsLibrary<Term> termsLibrary;
    private final LinkedList<String> executionMemory;
    private Logger logger = null;

    public CalculatorApp() {
        computeScreen = new TextField();
        expressionScreen = new TextField();
        expressionScreen.setEditable(false);
        expressionScreen.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.NONE, new CornerRadii(5), BorderStroke.DEFAULT_WIDTHS)));
        operationQueue = new LinkedList<>();
        termsLibrary = new TermsLibrary<>();
        executionMemory = new LinkedList<>();
        CalculatorButton.setComputeScreen(computeScreen);
        CalculatorButton.setExpressionScreen(expressionScreen);

        logger = Logger.getLogger(getClass().getSimpleName());
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

        buttonList.add(new CalculatorButton<>(ButtonName.X_POWER_Y.getName(), event -> {
            ExponentOperator xPowerY = (ExponentOperator) termsLibrary.getTL().get(ButtonName.X_POWER_Y);
            expressionScreen.setText(printOperationQueue(xPowerY));
        }, termsLibrary.getTL().get(ButtonName.X_POWER_Y), col, row));
        buttonList.add(new CalculatorButton<>(ButtonName.INVERSE.getName(), event -> {
            expressionScreen.setText(printOperationQueue(termsLibrary.getTL().get(ButtonName.INVERSE)));
        }, termsLibrary.getTL().get(ButtonName.INVERSE), col + 1, row));
        buttonList.add(new CalculatorButton<>(ButtonName.FACTORIAL.getName(), event -> {
            FactorialOperator factorial = (FactorialOperator) termsLibrary.getTL().get(ButtonName.FACTORIAL);
            expressionScreen.setText(printOperationQueue(factorial));
        }, termsLibrary.getTL().get(ButtonName.FACTORIAL), 2 + col, row));
        buttonList.add(new CalculatorButton<>("CE", event -> {
            PartialOperand.setStringValue("");
            expressionScreen.setText("");
            computeScreen.setText("0");
            operationQueue.clear();
        }, null, 3 + col, row));
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
        }, null, 4 + col, row));

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
            expressionScreen.setText(sqrt.toString());
            computeScreen.setText(printOperationQueue(sqrt));
        }, termsLibrary.getTL().get(ButtonName.SQUARE_ROOT), col, row + 2));
        buttonList.add(new CalculatorButton<>(ButtonName.TEN_POWER_X.getName(), event -> {
            Operator tenPowerX = (Operator) termsLibrary.getTL().get(ButtonName.TEN_POWER_X);
            expressionScreen.setText(tenPowerX.toString());
            computeScreen.setText(printOperationQueue(tenPowerX));
        }, termsLibrary.getTL().get(ButtonName.TEN_POWER_X), col, row + 3));

        buttonList.add(new CalculatorButton<>(ButtonName.LOG.getName(), event -> {
            Operator log = (Operator) termsLibrary.getTL().get(ButtonName.LOG);
            expressionScreen.setText(log.toString());
            computeScreen.setText(printOperationQueue(log));
        }, termsLibrary.getTL().get(ButtonName.LOG), col, row + 4));
        buttonList.add(new CalculatorButton<>(ButtonName.LN.getName(), event -> {
            Operator ln = (Operator) termsLibrary.getTL().get(ButtonName.LN);
            expressionScreen.setText(ln.toString());
            computeScreen.setText(printOperationQueue(ln));
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
            Operand answer = evaluateQueue();
            String answerString = answer.toString();
            executionMemory.add(answerString);
            computeScreen.setText(answerString);
            operationQueue.clear();
            executionMemory.push(answerString);
        }, null, 4 + col, row + 5));
        return buttonList;
    }

    private Operand evaluateQueue() {
        ListIterator<Term> queueIterator = operationQueue.listIterator();

        String lastAnswer = executionMemory.peek();
        Operand result = new Operand(lastAnswer == null ? "0" : lastAnswer);

        while(queueIterator.hasNext()) {
            result = createTree(queueIterator.next(), queueIterator, result);
        }
        return result;
    }

    private Operand createTree(Term op, Iterator<Term> queueIterator, Operand result) {
        if (op instanceof Operand) return (Operand) op;
        if (op instanceof ParenthesisOperator) {
            ParenthesisOperator pop = (ParenthesisOperator) op;
            if (pop.isOpen()) evaluateParenthesis(queueIterator.next(), queueIterator, result);
        }
        Operand param = createTree(queueIterator.next(), queueIterator, result);
        return op.compute(null, result, param);
    }

    private Operand evaluateParenthesis(Term op, Iterator<Term> queueIterator, Operand result) {
        if (op instanceof ParenthesisOperator && !((ParenthesisOperator)op).isOpen()) return result;
        Operand param = createTree(queueIterator.next(), queueIterator, result);
        return evaluateParenthesis(queueIterator.next(), queueIterator, param);
    }

    /**
     * Prints an expression unto a String object from the operation queue.
     * @return a String containing the expression
     */
    private String printOperationQueue(Term token) {
        StringBuilder expressionString = new StringBuilder();
        if (!PartialOperand.getStringValue().isEmpty()) {
            operationQueue.addLast(new Operand(PartialOperand.getStringValue()));
        }
        boolean shouldAppendProductOperator = (token instanceof Operator &&
                ((Operator) token).getOperatorType() == Operator.OperatorType.UNARY) || token instanceof Operand;
        if (shouldAppendProductOperator)  operationQueue.addLast(new MultiplicationOperator());

        if (token != null) {
            operationQueue.addLast(token);
            /*if (token instanceof Operator) {
                if (((Operator) token).getOperatorType() == Operator.OperatorType.UNARY) {
                    operationQueue.add(operationQueue.size() - 1, new MultiplicationOperator());
                    operationQueue.add(operationQueue.size() - 1, new ParenthesisOperator(true));
                }
            }*/
        }
        for (Term term : operationQueue){
            expressionString.append(term.toString());
        }
        PartialOperand.setStringValue("");
        return expressionString.toString();
    }

    public GridPane setupGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        ColumnConstraints column1Constraints = new ColumnConstraints(30, 45, 60, Priority.ALWAYS, HPos.LEFT, false);
        column1Constraints.setFillWidth(true);
        ColumnConstraints column5Constraints = new ColumnConstraints(30, 45, 60, Priority.ALWAYS, HPos.LEFT, false);
        //gridPane.getColumnConstraints().add(column1Constraints);
        for (int i = 0; i < 5; ++i) {
            ColumnConstraints columnConstraints = new ColumnConstraints(30, 45, 60, Priority.ALWAYS, HPos.LEFT, false);
            columnConstraints.setFillWidth(true);
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        for(int i = 0; i < 9; ++i) {
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

        for (CalculatorButton<? extends Term> calculatorButton : getCalcButtons(0, 3)) {
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
        VBox root = new VBox(5);
        root.setBackground(new Background(new BackgroundFill(
                new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.color(0.1,0.1, 0.1)),
                        new Stop(1, Color.color(0.18, 0.2, 0.18))),
                null, new Insets(10))
        ));
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(setupGrid());
        Scene mainScene = new Scene(root, 400, 400, Color.GRAY);

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
