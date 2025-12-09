package calculator.operator;

import calculator.Operand;
import com.sun.istack.internal.Nullable;

import java.util.Optional;
import java.util.function.Function;

public class PowerOperator extends Operator {
    public static final int IDENTITY = 1;

    public PowerOperator() {
        super(OperatorType.BINARY);
    }

    @Override
    public Operand compute(@Nullable Function<Operand[], Operand> computer) {
        Operand power = new Operand();
        double nullAdjustedBase = Optional.of(tokens[0]).orElse(new Operand()).doubleValue();
        double nullAdjustedExp = Optional.of(tokens[1]).orElse(new Operand()).doubleValue();
        power.setValue(Math.pow(nullAdjustedBase, nullAdjustedExp));
        return power;
    }
}
