package calculator.operator;

import calculator.Operand;

import java.util.Optional;
import java.util.function.Function;

public class ExponentOperator extends Operator {
    public ExponentOperator() {
        super(OperatorType.BINARY);
    }

    public ExponentOperator(OperatorType opType) {
        super(opType);
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer) {
        Operand exponent = new Operand();
        double nullAdjustedBase = Optional.of(tokens[0]).orElse(new Operand()).doubleValue();
        double nullAdjustedExponent = Optional.of(tokens[1]).orElse(new Operand()).doubleValue();
        exponent.setValue(Math.pow(nullAdjustedBase, nullAdjustedExponent));
        return exponent;
    }

    @Override
    public String toString() {
        return "y";
    }
}
