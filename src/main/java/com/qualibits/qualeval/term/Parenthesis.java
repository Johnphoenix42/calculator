package com.qualibits.qualeval.term;

import javafx.scene.control.TextField;

import java.util.function.Function;

public class Parenthesis implements Term {

    private final boolean isOpen;

    public Parenthesis(boolean isOpen) {
        this.isOpen = isOpen;
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... parameter) {
        return null;
    }

    @Override
    public void onHostClickAction(TextField computeScreen) {

    }

    @Override
    public OperationType getOperationType() {
        return null;
    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public String toString() {
        return isOpen ? "(" : ")";
    }
}
