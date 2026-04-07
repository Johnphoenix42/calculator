package com.qualibits.qualeval.term.operator;

import com.qualibits.qualeval.term.Operand;
import com.qualibits.qualeval.term.OperationType;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Optional;
import java.util.function.Function;

public class DivisionOperator extends Operator {

    public static final int IDENTITY = 1;

    public DivisionOperator() {
        super(OperationType.BINARY);
    }

    public DivisionOperator(OperationType type) {
        super(type);
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... param) {
        Operand division = new Operand();
        double nullAdjustedNumerator = Optional.of(param[0]).orElse(new Operand(IDENTITY)).getValue();
        double nullAdjustedDenominator = Optional.of(param[1]).orElse(new Operand(IDENTITY)).getValue();
        division.setValue(BigDecimal.valueOf(nullAdjustedNumerator).divide(BigDecimal.valueOf(nullAdjustedDenominator), MathContext.DECIMAL64).doubleValue());
        return division;
    }

    @Override
    public String toString() {
        return "÷";
    }
}
