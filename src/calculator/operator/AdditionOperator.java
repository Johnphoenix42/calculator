package calculator.operator;

public class AdditionOperator extends Operator{

    public AdditionOperator() {
        super(OperatorType.BINARY, AppendPosition.END);
    }

    @Override
    public String toString() {
        return "+";
    }
}
