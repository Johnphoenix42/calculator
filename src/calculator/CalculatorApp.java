package calculator;

import calculator.operator.DecimalPointOperator;
import calculator.operator.Operator;
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

import java.util.LinkedList;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalculatorApp<T> extends Application {

    public static final String APP_NAME = "Jounin Calculator";
    private TextField expressionScreen = null;
    private TextField computeScreen = null;
    private final LinkedList<Term> operationQueue;
    private final TermsLibrary<Term> termsLibrary;
    private final LinkedList<String> executionMemory;

    public CalculatorApp() {
        expressionScreen = new TextField();
        expressionScreen.setEditable(false);
        expressionScreen.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.NONE, new CornerRadii(5), BorderStroke.DEFAULT_WIDTHS)));
        operationQueue = new LinkedList<>();
        termsLibrary = new TermsLibrary<>();
        executionMemory = new LinkedList<>();
    }

    LinkedList<CalculatorButton<? extends Term>> getCalcButtons(int col, int row) {
        LinkedList<CalculatorButton<?>> buttonList = new LinkedList<>();
        buttonList.add(new CalculatorButton<>("xªy", event -> {
            operationQueue.addLast(termsLibrary.getTL().get(ButtonName.XPOWERY));
            computeScreen.setText("xy");
        }, termsLibrary.getTL().get("xy"), col, row));
        buttonList.add(new CalculatorButton<>("⅟x", event -> {
            operationQueue.addLast(termsLibrary.getTL().get(ButtonName.INVERSE));
            expressionScreen.setText("1/(" + computeScreen.getText() + ")");
            computeScreen.setText(evaluateQueue());
        }, termsLibrary.getTL().get("⅟x"), col + 1, row));
        buttonList.add(new CalculatorButton<>("n!", event -> {
            operationQueue.addLast(termsLibrary.getTL().get(ButtonName.FACTORIAL));
            computeScreen.setText("n!");
        }, termsLibrary.getTL().get("⅟x"), 2 + col, row));
        buttonList.add(new CalculatorButton<>("CE", event -> {
            computeScreen.setText("0");
            operationQueue.clear();
        }, null, 3 + col, row));
        buttonList.add(new CalculatorButton<>("X", event -> {
            computeScreen.undo();
            operationQueue.removeLast();
        }, null, 4 + col, row));

        buttonList.add(new CalculatorButton<>(ButtonName.SQUARE.getName(), event -> {
            Operator square = (Operator) termsLibrary.getTL().get(ButtonName.SQUARE);
            operationQueue.addLast(square);
            computeScreen.setText(square.toString());
        }, termsLibrary.getTL().get(ButtonName.SQUARE), col, row + 1));
        buttonList.add(new CalculatorButton<>(ButtonName.PI.getName(), event -> {
            Operand pi = (Operand) termsLibrary.getTL().get(ButtonName.PI);
            operationQueue.addLast(pi);
            computeScreen.setText(pi.toString());
        }, termsLibrary.getTL().get(ButtonName.PI), col + 1, row + 1));
        buttonList.add(new CalculatorButton<>(ButtonName.EULER.getName(), event -> {
            Operand euler = (Operand) termsLibrary.getTL().get(ButtonName.EULER);
            operationQueue.addLast(euler);
            computeScreen.setText(euler.toString());
        }, termsLibrary.getTL().get(ButtonName.EULER), 2 + col, row + 1));
        buttonList.add(new CalculatorButton<>(ButtonName.MODULO.getName(), event -> {
            Operator mod = (Operator) termsLibrary.getTL().get(ButtonName.MODULO);
            operationQueue.addLast(mod);
            expressionScreen.setText(printOperationQueue() + mod.toString());
            computeScreen.setText("");
        }, termsLibrary.getTL().get(ButtonName.MODULO), 3 + col, row + 1));
        buttonList.add(new CalculatorButton<>(ButtonName.DIVISION.getName(), event -> {
            Operator division = (Operator) termsLibrary.getTL().get(ButtonName.DIVISION);
            operationQueue.addLast(division);
            expressionScreen.setText(printOperationQueue() + division);
        }, termsLibrary.getTL().get(ButtonName.DIVISION), 4 + col, row + 1));

        buttonList.add(new CalculatorButton<>(ButtonName.SQUARE_ROOT.getName(), event -> {
            Operator sqrt = (Operator) termsLibrary.getTL().get(ButtonName.SQUARE_ROOT);
            expressionScreen.setText(sqrt.toString());
            computeScreen.setText(evaluateQueue());
        }, null, 4 + col, row + 1));


        buttonList.add(new CalculatorButton<>(ButtonName.SEVEN.getName(), event -> {
            PartialOperand seven = (PartialOperand) termsLibrary.getTL().get(ButtonName.SEVEN);
            PartialOperand.addPart(seven);
            operationQueue.addLast(seven);
            computeScreen.setText(PartialOperand.getStringValue());
        }, termsLibrary.getTL().get(ButtonName.SEVEN), 1 + col, row + 2));
        buttonList.add(new CalculatorButton<>(ButtonName.EIGHT.getName(), event -> {
            PartialOperand eight = (PartialOperand) termsLibrary.getTL().get(ButtonName.EIGHT);
            PartialOperand.addPart(eight);
            operationQueue.addLast(eight);
            computeScreen.setText(PartialOperand.getStringValue());
        }, termsLibrary.getTL().get(ButtonName.EIGHT), 2 + col, row + 2));
        buttonList.add(new CalculatorButton<>(ButtonName.NINE.getName(), event -> {
            PartialOperand nine = (PartialOperand) termsLibrary.getTL().get(ButtonName.NINE);
            PartialOperand.addPart(nine);
            operationQueue.addLast(nine);
            computeScreen.setText(PartialOperand.getStringValue());
        }, termsLibrary.getTL().get(ButtonName.NINE), 3 + col, row + 2));
        buttonList.add(new CalculatorButton<>(ButtonName.FOUR.getName(), event -> {
            PartialOperand four = (PartialOperand) termsLibrary.getTL().get(ButtonName.FOUR);
            PartialOperand.addPart(four);
            operationQueue.addLast(four);
            computeScreen.setText(PartialOperand.getStringValue());
        }, termsLibrary.getTL().get(ButtonName.FOUR), 1 + col, row + 3));
        buttonList.add(new CalculatorButton<>(ButtonName.FIVE.getName(), event -> {
            PartialOperand five = (PartialOperand) termsLibrary.getTL().get(ButtonName.FIVE);
            PartialOperand.addPart(five);
            operationQueue.addLast(five);
            computeScreen.setText(PartialOperand.getStringValue());
        }, termsLibrary.getTL().get(ButtonName.FIVE), 2 + col, row + 3));
        buttonList.add(new CalculatorButton<>(ButtonName.SIX.getName(), event -> {
            PartialOperand six = (PartialOperand) termsLibrary.getTL().get(ButtonName.SIX);
            PartialOperand.addPart(six);
            operationQueue.addLast(six);
            computeScreen.setText(PartialOperand.getStringValue());
        }, termsLibrary.getTL().get(ButtonName.SIX), 3 + col, row + 3));
        buttonList.add(new CalculatorButton<>(ButtonName.ONE.getName(), event -> {
            PartialOperand one = (PartialOperand) termsLibrary.getTL().get(ButtonName.ONE);
            PartialOperand.addPart(one);
            operationQueue.addLast(one);
            computeScreen.setText(PartialOperand.getStringValue());
        }, termsLibrary.getTL().get(ButtonName.ONE), 1 + col, row + 4));
        buttonList.add(new CalculatorButton<>(ButtonName.TWO.getName(), event -> {
            PartialOperand two = (PartialOperand) termsLibrary.getTL().get(ButtonName.TWO);
            PartialOperand.addPart(two);
            operationQueue.addLast(two);
            computeScreen.setText(PartialOperand.getStringValue());
        }, termsLibrary.getTL().get(ButtonName.TWO), 2 + col, row + 4));
        buttonList.add(new CalculatorButton<>(ButtonName.THREE.getName(), event -> {
            PartialOperand three = (PartialOperand) termsLibrary.getTL().get(ButtonName.THREE);
            PartialOperand.addPart(three);
            operationQueue.addLast(three);
            computeScreen.setText(PartialOperand.getStringValue());
        }, termsLibrary.getTL().get(ButtonName.THREE), 3 + col, row + 4));
        buttonList.add(new CalculatorButton<>(ButtonName.POINT.getName(), event -> {
            DecimalPointOperator point = (DecimalPointOperator) termsLibrary.getTL().get(ButtonName.POINT);
            String decimalValue = PartialOperand.getStringValue() + point.toString();
            Logger.getLogger(getClass().getName()).log(Level.INFO, decimalValue);
            PartialOperand.setStringValue(decimalValue);
            operationQueue.addLast(point);
            computeScreen.setText(decimalValue);
        }, termsLibrary.getTL().get(ButtonName.POINT), 1 + col, row + 5));
        buttonList.add(new CalculatorButton<>(ButtonName.ZERO.getName(), event -> {
            PartialOperand zero = (PartialOperand) termsLibrary.getTL().get(ButtonName.ZERO);
            PartialOperand.addPart(zero);
            operationQueue.addLast(zero);
            computeScreen.setText(PartialOperand.getStringValue());
        }, termsLibrary.getTL().get(ButtonName.ZERO), 2 + col, row + 5));
        buttonList.add(new CalculatorButton<>(ButtonName.ANS.getName(), event -> {
            operationQueue.addLast(new Operand(executionMemory.peek()));
            computeScreen.setText(executionMemory.peek());
        }, termsLibrary.getTL().get(ButtonName.ANS), 3 + col, row + 5));

        buttonList.add(new CalculatorButton<>(ButtonName.ADDITION.getName(), event -> {
            Operator addition = (Operator) termsLibrary.getTL().get(ButtonName.ADDITION);
            operationQueue.addLast(addition);
            computeScreen.setText(printOperationQueue());
        }, termsLibrary.getTL().get(ButtonName.ADDITION), 4 + col, row + 5));


        buttonList.add(new CalculatorButton<>("X₂", event -> {
            computeScreen.undo();
            operationQueue.removeLast();
        }, null, 4 + col, row + 1));

        buttonList.add(new CalculatorButton<>("=", event -> {
            String answer = evaluateQueue();
            computeScreen.setText(answer);
            printOperationQueue();
        }, null, 4 + col, row + 5));
        return buttonList;
    }

    private String evaluateQueue() {
        Operand resultOperand = new Operand();
        CalculatorButton<Operator> root;
        CalculatorButton<Operand> left = null;
        CalculatorButton<Operand> right = null;
        Function<Function<Operand[], Operand>, Operand> operation = null;
        while (!operationQueue.isEmpty()) {
            Term op = this.operationQueue.removeFirst();
            if (op instanceof Operand) {
                if (left == null) left = (CalculatorButton<Operand>) op;
                else {
                    right = (CalculatorButton<Operand>) op;
                    if (operation == null) continue;
                    resultOperand = operation.apply(null);
                }
            } else if (op instanceof Operator) {
                root = (CalculatorButton<Operator>) op;
                Operator operatorToken = root.getTerm();
                operatorToken.setParameters(new Operand[]{left.getTerm(), right.getTerm()});
                if (operatorToken.getOperatorType() == Operator.OperatorType.UNARY) {
                    resultOperand = operatorToken.compute((Operand[] token) -> {
                        Operand sum = new Operand("0");
                        for (Operand term: token) {
                            sum.setValue(term.doubleValue() + sum.doubleValue());
                        }
                        return sum;
                    });
                } else if (operatorToken.getOperatorType() == Operator.OperatorType.BINARY) {
                    if (left != null) {
                        operation = operatorToken::compute;
                    } else operation = null;
                }
            }
        }
        return String.valueOf(resultOperand);
    }

    /**
     * Prints an expression unto a String object from the operation queue.
     * @return a String containing the expression
     */
    private String printOperationQueue() {
        StringBuilder expressionString = new StringBuilder();
        for (Term term : operationQueue){
            expressionString.append(term.toString());
        }
        executionMemory.add(expressionString.toString());
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

        for(int i = 0; i < 7; ++i) {
            RowConstraints rowConstraints = new RowConstraints(30, 40, 50);
            rowConstraints.setFillHeight(true);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        expressionScreen.setMaxWidth(Double.MAX_VALUE);
        expressionScreen.setAlignment(Pos.CENTER_RIGHT);
        gridPane.add(expressionScreen, 0, 0, 5, 1);
        computeScreen = new TextField();
        computeScreen.setEditable(false);
        computeScreen.setEffect(new Glow(0));
        computeScreen.setFont(new Font("Arial", 18));
        computeScreen.setPrefHeight(40);
        computeScreen.setMaxHeight(60);
        GridPane.setFillWidth(computeScreen, true);
        GridPane.setVgrow(computeScreen, Priority.ALWAYS);
        computeScreen.setAlignment(Pos.CENTER_RIGHT);
        gridPane.add(computeScreen, 0, 1, 5, 1);

        for (CalculatorButton<? extends Term> calculatorButton : getCalcButtons(0, 2)) {
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
                        new Stop(1, Color.color(0.15, 0.15, 0.15))),
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
