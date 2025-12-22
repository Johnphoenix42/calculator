package calculator.operator;

import calculator.Operand;
import calculator.OperationType;

import java.util.Optional;
import java.util.function.Function;

public class TrigOperator extends Operator {

    private final TrigOperatorType trigOperatorType;

    public TrigOperator(TrigOperatorType type) {
        super(OperationType.UNARY);
        this.trigOperatorType = type;
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... param) {
        Operand trig = new Operand();
        double nullAdjustedVal = Optional.of(param[1]).orElse(new Operand()).getValue();
        switch (trigOperatorType) {
            case SIN: {
                trig.setValue(Math.sin(nullAdjustedVal));
                break;
            }
            case COS: {
                trig.setValue(Math.cos(nullAdjustedVal));
                break;
            }
            case TAN: {
                trig.setValue(Math.tan(nullAdjustedVal));
                break;
            }
            default: trig.setValue(Double.parseDouble(Double.toHexString(nullAdjustedVal)));
        }

        return trig;
    }

    public TrigOperatorType getTrigOperatorType() {
        return trigOperatorType;
    }

    @Override
    public String toString() {
        switch (trigOperatorType) {
            case SIN: return "Sin";
            case COS: return "Cos";
            case TAN: return "Tan";
            default: return "Trig";
        }
    }

    public enum TrigOperatorType {
        SIN, COS, TAN
    }
}
