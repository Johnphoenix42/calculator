package calculator.operator;

import calculator.Operand;
import calculator.OperationType;
import calculator.PartialOperand;
import com.sun.istack.internal.Nullable;
import javafx.scene.control.TextField;

import java.util.Optional;
import java.util.function.Function;

public class DecimalPointOperator extends Operator{


    public DecimalPointOperator() {
        super(OperationType.UNARY);
    }

    @Override
    public Operand compute(@Nullable Function<Operand[], Operand> computer, Operand... param) {
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
