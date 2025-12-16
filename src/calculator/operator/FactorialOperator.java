package calculator.operator;

import calculator.Operand;

import java.util.function.Function;

public class FactorialOperator extends MultiplicationOperator{

    public FactorialOperator(){
        super(OperatorType.UNARY);
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... param) {
        return super.compute(null);
    }

    @Override
    public String toString() {
        return "!";
    }
}
