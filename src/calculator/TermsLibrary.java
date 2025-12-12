package calculator;

import calculator.operator.DivisionOperator;
import calculator.operator.ModulusOperator;
import calculator.operator.Operator;

import java.util.HashMap;

public class TermsLibrary<T extends Term> {

    private final HashMap<String, T> termsLibrary;

    public TermsLibrary() {
        termsLibrary = new HashMap<>();
        addLibrary(termsLibrary);
    }

    public void addLibrary(HashMap<String, T> tl) {
        tl.put("xy", (T) new Operator(Operator.OperatorType.BINARY));
        tl.put("⅟x", (T) new Operator(Operator.OperatorType.UNARY));
        tl.put("n!", (T) new Operator(Operator.OperatorType.UNARY));
        tl.put("x²", (T) new Operator(Operator.OperatorType.UNARY));
        tl.put("⫪", (T) new Operand(Math.PI));
        tl.put("e", (T) new Operand(Math.E));
        tl.put("mod", (T) new ModulusOperator());
        tl.put("÷", (T) new DivisionOperator());
        tl.put("+", (T) new Operator(Operator.OperatorType.BINARY));

        tl.put("7", (T) new PartialOperand("7"));
        tl.put("8", (T) new PartialOperand("8"));
        tl.put("9", (T) new PartialOperand("9"));
        tl.put("4", (T) new PartialOperand("4"));
        tl.put("5", (T) new PartialOperand("5"));
        tl.put("6", (T) new PartialOperand("6"));
        tl.put("1", (T) new PartialOperand("1"));
        tl.put("2", (T) new PartialOperand("2"));
        tl.put("3", (T) new PartialOperand("3"));
        tl.put("0", (T) new PartialOperand("0"));
    }

    public HashMap<String, T> getTL() {
        return termsLibrary;
    }
}
