package calculator.operator;

import calculator.Operand;
import calculator.OperationType;

import java.util.function.Function;

public class SquareOperator extends MultiplicationOperator{

    public SquareOperator(){
        super(OperationType.UNARY);
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... param) {
        return super.compute(computer, param[1], param[1]);
    }

    @Override
    public String toString() {
        return "SQR";
    }
}
