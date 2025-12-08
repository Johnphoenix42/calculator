package calculator.operator;

import calculator.Operand;

import java.util.Optional;

public class Multiplication extends Operator {
    public Multiplication(OperatorType type) {
        super(type);
    }

    @Override
    public Operand compute() {
        Operand multiplication = new Operand();
        for (Operand term: this.tokens) {
            double nullAdjustedVal = Optional.of(term).orElse(new Operand()).doubleValue();
            multiplication.setValue(nullAdjustedVal * multiplication.doubleValue());
        }
        return multiplication;
    }
}
