package com.qualibits.qualeval.term.operator;

import com.qualibits.qualeval.term.Operand;
import com.qualibits.qualeval.term.OperationType;
//import com.sun.istack.internal.Nullable;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

public class MultiplicationOperator extends Operator {

    public static final int IDENTITY = 1;

    public MultiplicationOperator() {
        super(OperationType.BINARY);
    }

    public MultiplicationOperator(OperationType opType) {
        super(opType);
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... param) {
        Operand multiplication = new Operand();
        double nullAdjustedParam1 = Optional.of(param[0]).orElse(new Operand(IDENTITY)).getValue();
        double nullAdjustedParam2 = Optional.of(param[1]).orElse(new Operand(IDENTITY)).getValue();
        multiplication.setValue(BigDecimal.valueOf(nullAdjustedParam1).multiply(BigDecimal.valueOf(nullAdjustedParam2)).doubleValue());
        return multiplication;
    }

    @Override
    public String toString() {
        return "⨉";
    }
}
