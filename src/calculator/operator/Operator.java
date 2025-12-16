package calculator.operator;

import calculator.Operand;
import calculator.Term;
import com.sun.istack.internal.Nullable;
import javafx.scene.control.TextField;

import java.util.Optional;
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

    @Override
    public Operand compute(@Nullable Function<Operand[], Operand> computer, Operand... parameter) {
        Operand sum = new Operand();
        if (computer != null) {
            return computer.apply(parameter);
        }
        for (Operand term: parameter) {
            double nullAdjustedVal = Optional.of(term).orElse(new Operand()).doubleValue();
            sum.setValue(nullAdjustedVal + sum.doubleValue());
        }
        return sum;
    }

    public OperatorType getOperatorType() {
        return type;
    }

    @Override
    public void onHostClickAction(TextField computeScreen) {

    }

    @Override
    public String toString() {
        return "GEN";
    }

    public enum OperatorType{
        UNARY, BINARY, TERNARY
    }

}
