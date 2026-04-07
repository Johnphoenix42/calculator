package com.qualibits.qualeval.term.operator;

import com.qualibits.qualeval.term.Operand;
import com.qualibits.qualeval.term.OperationType;

import java.util.function.Function;

public class ParenthesisOperator extends Operator{

    private final boolean isOpen;

    public ParenthesisOperator(boolean isOpen) {
        super(OperationType.BINARY);
        this.isOpen = isOpen;
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... parameter) {
        return null;
    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public String toString() {
        return isOpen ? "(" : ")";
    }
}
