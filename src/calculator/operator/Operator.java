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
    private final AppendPosition appendPosition;

    public Operator(OperatorType type) {
        this.type = type;
        appendPosition = AppendPosition.END;
    }

    public Operator(OperatorType type, AppendPosition appendPosition) {
        this.type = type;
        this.appendPosition = appendPosition;
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

    public AppendPosition getAppendPosition() {
        return appendPosition;
    }

    @Override
    public String toString() {
        return "GEN";
    }

    public enum OperatorType{
        UNARY, BINARY, TERNARY
    }

    public enum AppendPosition {
        START, END
    }

}
