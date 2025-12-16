package calculator.operator;

import calculator.ButtonName;
import calculator.Operand;
import javafx.scene.control.TextField;

import java.util.Optional;
import java.util.function.Function;

public class AdditionOperator extends Operator{

    public AdditionOperator() {
        super(OperatorType.BINARY);
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... param) {
        Operand sum = new Operand();
        if (computer != null) {
            return computer.apply(param);
        }
        for (Operand term: param) {
            double nullAdjustedVal = Optional.of(term).orElse(new Operand()).doubleValue();
            sum.setValue(nullAdjustedVal + sum.doubleValue());
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
