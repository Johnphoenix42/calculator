package calculator.operator;

import calculator.Operand;
import calculator.OperationType;

import java.util.Optional;
import java.util.function.Function;

public class InverseOperator extends DivisionOperator{

    public InverseOperator(){
        super(OperationType.UNARY);
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... param) {
        Operand inverse = new Operand();
        double nullAdjustedDenominator = Optional.of(param[1]).orElse(new Operand()).getValue();
        inverse.setValue(1 / nullAdjustedDenominator);
        return inverse;
    }

    @Override
    public String toString() {
        return "INV";
    }
}
