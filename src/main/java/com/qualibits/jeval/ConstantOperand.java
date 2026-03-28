package com.qualibits.jeval;

public class ConstantOperand extends Operand{

    private final String denotation;

    public ConstantOperand(double val, String denotation){
        super(val);
        this.denotation = denotation;
    }

    @Override
    public String toString() {
        return denotation;
    }
}
