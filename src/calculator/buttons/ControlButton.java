package calculator.buttons;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ControlButton extends ToggleButton {

    private final String name;
    private final int column;
    private final int row;
    private final int colSpan;
    private final int rowSpan;

    public ControlButton(String name, EventHandler<ActionEvent> eHandler, int column, int row, int colSpan, int rowSpan){
        this.name = name;
        this.column = column;
        this.row = row;
        this.colSpan = colSpan;
        this.rowSpan = rowSpan;
        setText(name);
        setTextFill(Color.color(1, 1, 1));
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
     * @param column
     * @param row
     */
    public ControlButton(String name, int column, int row) {
        this(name, e -> {}, column, row, 1, 1);
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

}
