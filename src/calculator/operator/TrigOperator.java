package calculator.operator;

import calculator.GlobalModes;
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
                trig.setValue(Math.sin(GlobalModes.getAngleMode() == GlobalModes.TrigMode.RADIANS ?
                        nullAdjustedVal : Math.toDegrees(nullAdjustedVal)));
                break;
            }
            case COS: {
                trig.setValue(Math.cos(GlobalModes.getAngleMode() == GlobalModes.TrigMode.RADIANS ?
                        nullAdjustedVal : Math.toDegrees(nullAdjustedVal)));
                break;
            }
            case TAN: {
                trig.setValue(Math.tan(GlobalModes.getAngleMode() == GlobalModes.TrigMode.RADIANS ?
                        nullAdjustedVal : Math.toDegrees(nullAdjustedVal)));
                break;
            }
            case ASIN: {
                trig.setValue(Math.asin(GlobalModes.getAngleMode() == GlobalModes.TrigMode.RADIANS ?
                        nullAdjustedVal : Math.toDegrees(nullAdjustedVal)));
                break;
            }
            case ACOS: {
                trig.setValue(Math.acos(GlobalModes.getAngleMode() == GlobalModes.TrigMode.RADIANS ?
                        nullAdjustedVal : Math.toDegrees(nullAdjustedVal)));
                break;
            }
            case ATAN: {
                trig.setValue(Math.atan(GlobalModes.getAngleMode() == GlobalModes.TrigMode.RADIANS ?
                        nullAdjustedVal : Math.toDegrees(nullAdjustedVal)));
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
            case ASIN: return "Sin^-1";
            case ACOS: return "Cos^-1";
            case ATAN: return "Tan^-1";
            default: return "Trig";
        }
    }

    public enum TrigOperatorType {
        SIN, COS, TAN, ASIN, ACOS, ATAN
    }
}
