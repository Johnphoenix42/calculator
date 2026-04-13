package com.qualibits.qualeval.term.operator;

import com.qualibits.qualeval.term.Operand;
import com.qualibits.qualeval.term.OperationType;
import com.qualibits.qualeval.term.Term;
import com.qualibits.qualeval.mode.ModeModel;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

public abstract class Operator implements Term {

    private final OperationType type;
    protected Operand[] tokens;
    protected ModeModel modeData;

    public Operator(OperationType type) {
        this.type = type;
    }

    public void setParameters(Operand[] terms) {
        this.tokens = terms;
    }

    @Override
    public abstract Operand compute(Function<Operand[], Operand> computer, Operand... parameter);

    public static <T extends Operator> boolean firstPrecedesSecond(T firstOperator, T secondOperator){
        if (firstOperator == null || secondOperator == null) return true;
        return firstOperator.getPrecedence() <= secondOperator.getPrecedence();
    }

    public abstract int getPrecedence();

    public void setModeData(ModeModel mode) {
        this.modeData = mode;
    }

    @Override
    public abstract void onHostClickAction(TextField computeScreen);

    @Override
    public OperationType getOperationType() {
        return type;
    }

    @Override
    public abstract String toString();

}
