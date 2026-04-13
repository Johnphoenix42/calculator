package com.qualibits.qualeval.term.operator;

import com.qualibits.qualeval.term.Operand;
import com.qualibits.qualeval.term.OperationType;
import com.qualibits.qualeval.mode.ModeModel.TrigMode;
import javafx.scene.control.TextField;

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
            case SINH: {
                trig.setValue(Math.sinh(modeData.getAngleMode() == TrigMode.RADIANS ?
                        nullAdjustedVal : Math.toRadians(nullAdjustedVal)));
                break;
            }
            case COSH: {
                trig.setValue(Math.cosh(modeData.getAngleMode() == TrigMode.RADIANS ?
                        nullAdjustedVal : Math.toRadians(nullAdjustedVal)));
                break;
            }
            case TANH: {
                trig.setValue(Math.tanh(modeData.getAngleMode() == TrigMode.RADIANS ?
                        nullAdjustedVal : Math.toRadians(nullAdjustedVal)));
                break;
            }
            default: trig.setValue(Double.parseDouble(Double.toHexString(nullAdjustedVal)));
        }

        return trig;
    }

    @Override
    public int getPrecedence() {
        return 1;
    }

    @Override
    public void onHostClickAction(TextField computeScreen) {

    }

    @Override
    public String toString() {
        return switch (trigOperatorType) {
            case SIN -> "Sin";
            case COS -> "Cos";
            case TAN -> "Tan";
            case ARC_SIN -> "Sin⁻¹";
            case ARC_COS -> "Cos⁻¹";
            case ARC_TAN -> "Tan⁻¹";
            case SINH -> "Sinh";
            case COSH -> "Cosh";
            case TANH -> "Tanh";
        };
    }

    public enum TrigOperatorType {
        SIN, COS, TAN, ARC_SIN, ARC_COS, ARC_TAN, SINH, COSH, TANH
    }
}
