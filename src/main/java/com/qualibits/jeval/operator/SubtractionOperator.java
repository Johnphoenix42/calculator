package com.qualibits.jeval.operator;

import com.qualibits.jeval.Operand;
import com.qualibits.jeval.OperationType;
//import com.sun.istack.internal.Nullable;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

public class SubtractionOperator extends Operator {

    public SubtractionOperator() {
        super(OperationType.BINARY);
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... param) {
        Operand subtraction = new Operand();
        double nullAdjustedParam1 = Optional.of(param[0]).orElse(new Operand()).getValue();
        double nullAdjustedParam2 = Optional.of(param[1]).orElse(new Operand()).getValue();
        subtraction.setValue(BigDecimal.valueOf(nullAdjustedParam1).subtract(BigDecimal.valueOf(nullAdjustedParam2)).doubleValue());
        return subtraction;
    }

    @Override
    public String toString() {
        return "-";
    }
}
