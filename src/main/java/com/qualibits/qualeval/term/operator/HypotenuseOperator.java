package com.qualibits.qualeval.term.operator;

import com.qualibits.qualeval.term.Operand;

import java.util.Optional;
import java.util.function.Function;

public class HypotenuseOperator extends AdditionOperator{

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... param) {
        Operand exponent = new Operand();
        double nullAdjustedOpp = Optional.of(param[0]).orElse(new Operand()).getValue();
        double nullAdjustedAdj = Optional.of(param[1]).orElse(new Operand()).getValue();
        exponent.setValue(Math.pow(Math.pow(nullAdjustedOpp, 2) + Math.pow(nullAdjustedAdj, 2), 0.5));
        return exponent;
    }

    @Override
    public String toString() {
        return "Hyp";
    }
}
