package com.qualibits.qualeval;

import javafx.scene.control.TextField;

public class PartialOperand extends Operand {

    private static String wholeVal = "";
    private final String numString;

    public PartialOperand(String partValue) {
        super(partValue);
        numString = partValue;
    }

    public static void addPart(PartialOperand op){
        wholeVal += op.numString;
    }

    public static String getStringValue() {
        return wholeVal;
    }

    public static void setStringValue(String s) {
        wholeVal = s;
    }

    public void onHostClickAction(TextField computeScreen) {
        PartialOperand.addPart(this);
        computeScreen.setText(getStringValue());
    }

    @Override
    public String toString() {
        return getStringValue();
    }
}
