package calculator.operator;

import calculator.Operand;

import java.util.function.Function;

public class SquareOperator extends MultiplicationOperator{

    public SquareOperator(){
        super(OperatorType.UNARY);
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... param) {
        return super.compute(computer);
    }

    @Override
    public String toString() {
        return "²";
    }
}
