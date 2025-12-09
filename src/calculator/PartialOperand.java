package calculator;

public class PartialOperand extends Operand {

    private String wholeVal;

    public PartialOperand(String partValue) {
        super(partValue);
        wholeVal = partValue;
    }

    public String addPart(String numString){
        return wholeVal += numString;
    }
}
