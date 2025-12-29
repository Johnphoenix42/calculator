package calculator.buttons;

import calculator.Term;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CalculatorButton<T extends Term> extends Button {

    private static TextField computeScreen;
    private static TextField expressionScreen;
    private final String name;
    private final int column;
    private final int row;
    private final int colSpan;
    private final int rowSpan;
    private final T t;

    public CalculatorButton(String name, EventHandler<ActionEvent> eHandler, T type, int column, int row, int colSpan, int rowSpan){
        this.name = name;
        this.t = type;
        this.column = column;
        this.row = row;
        this.colSpan = colSpan;
        this.rowSpan = rowSpan;
        setText(name);
        setTextFill(Color.GRAY);
        setFont(Font.font(15));
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        setBackground(new Background(
                new BackgroundFill(Color.web("0f0f0f"), null, null)
        ));
        setOnMouseEntered(e -> {
            setEffect(new Glow(1));
        });
        setOnMouseExited(e -> {
            setEffect(new Glow(0));
        });
        setOnAction(eHandler);
    }

    /**
     * This variant leaves out onActionEventHandler function for the user, defaulting to what
     * is specified in onHostClickAction implemented by SubTypes of Term interface
     * @param name
     * @param type
     * @param column
     * @param row
     */
    public CalculatorButton(String name, T type, int column, int row) {
        this(name, e -> {
            type.onHostClickAction(computeScreen);
        }, type, column, row, 1, 1);
    }

    public CalculatorButton(String name, EventHandler<ActionEvent> eHandler, T type, int column, int row){
        this(name, eHandler, type, column, row, 1, 1);
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

    public static void setComputeScreen(TextField screen) {
        computeScreen = screen;
    }

    public static void setExpressionScreen(TextField screen){
        expressionScreen = screen;
    }

    public T getTerm() throws NullPointerException {
        try {
            return t;
        } catch (NullPointerException n) {
            throw new NullPointerException(name + " has a NULL term.");
        }
    }
}

