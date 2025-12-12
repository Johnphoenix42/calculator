package calculator;

import calculator.operator.DivisionOperator;
import calculator.operator.ModulusOperator;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.Function;

public class CalculatorApp<T> extends Application {

    public static final String APP_NAME = "Jounin Calculator";
    private TextField expressionScreen = null;
    private TextField computeScreen = null;
    private final LinkedList<CalculatorButton<? extends Term>> operationQueue;
    private final TermsLibrary<? extends Term> termsLibrary;
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
            operationQueue.addLast(((CalculatorButton<? extends Term>) event.getSource()));
            computeScreen.setText("xy");
        }, termsLibrary.getTL().get("xy"), col, row));
        buttonList.add(new CalculatorButton<>("⅟x", event -> {
            operationQueue.addLast(((CalculatorButton<? extends Term>) event.getSource()));
            expressionScreen.setText("1/(" + computeScreen.getText() + ")");
            computeScreen.setText(evaluateQueue());
        }, termsLibrary.getTL().get("⅟x"), col + 1, row));
        buttonList.add(new CalculatorButton<>("n!", event -> {
            operationQueue.addLast(((CalculatorButton<? extends Term>) event.getSource()));
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

        buttonList.add(new CalculatorButton<>("x²", event -> {
            operationQueue.addLast(((CalculatorButton<? extends Term>) event.getSource()));
            computeScreen.setText("x²");
        }, termsLibrary.getTL().get("x²"), col, row + 1));
        buttonList.add(new CalculatorButton<>("⫪", event -> {
            operationQueue.addLast(((CalculatorButton<? extends Term>) event.getSource()));
            computeScreen.setText("(⫪)");
        }, termsLibrary.getTL().get("⫪"), col + 1, row + 1));
        buttonList.add(new CalculatorButton<>("e", event -> {
            operationQueue.addLast(((CalculatorButton<? extends Term>) event.getSource()));
            computeScreen.setText("(e)");
        }, termsLibrary.getTL().get("e"), 2 + col, row + 1));
        buttonList.add(new CalculatorButton<>("mod", event -> {
            operationQueue.addLast(((CalculatorButton<? extends Term>) event.getSource()));
            expressionScreen.setText(expressionScreen.getText() + "%");
            computeScreen.setText("%");
        }, termsLibrary.getTL().get("mod"), 3 + col, row + 1));
        buttonList.add(new CalculatorButton<>("÷", event -> {
            operationQueue.addLast(((CalculatorButton<? extends Term>) event.getSource()));
            expressionScreen.setText(expressionScreen.getText() + "÷");
            expressionScreen.setText(printOperationQueue());
        }, termsLibrary.getTL().get("÷"), 4 + col, row + 1));

        buttonList.add(new CalculatorButton<>("√", event -> {
            expressionScreen.setText("1/(" + computeScreen.getText() + ")");
            computeScreen.setText(evaluateQueue());
        }, null, 4 + col, row + 1));


        buttonList.add(new CalculatorButton<>("7", event -> {
            PartialOperand.addPart((PartialOperand) termsLibrary.getTL().get("7"));
            computeScreen.setText(PartialOperand.getStringValue());
            operationQueue.addLast((CalculatorButton<? extends Term>) event.getSource());
        }, termsLibrary.getTL().get("7"), 1 + col, row + 2));
        buttonList.add(new CalculatorButton<>("8", event -> {
            PartialOperand.addPart((PartialOperand) termsLibrary.getTL().get("8"));
            operationQueue.addLast((CalculatorButton<? extends Term>) event.getSource());
            computeScreen.setText(PartialOperand.getStringValue());
        }, termsLibrary.getTL().get("8"), 2 + col, row + 2));
        buttonList.add(new CalculatorButton<>("9", event -> {
            PartialOperand.addPart((PartialOperand) termsLibrary.getTL().get("9"));
            operationQueue.addLast((CalculatorButton<? extends Term>) event.getSource());
            computeScreen.setText(PartialOperand.getStringValue());
        }, termsLibrary.getTL().get("9"), 3 + col, row + 2));
        buttonList.add(new CalculatorButton<>("4", event -> {
            PartialOperand.addPart((PartialOperand) termsLibrary.getTL().get("4"));
            computeScreen.setText(PartialOperand.getStringValue());
            operationQueue.addLast((CalculatorButton<? extends Term>) event.getSource());
        }, termsLibrary.getTL().get("4"), 1 + col, row + 3));
        buttonList.add(new CalculatorButton<>("5", event -> {
            PartialOperand.addPart((PartialOperand) termsLibrary.getTL().get("5"));
            operationQueue.addLast((CalculatorButton<? extends Term>) event.getSource());
            computeScreen.setText(PartialOperand.getStringValue());
        }, termsLibrary.getTL().get("5"), 2 + col, row + 3));
        buttonList.add(new CalculatorButton<>("6", event -> {
            PartialOperand.addPart((PartialOperand) termsLibrary.getTL().get("6"));
            operationQueue.addLast((CalculatorButton<? extends Term>) event.getSource());
            computeScreen.setText(PartialOperand.getStringValue());
        }, termsLibrary.getTL().get("6"), 3 + col, row + 3));
        buttonList.add(new CalculatorButton<>("1", event -> {
            PartialOperand.addPart((PartialOperand) termsLibrary.getTL().get("1"));
            computeScreen.setText(PartialOperand.getStringValue());
            operationQueue.addLast((CalculatorButton<? extends Term>) event.getSource());
        }, termsLibrary.getTL().get("1"), 1 + col, row + 4));
        buttonList.add(new CalculatorButton<>("2", event -> {
            PartialOperand.addPart((PartialOperand) termsLibrary.getTL().get("2"));
            operationQueue.addLast((CalculatorButton<? extends Term>) event.getSource());
            computeScreen.setText(PartialOperand.getStringValue());
        }, termsLibrary.getTL().get("2"), 2 + col, row + 4));
        buttonList.add(new CalculatorButton<>("3", event -> {
            PartialOperand.addPart((PartialOperand) termsLibrary.getTL().get("3"));
            operationQueue.addLast((CalculatorButton<? extends Term>) event.getSource());
            computeScreen.setText(PartialOperand.getStringValue());
        }, termsLibrary.getTL().get("3"), 3 + col, row + 4));
        buttonList.add(new CalculatorButton<>(".", event -> {
            PartialOperand.addPart((PartialOperand) termsLibrary.getTL().get("."));
            operationQueue.addLast((CalculatorButton<? extends Term>) event.getSource());
            computeScreen.setText(PartialOperand.getStringValue());
        }, termsLibrary.getTL().get(".0"), 1 + col, row + 5));
        buttonList.add(new CalculatorButton<>("0", event -> {
            PartialOperand.addPart((PartialOperand) termsLibrary.getTL().get("0"));
            operationQueue.addLast((CalculatorButton<? extends Term>) event.getSource());
            computeScreen.setText(PartialOperand.getStringValue());
        }, termsLibrary.getTL().get("0"), 2 + col, row + 5));
        buttonList.add(new CalculatorButton<>("Ans", event -> {
            operationQueue.addLast((CalculatorButton<? extends Term>) event.getSource());
            computeScreen.setText(executionMemory.peek());
        }, termsLibrary.getTL().get("3"), 3 + col, row + 5));

        buttonList.add(new CalculatorButton<>("+", event -> {
            operationQueue.addLast((CalculatorButton<? extends Term>) event.getSource());
            computeScreen.setText(printOperationQueue());
        }, termsLibrary.getTL().get("+"), 4 + col, row + 5));


        buttonList.add(new CalculatorButton<>("X₂", event -> {
            computeScreen.undo();
            operationQueue.removeLast();
        }, null, 4 + col, row + 1));

        buttonList.add(new CalculatorButton<>("=", event -> {
            String answer = evaluateQueue();
            computeScreen.setText(answer);
            operationQueue.clear();
        }, null, 4 + col, row + 5));
        return buttonList;
    }

    private String evaluateQueue() {
        Operand operand = new Operand();
        CalculatorButton<Operator> root = null;
        CalculatorButton<Operand> left = null;
        CalculatorButton<Operand> right = null;
        Function<Function<Operand[], Operand>, Operand> operation = null;
        while (!operationQueue.isEmpty()) {
            CalculatorButton<? extends Term> button = this.operationQueue.removeFirst();
            if (button.getTerm() instanceof Operand) {
                if (left == null) left = (CalculatorButton<Operand>) button;
                else {
                    right = (CalculatorButton<Operand>) button;
                    if (operation == null) continue;
                    operand = operation.apply(null);
                }
            } else if (button.getTerm() instanceof Operator) {
                root = (CalculatorButton<Operator>) button;
                Operator operatorToken = root.getTerm();
                operatorToken.setParameters(new Operand[]{left.getTerm(), right.getTerm()});
                if (operatorToken.getOperatorType() == Operator.OperatorType.UNARY) {
                    operand = operatorToken.compute((Operand[] token) -> {
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
        return String.valueOf(operand);
    }

    /**
     * Prints an expression unto a String object from the operation queue.
     * @return a String containing the expression
     * @param <T> any Operator class, e.g. DivisionOperator, ModulusOperator
     */
    private <T extends Operator> String printOperationQueue() {
        StringBuilder expressionString = new StringBuilder();
        for (CalculatorButton<? extends Term> button : operationQueue){
            T t = (T) button.getTerm();
            expressionString.append(t.toString());
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
            //gridPane.getColumnConstraints().add(new ColumnConstraints(30, 45, 60, Priority.ALWAYS, HPos.CENTER, false));
            //gridPane.getColumnConstraints().add(new ColumnConstraints(30, 45, 60, Priority.ALWAYS, HPos.CENTER, false));
            //gridPane.getColumnConstraints().add(column5Constraints);
        }

        for(int i = 0; i < 7; ++i) {
            RowConstraints rowConstraints = new RowConstraints(25, 35, 45);
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
        return gridPane;
    }

    public void start(Stage primaryStage) {
        VBox root = new VBox(5);
        root.setBackground(new Background(new BackgroundFill(
                new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.color(0.1,0.3, 0.1)),
                        new Stop(1, Color.color(0.045, 0.15, 0.045))),
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
