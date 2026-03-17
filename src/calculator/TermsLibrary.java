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
        tl.put(ButtonName.SINH, (T) new TrigOperator(TrigOperator.TrigOperatorType.SINH));
        tl.put(ButtonName.COSH, (T) new TrigOperator(TrigOperator.TrigOperatorType.COSH));
        tl.put(ButtonName.TANH, (T) new TrigOperator(TrigOperator.TrigOperatorType.TANH));
        tl.put(ButtonName.ARC_TAN, (T) new TrigOperator(TrigOperator.TrigOperatorType.ARC_TAN));
        tl.put(ButtonName.ARC_SIN, (T) new TrigOperator(TrigOperator.TrigOperatorType.ARC_SIN));
        tl.put(ButtonName.ARC_COS, (T) new TrigOperator(TrigOperator.TrigOperatorType.ARC_COS));
        tl.put(ButtonName.SIN, (T) new TrigOperator(TrigOperator.TrigOperatorType.SIN));
        tl.put(ButtonName.COS, (T) new TrigOperator(TrigOperator.TrigOperatorType.COS));
        tl.put(ButtonName.TAN, (T) new TrigOperator(TrigOperator.TrigOperatorType.TAN));

        tl.put(ButtonName.HYPOTENUSE, (T) new HypotenuseOperator());
        tl.put(ButtonName.X_POWER_Y, (T) new ExponentOperator());
        tl.put(ButtonName.INVERSE, (T) new InverseOperator());
        tl.put(ButtonName.FACTORIAL, (T) new FactorialOperator());
        tl.put(ButtonName.SQUARE, (T) new SquareOperator());
        tl.put(ButtonName.SQUARE_ROOT, (T) new RootOperator(2));
        tl.put(ButtonName.TEN_POWER_X, (T) new ExponentOperator(10D));
        tl.put(ButtonName.MODULO, (T) new ModulusOperator());
        tl.put(ButtonName.MULTIPLICATION, (T) new MultiplicationOperator());
        tl.put(ButtonName.DIVISION, (T) new DivisionOperator());
        tl.put(ButtonName.ADDITION, (T) new AdditionOperator());
        tl.put(ButtonName.SUBTRACTION, (T) new SubtractionOperator());
        tl.put(ButtonName.LOG, (T) new LogarithmOperator(10));
        tl.put(ButtonName.LN, (T) new NaturalLogOperator());
        tl.put(ButtonName.OPEN_PARENTHESIS, (T) new ParenthesisOperator(true));
        tl.put(ButtonName.CLOSE_PARENTHESIS, (T) new ParenthesisOperator(false));

        tl.put(ButtonName.PI, (T) new PiOperand());
        tl.put(ButtonName.EULER, (T) new EOperand());
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
