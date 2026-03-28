package com.qualibits.qualeval.operator;

import com.qualibits.qualeval.Operand;
import com.qualibits.qualeval.OperationType;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

public class AdditionOperator extends Operator{

    public AdditionOperator() {
        super(OperationType.BINARY);
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... param) {
        Operand sum = new Operand();
        if (computer != null) {
            return computer.apply(param);
        }
        for (Operand term: param) {
            double nullAdjustedVal = Optional.of(term).orElse(new Operand()).getValue();
            sum.setValue(BigDecimal.valueOf(nullAdjustedVal).add(BigDecimal.valueOf(sum.getValue())).doubleValue());
        }
        return sum;
    }

    @Override
    public void onHostClickAction(TextField computeScreen) {
        computeScreen.setText(toString());
    }

    @Override
    public String toString() {
        return "+";
    }
}
