package calculator.operator;

import calculator.Operand;
import calculator.OperationType;
import calculator.Term;
import calculator.mode.ModeModel;
import com.sun.istack.internal.Nullable;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

public class Operator implements Term {

    private final OperationType type;
    protected Operand[] tokens;
    protected ModeModel modeData;

    public Operator(OperationType type) {
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
            double nullAdjustedVal = Optional.of(term).orElse(new Operand()).getValue();
            sum.setValue(BigDecimal.valueOf(nullAdjustedVal).add(BigDecimal.valueOf(sum.getValue())).doubleValue());
        }

        return sum;
    }

    public void setModeData(ModeModel mode) {
        this.modeData = mode;
    }

    @Override
    public void onHostClickAction(TextField computeScreen) {

    }

    @Override
    public OperationType getOperationType() {
        return type;
    }

    @Override
    public String toString() {
        return "GEN";
    }

}
