package calculator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class CalculatorButton<T extends Term> extends Button {
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
        setText(name);
        setFont(Font.font(10));
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        setOnAction(eHandler);
    }

    CalculatorButton(String name, EventHandler<ActionEvent> eHandler, T type, int column, int row){
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

    public T getTerm() throws NullPointerException {
        try {
            return t;
        } catch (NullPointerException n) {
            throw new NullPointerException(name + " has a NULL term.");
        }
    }
}

