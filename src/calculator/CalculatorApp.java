package calculator;

import calculator.operator.Operator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.function.Function;

public class CalculatorApp extends Application {

    public static final String APP_NAME = "Jounin Calculator";
    private TextField expressionScreen = null;
    private TextField computeScreen = null;
    private final LinkedList<CalculatorButton<? extends Term>> operationQueue;

    public CalculatorApp() {
        expressionScreen = new TextField();
        operationQueue = new LinkedList<>();
    }

    LinkedList<CalculatorButton<? extends Term>> getCalcButtons(int col, int row) {
        LinkedList<CalculatorButton<?>> buttonList = new LinkedList<>();
        buttonList.add(new CalculatorButton<>("xsupy", event -> {
            operationQueue.addLast(((CalculatorButton<?>) event.getSource()));
            computeScreen.setText("xy");
        }, new Operator(Operator.OperatorType.BINARY), col, row));
        buttonList.add(new CalculatorButton<>("1/x", event -> {
            operationQueue.addLast(((CalculatorButton<?>) event.getSource()));
            computeScreen.setText("1/x");
        }, new Operator(Operator.OperatorType.UNARY), col + 1, row));
        buttonList.add(new CalculatorButton<>("n!", event -> {
            operationQueue.addLast(((CalculatorButton<?>) event.getSource()));
            computeScreen.setText("n!");
        }, new Operator(Operator.OperatorType.UNARY), 2 + col, row));
        buttonList.add(new CalculatorButton<>("CE", event -> {
            computeScreen.setText("0");
            operationQueue.clear();
        }, null, 3 + col, row));
        buttonList.add(new CalculatorButton<>("X", event -> {
            computeScreen.undo();
            operationQueue.removeLast();
        }, null, 4 + col, row));
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

    public GridPane setupGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        Label label = new Label("l");
        ColumnConstraints column1Constraints = new ColumnConstraints(30, 45, 60, Priority.ALWAYS, HPos.LEFT, false);
        column1Constraints.setFillWidth(true);
        ColumnConstraints column5Constraints = new ColumnConstraints(30, 45, 60, Priority.ALWAYS, HPos.LEFT, false);
        gridPane.getColumnConstraints().add(column1Constraints);
        gridPane.getColumnConstraints().add(new ColumnConstraints(30, 45, 60, Priority.ALWAYS, HPos.CENTER, false));
        gridPane.getColumnConstraints().add(new ColumnConstraints(30, 45, 60, Priority.ALWAYS, HPos.CENTER, false));
        gridPane.getColumnConstraints().add(new ColumnConstraints(30, 45, 60, Priority.ALWAYS, HPos.CENTER, false));
        gridPane.getColumnConstraints().add(column5Constraints);
        //gridPane.add(label, 0, 0, 5, 1);
        expressionScreen.setMaxWidth(Double.MAX_VALUE);
        expressionScreen.setAlignment(Pos.CENTER_RIGHT);
        gridPane.add(expressionScreen, 0, 0, 5, 1);
        computeScreen = new TextField();
        computeScreen.setFont(new Font("Arial", 18));
        computeScreen.setPrefHeight(40);
        computeScreen.setMaxHeight(60);
        GridPane.setFillWidth(computeScreen, true);
        GridPane.setVgrow(computeScreen, Priority.ALWAYS);
        computeScreen.setAlignment(Pos.CENTER_RIGHT);
        gridPane.add(computeScreen, 0, 1, 5, 1);

        for (CalculatorButton<? extends Term> calculatorButton : getCalcButtons(0, 2)) {
            gridPane.add(calculatorButton.getButton(), calculatorButton.getColumn(), calculatorButton.getRow(),
                    calculatorButton.getColSpan(), calculatorButton.getRowSpan());
        }
        gridPane.setGridLinesVisible(true);
        return gridPane;
    }

    public void start(Stage primaryStage) {
        VBox root = new VBox(5);
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

    static class CalculatorButton<T extends Term> {
        private final Button button;
        private final String name;
        private final int column;
        private final int row;
        private final int colSpan;
        private final int rowSpan;
        private final T t;

        CalculatorButton(String name, EventHandler<ActionEvent> eHandler, T type, int column, int row, int colSpan, int rowSpan){
            this.name = name;
            this.t = type;
            this.column = column;
            this.row = row;
            this.colSpan = colSpan;
            this.rowSpan = rowSpan;
            this.button = new Button(name);
            button.setOnAction(eHandler);
        }

        CalculatorButton(String name, EventHandler<ActionEvent> eHandler, T type, int column, int row){
            this(name, eHandler, type, column, row, 1, 1);
        }

        public Button getButton() {
            return button;
        }

        public int getColumn() {
            return column;
        }

        public int getRow() {
            return row;
        }

        public int getColSpan() {
            return colSpan;
        }

        public int getRowSpan() {
            return rowSpan;
        }

        public T getTerm() throws NullPointerException {
            try {
                return t;
            } catch (NullPointerException n) {
                throw new NullPointerException(name + " has a NULL term.");
            }
        }
    }

}
