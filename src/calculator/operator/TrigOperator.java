package calculator.operator;

import calculator.Operand;
import calculator.OperationType;
import calculator.mode.ModeModel.TrigMode;

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
                trig.setValue(Math.sin(modeData.getAngleMode() == TrigMode.RADIANS ?
                        nullAdjustedVal : Math.toDegrees(nullAdjustedVal)));
                break;
            }
            case COS: {
                trig.setValue(Math.cos(modeData.getAngleMode() == TrigMode.RADIANS ?
                        nullAdjustedVal : Math.toDegrees(nullAdjustedVal)));
                break;
            }
            case TAN: {
                trig.setValue(Math.tan(modeData.getAngleMode() == TrigMode.RADIANS ?
                        nullAdjustedVal : Math.toDegrees(nullAdjustedVal)));
                break;
            }
            case ARC_SIN: {
                trig.setValue(Math.asin(modeData.getAngleMode() == TrigMode.RADIANS ?
                        nullAdjustedVal : Math.toDegrees(nullAdjustedVal)));
                break;
            }
            case ARC_COS: {
                trig.setValue(Math.acos(modeData.getAngleMode() == TrigMode.RADIANS ?
                        nullAdjustedVal : Math.toDegrees(nullAdjustedVal)));
                break;
            }
            case ARC_TAN: {
                trig.setValue(Math.atan(modeData.getAngleMode() == TrigMode.RADIANS ?
                        nullAdjustedVal : Math.toDegrees(nullAdjustedVal)));
                break;
            }
            default: trig.setValue(Double.parseDouble(Double.toHexString(nullAdjustedVal)));
        }

        return trig;
    }

    @Override
    public String toString() {
        switch (trigOperatorType) {
            case SIN: return "Sin";
            case COS: return "Cos";
            case TAN: return "Tan";
            case ARC_SIN: return "Sin^-1";
            case ARC_COS: return "Cos^-1";
            case ARC_TAN: return "Tan^-1";
            default: return "Trig";
        }
    }

    public enum TrigOperatorType {
        SIN, COS, TAN, ARC_SIN, ARC_COS, ARC_TAN
    }
}
