package com.qualibits.qualeval.buttons;

import com.qualibits.qualeval.ButtonName;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class BaseButton extends Button {

    protected final ButtonName name;
    protected final int column;
    protected final int row;
    protected final int colSpan;
    protected final int rowSpan;

    public BaseButton(ButtonName name, EventHandler<ActionEvent> eHandler, int column, int row, int colSpan, int rowSpan){
        this.name = name;
        this.column = column;
        this.row = row;
        this.colSpan = colSpan;
        this.rowSpan = rowSpan;
        setText(name.getName());
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

    public BaseButton(ButtonName name, EventHandler<ActionEvent> eHandler, int column, int row){
        this(name, eHandler, column, row, 1, 1);
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

    public ButtonName getName() {
        return name;
    }
}
