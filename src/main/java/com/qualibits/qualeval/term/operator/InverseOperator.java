package com.qualibits.qualeval.term.operator;

import com.qualibits.qualeval.term.Operand;
import com.qualibits.qualeval.term.OperationType;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Optional;
import java.util.function.Function;

public class InverseOperator extends DivisionOperator implements Functions{

    public InverseOperator(){
        super(OperationType.UNARY);
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... param) {
        Operand inverse = new Operand();
        double nullAdjustedDenominator = Optional.of(param[1]).orElse(new Operand()).getValue();
        inverse.setValue(BigDecimal.valueOf(1).divide(BigDecimal.valueOf(nullAdjustedDenominator), MathContext.DECIMAL64).doubleValue());
        return inverse;
    }

    @Override
    public int getPrecedence() {
        return 1;
    }

    @Override
    public String toString() {
        return "INV";
    }
}
