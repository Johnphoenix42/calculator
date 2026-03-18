package com.qualibits.jeval;

public class EOperand extends Operand{

    public EOperand() {
        super(Math.E);
    }

    @Override
    public String toString() {
        return "e";
    }
}
