package com.qualibits.qualeval.term.operator;

import com.qualibits.qualeval.term.Operand;
import com.qualibits.qualeval.term.OperationType;

import java.util.Optional;
import java.util.function.Function;

public class ModulusOperator extends Operator{

    public static final int IDENTITY = 1;

    public ModulusOperator() {
        super(OperationType.BINARY);
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... param) {
        Operand modulus = new Operand();
        double nullAdjustedVal = Optional.of(param[0]).orElse(new Operand()).getValue();
        double nullAdjustedBase = Optional.of(param[1]).orElse(new Operand(IDENTITY)).getValue();
        modulus.setValue(nullAdjustedVal % nullAdjustedBase);
        return modulus;
    }

    @Override
    public String toString() {
        return "%";
    }
}
