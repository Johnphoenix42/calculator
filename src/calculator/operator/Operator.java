package calculator.operator;

import calculator.Operand;
import calculator.Term;
import com.sun.istack.internal.Nullable;

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

    public Operand compute(@Nullable Function<Operand[], Operand> computer) {
        Operand sum = new Operand();
        if (computer != null) {
            return computer.apply(tokens);
        }
        for (Operand term: this.tokens) {
            double nullAdjustedVal = Optional.of(term).orElse(new Operand()).doubleValue();
            sum.setValue(nullAdjustedVal + sum.doubleValue());
        }
        return sum;
    }

    public OperatorType getOperatorType() {
        return type;
    }

    public enum OperatorType{
        UNARY, BINARY, TERNARY
    }

}
