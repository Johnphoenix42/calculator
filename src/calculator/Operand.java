package calculator;

import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.util.function.Function;

public class Operand implements Term {

    private double dVal;

    public Operand() {
        dVal = 0;
    }

    public Operand(double val) {
        dVal = val;
    }

    public Operand(String value) {
        dVal = Double.parseDouble(value);
    }

    public void setValue(double v) {
        this.dVal = v;
    }

    public double getValue() {
        return dVal;
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... parameter) {
        return this;
    }

    @Override
    public void onHostClickAction(TextField computeScreen) {

    }

    @Override
    public OperationType getOperationType() {
        return OperationType.NONE;
    }

    @Override
    public String toString() {
        int iVal = (int) dVal;
        if (iVal == dVal) return String.valueOf(iVal);
        return String.valueOf(dVal);
    }
}
