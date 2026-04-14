package com.qualibits.qualeval.term.operator;

import com.qualibits.qualeval.term.Operand;
import com.qualibits.qualeval.term.OperationType;
import javafx.scene.control.TextField;

import java.util.Optional;
import java.util.function.Function;

public class ExponentOperator extends Operator implements Functions {

    protected double base = 0;

    public ExponentOperator() {
        super(OperationType.BINARY);
    }

    public ExponentOperator(double base) {
        super(OperationType.UNARY);
        this.base = base;
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... param) {
        Operand exponent = new Operand();
        double nullAdjustedBase = Optional.of(param[0]).orElse(new Operand()).getValue();
        if (getOperationType() == OperationType.UNARY) nullAdjustedBase = base;
        double nullAdjustedExponent = Optional.of(param[1]).orElse(new Operand()).getValue();
        exponent.setValue(Math.pow(nullAdjustedBase, nullAdjustedExponent));
        return exponent;
    }

    @Override
    public int getPrecedence() {
        return 2;
    }

    @Override
    public void onHostClickAction(TextField computeScreen) {

    }

    @Override
    public String toString() {
        return getOperationType() == OperationType.BINARY ? "Pow" : "Pow₁₀";
    }
}
