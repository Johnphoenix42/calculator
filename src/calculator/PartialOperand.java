package calculator;

public class PartialOperand extends Operand {

    private static String wholeVal = "";
    private String numString;

    public PartialOperand(String partValue) {
        super(partValue);
        numString = partValue;
    }

    public static String addPart(PartialOperand op){
        return wholeVal += op.numString;
    }

    public static String getStringValue() {
        return wholeVal;
    }

    public static void setStringValue(String s) {
        wholeVal = s;
    }
}
