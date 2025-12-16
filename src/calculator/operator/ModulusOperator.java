package calculator.operator;

import calculator.Operand;

import java.util.Optional;
import java.util.function.Function;

public class ModulusOperator extends Operator{

    public ModulusOperator() {
        super(OperatorType.BINARY);
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... param) {
        super.compute(null);
        Operand modulus = new Operand();
        double nullAdjustedVal = Optional.of(param[0]).orElse(new Operand()).doubleValue();
        double nullAdjustedBase = Optional.of(param[1]).orElse(new Operand()).doubleValue();
        modulus.setValue(nullAdjustedVal % nullAdjustedBase);
        return modulus;
    }

    @Override
    public String toString() {
        return "%";
    }
}
