package calculator.operator;

import calculator.Operand;

import java.util.Optional;
import java.util.function.Function;

public class DivisionOperator extends Operator {

    public static final int IDENTITY = 1;

    public DivisionOperator() {
        super(OperatorType.BINARY);
    }

    public DivisionOperator(OperatorType type) {
        super(type);
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... param) {
        Operand division = new Operand();
        double nullAdjustedNumerator = Optional.of(param[0]).orElse(new Operand(IDENTITY)).getValue();
        double nullAdjustedDenominator = Optional.of(param[1]).orElse(new Operand(IDENTITY)).getValue();
        division.setValue(nullAdjustedNumerator / nullAdjustedDenominator);
        return division;
    }

    @Override
    public String toString() {
        return "÷";
    }
}
