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
                        nullAdjustedVal : Math.toRadians(nullAdjustedVal)));
                break;
            }
            case COS: {
                trig.setValue(Math.cos(modeData.getAngleMode() == TrigMode.RADIANS ?
                        nullAdjustedVal : Math.toRadians(nullAdjustedVal)));
                break;
            }
            case TAN: {
                trig.setValue(Math.tan(modeData.getAngleMode() == TrigMode.RADIANS ?
                        nullAdjustedVal : Math.toRadians(nullAdjustedVal)));
                break;
            }
            case ARC_SIN: {
                trig.setValue(modeData.getAngleMode() == TrigMode.RADIANS ?
                        Math.asin(nullAdjustedVal) : Math.toDegrees(Math.asin(nullAdjustedVal)));
                break;
            }
            case ARC_COS: {
                trig.setValue(modeData.getAngleMode() == TrigMode.RADIANS ?
                        Math.acos(nullAdjustedVal) : Math.toRadians(Math.acos(nullAdjustedVal)));
                break;
            }
            case ARC_TAN: {
                trig.setValue(modeData.getAngleMode() == TrigMode.RADIANS ?
                        Math.atan(nullAdjustedVal) : Math.toRadians(Math.atan(nullAdjustedVal)));
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
            case ARC_SIN: return "Sin⁻¹";
            case ARC_COS: return "Cos⁻¹";
            case ARC_TAN: return "Tan⁻¹";
            default: return "Trig";
        }
    }

    public enum TrigOperatorType {
        SIN, COS, TAN, ARC_SIN, ARC_COS, ARC_TAN
    }
}
