package com.qualibits.qualeval.term.operator;

import com.qualibits.qualeval.term.Operand;
import com.qualibits.qualeval.term.OperationType;
import javafx.scene.control.TextField;

import java.util.Optional;
import java.util.function.Function;

public class LogarithmOperator extends Operator implements Functions {

    private double param;

    public LogarithmOperator() {
        super(OperationType.BINARY);
    }

    public LogarithmOperator(double param) {
        super(OperationType.UNARY);
        this.param = param;
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... parameter) {
        Operand log = new Operand();
        double nullAdjustedBase = Optional.ofNullable(parameter[1]).orElse(new Operand(this.param)).getValue();
        double nullAdjustedVal = Optional.ofNullable(parameter[0]).orElse(new Operand()).getValue();
        if (getOperationType() == OperationType.UNARY) log.setValue(Math.log10(nullAdjustedBase));
        else {
            log.setValue(Math.log(nullAdjustedVal) / Math.log(nullAdjustedBase));
        }
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
        return getOperationType() == OperationType.UNARY ? "log₁₀" : "log";
    }
}
