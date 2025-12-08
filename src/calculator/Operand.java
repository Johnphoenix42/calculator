package calculator;

public class Operand extends Number implements Term {

    private Number val;

    public Operand() {
        val = 0;
    }

    public Operand(String value) {
        val = Double.parseDouble(value);
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
}
