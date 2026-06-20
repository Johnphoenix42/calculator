package com.qualibits.qualeval.dialoglayout;

import com.qualibits.qualeval.buttons.TermButton;
import com.qualibits.qualeval.term.Operand;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

import java.util.HashMap;

public class ConstantCreationGridPane extends GridPane {

    private final TextField nameField;
    private final Spinner<Double> valueField;

    public ConstantCreationGridPane(){
        super();
        ColumnConstraints columnConstraints = new ColumnConstraints(40, 50, 60, Priority.ALWAYS, HPos.CENTER, true);
        getColumnConstraints().add(columnConstraints);
        for (int i = 0; i < 2; ++i) {
            getRowConstraints().add(new RowConstraints(40, 50, 60, Priority.ALWAYS, VPos.CENTER, true));
        }
        nameField = new TextField();
        valueField = new Spinner<>(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0, 0.1);
        add(new Label("Name"), 0, 0);
        add(nameField, 1, 0);
        add(new Label("Value"), 0, 1);
        add(valueField, 1, 1);

        valueField.setEditable(true);
    }

    public Spinner<Double> getValueField() {
        return valueField;
    }

    public TextField getNameField() {
        return nameField;
    }


}
