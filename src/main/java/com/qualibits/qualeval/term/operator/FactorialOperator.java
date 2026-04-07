package com.qualibits.qualeval.term.operator;

import com.qualibits.qualeval.term.Operand;
import com.qualibits.qualeval.term.OperationType;

import java.util.Optional;
import java.util.function.Function;

public class FactorialOperator extends MultiplicationOperator{

    public FactorialOperator(){
        super(OperationType.UNARY);
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... param) {
        Operand factorial = new Operand();
        double nullAdjustedVal = Optional.of(param[1]).orElse(new Operand(1)).getValue();
        factorial.setValue(factorial(nullAdjustedVal));
        return factorial;
    }

    private double factorial(double val) {
        double result = 1;
        for (int i = (int)val; i > 0; i--){
            result *= i;
        }
        return result;
    }

    @Override
    public String toString() {
        return "Fac";
    }
}
