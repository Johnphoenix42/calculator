package calculator.operator;

import calculator.Operand;
import com.sun.istack.internal.Nullable;

import java.util.Optional;
import java.util.function.Function;

public class SubtractionOperator extends Operator {

    public SubtractionOperator() {
        super(OperatorType.BINARY);
    }

    @Override
    public Operand compute(@Nullable Function<Operand[], Operand> computer, Operand... param) {
        Operand subtraction = new Operand();
        double nullAdjustedParam1 = Optional.of(param[0]).orElse(new Operand()).getValue();
        double nullAdjustedParam2 = Optional.of(param[1]).orElse(new Operand()).getValue();
        subtraction.setValue(nullAdjustedParam1 - nullAdjustedParam2);
        return subtraction;
    }

    @Override
    public String toString() {
        return "-";
    }
}
