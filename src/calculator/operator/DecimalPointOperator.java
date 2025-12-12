package calculator.operator;

import calculator.Operand;
import calculator.PartialOperand;
import com.sun.istack.internal.Nullable;

import java.util.Optional;
import java.util.function.Function;

public class DecimalPointOperator extends Operator{


    public DecimalPointOperator() {
        super(OperatorType.UNARY);
    }

    @Override
    public Operand compute(@Nullable Function<Operand[], Operand> computer) {
        return Optional.of(tokens[0]).orElse(new PartialOperand("0."));
    }

    @Override
    public String toString() {
        return ".";
    }
}
