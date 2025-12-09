package calculator.operator;

import calculator.Operand;
import com.sun.istack.internal.Nullable;

import java.util.Optional;
import java.util.function.Function;

public class Multiplication extends Operator {

    public static final int IDENTITY = 1;

    public Multiplication(OperatorType type) {
        super(type);
    }

    @Override
    public Operand compute(@Nullable Function<Operand[], Operand> computer) {
        Operand multiplication = new Operand();
        for (Operand term: this.tokens) {
            double nullAdjustedVal = Optional.of(term).orElse(new Operand()).doubleValue();
            multiplication.setValue(nullAdjustedVal * multiplication.doubleValue());
        }
        return multiplication;
    }
}
