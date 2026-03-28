package com.qualibits.jeval.operator;

import com.qualibits.jeval.Operand;
import com.qualibits.jeval.OperationType;
import com.qualibits.jeval.PartialOperand;
import javafx.scene.control.TextField;

import java.util.Optional;
import java.util.function.Function;

public class DecimalPointOperator extends Operator{


    public DecimalPointOperator() {
        super(OperationType.UNARY);
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... param) {
        return Optional.of(param[0]).orElse(new PartialOperand("0."));
    }

    @Override
    public void onHostClickAction(TextField computeScreen) {
        String decimalValue = PartialOperand.getStringValue() + this;
        PartialOperand.setStringValue(decimalValue);
        computeScreen.setText(decimalValue);
    }

    @Override
    public String toString() {
        return ".";
    }
}
