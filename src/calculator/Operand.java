package calculator;

import javafx.scene.control.TextField;

import java.util.function.Function;

public class Operand extends Number implements Term {

    private Number val;

    public Operand() {
        val = 0;
    }

    public Operand(String value) {
        if (value.contains("."))
            val = Double.parseDouble(value);
        else val = Integer.parseInt(value);
    }

    public Operand(Number value) {
        val = value;
    }

    @Override
    public int intValue() {
        return (int) val;
    }

    @Override
    public long longValue() {
        return (long) val;
    }

    @Override
    public float floatValue() {
        return (float) val;
    }

    @Override
    public double doubleValue() {
        return (double) val;
    }

    public void setValue(double v) {
        this.val = v;
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... parameter) {
        return this;
    }

    @Override
    public void onHostClickAction(TextField computeScreen) {

    }

    @Override
    public String toString() {
        return "(" + val.toString() + ")";
    }
}
