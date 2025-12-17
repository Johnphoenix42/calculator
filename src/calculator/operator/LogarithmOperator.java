package calculator.operator;

import calculator.Operand;

import java.util.Optional;
import java.util.function.Function;

public class LogarithmOperator extends Operator {

    private double param;

    public LogarithmOperator() {
        super(OperatorType.BINARY);
    }

    public LogarithmOperator(double param) {
        super(OperatorType.UNARY);
        this.param = param;
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... parameter) {
        Operand log = new Operand();
        double nullAdjustedBase = Optional.ofNullable(parameter[1]).orElse(new Operand(this.param)).getValue();
        double nullAdjustedVal = Optional.ofNullable(parameter[0]).orElse(new Operand()).getValue();
        if (getOperatorType() == OperatorType.UNARY) log.setValue(Math.log10(nullAdjustedBase));
        else {
            log.setValue(Math.log(nullAdjustedVal) / Math.log(nullAdjustedBase));
        }
        return log;
    }

    @Override
    public String toString() {
        return getOperatorType() == OperatorType.UNARY ? "log_10" : "log";
    }
}
