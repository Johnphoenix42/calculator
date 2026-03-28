package com.qualibits.qualeval;

import javafx.scene.control.TextField;

import java.util.function.Function;

public interface Term {

    Operand compute(Function<Operand[], Operand> computer, Operand... parameter);
    void onHostClickAction(TextField computeScreen);
    OperationType getOperationType();
    String toString();

}
