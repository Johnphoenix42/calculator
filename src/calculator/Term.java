package calculator;

import com.sun.istack.internal.Nullable;
import javafx.scene.control.TextField;

import java.util.function.Function;

public interface Term {

    Operand compute(@Nullable Function<Operand[], Operand> computer, Operand... parameter);
    void onHostClickAction(TextField computeScreen);
    OperationType getOperationType();
    String toString();

}
