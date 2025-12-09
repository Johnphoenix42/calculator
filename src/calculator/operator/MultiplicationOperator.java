package calculator.operator;

import calculator.Operand;
import com.sun.istack.internal.Nullable;

import java.util.Optional;
import java.util.function.Function;

public class MultiplicationOperator extends Operator {

    public static final int IDENTITY = 1;

    public MultiplicationOperator() {
        super(OperatorType.BINARY);
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
