package com.qualibits.qualeval.term.operator;

import com.qualibits.qualeval.term.Operand;
import com.qualibits.qualeval.term.OperationType;
import javafx.scene.control.TextField;

import java.util.Optional;
import java.util.function.Function;

public class NaturalLogOperator extends Operator implements Functions {

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
    public int getPrecedence() {
        return 2;
    }

    @Override
    public void onHostClickAction(TextField computeScreen) {

    }

    @Override
    public String toString() {
        return "ln";
    }
}
