package com.qualibits.qualeval.operator;

import com.qualibits.qualeval.Operand;
import com.qualibits.qualeval.OperationType;

import java.util.Optional;
import java.util.function.Function;

public class NaturalLogOperator extends Operator{

    public NaturalLogOperator() {
        super(OperationType.UNARY);
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... parameter) {
        Operand log = new Operand();
        double nullAdjustedVal = Optional.of(parameter[0]).orElse(new Operand()).getValue();
        double nullAdjustedBase = Optional.of(parameter[1]).orElse(new Operand()).getValue();
        log.setValue(Math.log(nullAdjustedBase));
        return log;
    }

    @Override
    public String toString() {
        return "ln";
    }
}
