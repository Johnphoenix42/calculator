package calculator.operator;

import calculator.Operand;
import calculator.OperationType;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.function.Function;

public class RootOperator extends ExponentOperator{

    private double param;

    public RootOperator() {
        super();
    }

    public RootOperator(double base) {
        super(base);
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... param) {
        Operand root = new Operand();
        double nullAdjustedDegree = Optional.of(param[0]).orElse(new Operand(base)).getValue();
        if (getOperationType() == OperationType.UNARY) nullAdjustedDegree = base;
        double nullAdjustedVal = Optional.of(param[1]).orElse(new Operand()).getValue();
        root.setValue(Math.pow(nullAdjustedVal, BigDecimal.valueOf(1).divide(BigDecimal.valueOf(nullAdjustedDegree), MathContext.DECIMAL64).doubleValue()));
        return root;
    }

    @Override
    public String toString() {
        return "√";
    }
}
