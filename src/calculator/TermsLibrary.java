package calculator;

import calculator.operator.*;

import java.util.HashMap;

public class TermsLibrary<T extends Term> {

    private final HashMap<ButtonName, T> termsLibrary;

    public TermsLibrary() {
        termsLibrary = new HashMap<>();
        addLibrary(termsLibrary);
    }

    public void addLibrary(HashMap<ButtonName, T> tl) {
        tl.put(ButtonName.X_POWER_Y, (T) new ExponentOperator());
        tl.put(ButtonName.INVERSE, (T) new Operator(Operator.OperatorType.UNARY));
        tl.put(ButtonName.FACTORIAL, (T) new FactorialOperator());
        tl.put(ButtonName.SQUARE, (T) new SquareOperator());
        tl.put(ButtonName.SQUARE_ROOT, (T) new Operator(Operator.OperatorType.UNARY));
        tl.put(ButtonName.PI, (T) new Operand(Math.PI));
        tl.put(ButtonName.EULER, (T) new Operand(Math.E));
        tl.put(ButtonName.MODULO, (T) new ModulusOperator());
        tl.put(ButtonName.MULTIPLICATION, (T) new MultiplicationOperator());
        tl.put(ButtonName.DIVISION, (T) new DivisionOperator());
        tl.put(ButtonName.ADDITION, (T) new Operator(Operator.OperatorType.BINARY));

        tl.put(ButtonName.SEVEN, (T) new PartialOperand("7"));
        tl.put(ButtonName.EIGHT, (T) new PartialOperand("8"));
        tl.put(ButtonName.NINE, (T) new PartialOperand("9"));
        tl.put(ButtonName.FOUR, (T) new PartialOperand("4"));
        tl.put(ButtonName.FIVE, (T) new PartialOperand("5"));
        tl.put(ButtonName.SIX, (T) new PartialOperand("6"));
        tl.put(ButtonName.ONE, (T) new PartialOperand("1"));
        tl.put(ButtonName.TWO, (T) new PartialOperand("2"));
        tl.put(ButtonName.THREE, (T) new PartialOperand("3"));
        tl.put(ButtonName.ZERO, (T) new PartialOperand("0"));
        tl.put(ButtonName.POINT, (T) new DecimalPointOperator());
    }

    public HashMap<ButtonName, T> getTL() {
        return termsLibrary;
    }
}
