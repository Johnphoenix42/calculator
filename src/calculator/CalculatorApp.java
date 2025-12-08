package calculator;

import calculator.operator.Operator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.LinkedList;

public class CalculatorApp extends Application {

    public static final String APP_NAME = "Simp Calc";
    private TextField expressionScreen = null;
    private TextField computeScreen = null;
    private final LinkedList<CalculatorButton<? extends Term>> operationQueue;

    public CalculatorApp() {
        expressionScreen = new TextField();
        operationQueue = new LinkedList<>();
    }

    LinkedList<CalculatorButton<? extends Term>> getCalcButtons() {
        LinkedList<CalculatorButton<?>> buttonList = new LinkedList<>();
        buttonList.add(new CalculatorButton<>("xy", event -> {
            operationQueue.addLast(((CalculatorButton<?>) event.getSource()));
            computeScreen.setText("xy");
        }, new Operator(Operator.OperatorType.Binary), 0, 1, 0, 0));
        buttonList.add(new CalculatorButton<>("1/x", event -> {

            computeScreen.setText("1/x");
        }, new Operator(Operator.OperatorType.UNARY), 2, 1, 0, 0));
        buttonList.add(new CalculatorButton<>("n!", event -> {
            computeScreen.setText("n!");
        }, new Operator(Operator.OperatorType.UNARY), 3, 1, 0, 0));
        buttonList.add(new CalculatorButton<>("CE", event -> {
            computeScreen.setText("0");
            operationQueue.clear();
        }, null, 4, 1, 0, 0));
        buttonList.add(new CalculatorButton<>("X", event -> {
            computeScreen.undo();
            operationQueue.removeLast();
        }, null, 5, 1, 0, 0));
        buttonList.add(new CalculatorButton<>("=", event -> {
            String answer = evaluateQueue();
            computeScreen.setText(answer);
            operationQueue.clear();
        }, null, 5, 5, 0, 0));
        return buttonList;
    }

    private String evaluateQueue() {
        String val = "";
        CalculatorButton<Operator> root = null;
        CalculatorButton<Operand> left = null;
        CalculatorButton<Operand> right = null;
        while (!operationQueue.isEmpty()) {
            CalculatorButton<? extends Term> button = this.operationQueue.removeFirst();
            if (button.getTerm() instanceof Operand) {
                if (left == null) left = (CalculatorButton<Operand>) button;
                else right = (CalculatorButton<Operand>) button;
            } else if (button.getTerm() instanceof Operator) {
                root = (CalculatorButton<Operator>) button;
                assert left != null;
                assert right != null;
                Operator operatorToken = root.getTerm();
                operatorToken.setParameters(new Operand[]{left.getTerm(), right.getTerm()});
                if (operatorToken.getOperatorType() == Operator.OperatorType.UNARY) {
                    val = operatorToken.customCompute((Operand[] token) -> {
                        Operand sum = new Operand("0");
                        for (Operand term: token) {
                            sum.setValue(term.doubleValue() + sum.doubleValue());
                        }
                        return sum;
                    });
                }
            }
        }
        return val;
    }

    public GridPane setupGrid() {
        GridPane gridPane = new GridPane();
        Label label = new Label();
        gridPane.getColumnConstraints().add(new ColumnConstraints(30, 45, 60, Priority.ALWAYS, HPos.CENTER, false));
        gridPane.getColumnConstraints().add(new ColumnConstraints(30, 45, 60, Priority.ALWAYS, HPos.CENTER, false));
        gridPane.add(label, 0, 0, 5, 1);
        gridPane.add(expressionScreen, 0, 0, 5, 1);
        computeScreen = new TextField();
        gridPane.add(computeScreen, 0, 1, 5, 1);

        for (CalculatorButton<? extends Term> calculatorButton : getCalcButtons()) {
            gridPane.add(calculatorButton.getButton(), calculatorButton.getColumn(), calculatorButton.getRow(),
                    calculatorButton.getColSpan(), calculatorButton.getRowSpan());
        }
        return gridPane;
    }

    public void start(Stage primaryStage) {
        VBox root = new VBox(5);
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
        private final EventHandler<ActionEvent> eventHandler;
        private final int column;
        private final int row;
        private final int colSpan;
        private final int rowSpan;
        private final T t;

        CalculatorButton(String name, EventHandler<ActionEvent> eHandler, T type, int column, int row, int colSpan, int rowSpan){
            this.name = name;
            this.eventHandler = eHandler;
            this.t = type;
            this.column = column;
            this.row = row;
            this.colSpan = colSpan;
            this.rowSpan = rowSpan;
            this.button = new Button(name);
            button.setOnAction(eHandler);
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
