package calculator.operator;

import calculator.Operand;
import com.sun.istack.internal.Nullable;

import java.util.Optional;
import java.util.function.Function;

public class SubtractionOperator extends Operator{

    public SubtractionOperator() {
        super(OperatorType.BINARY);
    }

    @Override
    public Operand compute(@Nullable Function<Operand[], Operand> computer, Operand... param) {
        Operand subtraction = new Operand();
        for (Operand term: param) {
            double nullAdjustedVal = Optional.of(term).orElse(new Operand()).doubleValue();
            subtraction.setValue(nullAdjustedVal - subtraction.doubleValue());
        }
        return subtraction;
    }

    @Override
    public String toString() {
        return "-";
    }
}
