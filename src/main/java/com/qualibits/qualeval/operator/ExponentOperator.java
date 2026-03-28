package com.qualibits.qualeval.operator;

import com.qualibits.qualeval.Operand;
import com.qualibits.qualeval.OperationType;

import java.util.Optional;
import java.util.function.Function;

public class ExponentOperator extends Operator {

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
    public String toString() {
        return getOperationType() == OperationType.BINARY ? "Pow" : "Pow₁₀";
    }
}
