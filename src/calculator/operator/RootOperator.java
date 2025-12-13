package calculator.operator;

import calculator.Operand;

import java.util.Optional;
import java.util.function.Function;

public class RootOperator extends ExponentOperator{

    public RootOperator() {
        super(OperatorType.BINARY);
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer) {
        Operand root = new Operand();
        double nullAdjustedDegree = Optional.of(tokens[0]).orElse(new Operand()).doubleValue();
        double nullAdjustedVal = Optional.of(tokens[1]).orElse(new Operand()).doubleValue();
        root.setValue(nthRootOf(nullAdjustedDegree, nullAdjustedVal));
        return root;
    }

    private double nthRootOf(double n, double val) {
        return StrictMath.pow(val, 1/n);
    }

    @Override
    public String toString() {
        return "√";
    }
}
