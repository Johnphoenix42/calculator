package com.qualibits.qualeval.buttons;

import com.qualibits.qualeval.ButtonName;
import com.qualibits.qualeval.Term;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

/**
 * This class represents a button that wraps a term
 * @param <T> Specifies the term i.e. TrigOperator, Operand
 */
public class TermButton<T extends Term> extends BaseButton {

    private static TextField computeScreen;
    private final T t;

    public TermButton(ButtonName name, EventHandler<ActionEvent> eHandler, T type, int column, int row, int colSpan, int rowSpan){
        super(name, eHandler, column, row, colSpan, rowSpan);
        this.t = type;
    }

    /**
     * This variant leaves out onActionEventHandler function for the user, defaulting to what
     * is specified in onHostClickAction implemented by SubTypes of Term interface
     * @param name name on top of the button
     * @param type term objects that this button represents
     * @param column column where this button can be found
     * @param row row where this button can be found
     */
    public TermButton(ButtonName name, T type, int column, int row) {
        this(name, e -> {
            type.onHostClickAction(computeScreen);
        }, type, column, row, 1, 1);
    }

    public TermButton(ButtonName name, EventHandler<ActionEvent> eHandler, T type, int column, int row){
        this(name, eHandler, type, column, row, 1, 1);
    }

    public static void setComputeScreen(TextField screen) {
        computeScreen = screen;
    }

    public T getTerm() throws NullPointerException {
        try {
            return t;
        } catch (NullPointerException n) {
            throw new NullPointerException(name + " has a NULL term.");
        }
    }
}

