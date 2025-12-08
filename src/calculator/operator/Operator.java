package calculator.operator;

import calculator.Operand;
import calculator.Term;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class Operator implements Term {

    private final OperatorType type;
    protected Operand[] tokens;

    public Operator(OperatorType type) {
        this.type = type;
    }

    public void setParameters(Operand[] terms) {
        this.tokens = terms;
    }

    public Operand compute() {
        Operand sum = new Operand();
        for (Operand term: this.tokens) {
            double nullAdjustedVal = Optional.of(term).orElse(new Operand()).doubleValue();
            sum.setValue(nullAdjustedVal + sum.doubleValue());
        }
        return sum;
    }

    public String customCompute(Function<Operand[], Operand> computer) {
        return String.valueOf(computer.apply(tokens));
    }

    public OperatorType getOperatorType() {
        return type;
    }

    public enum OperatorType{
        UNARY, Binary, TERNARY
    }

}
