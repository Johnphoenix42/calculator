package calculator;

import calculator.mode.ModeModel;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.util.function.Function;

public class Operand implements Term {

    private double dVal;
    protected ModeModel modeData;

    public Operand() {
        dVal = 0;
    }

    public Operand(double val) {
        dVal = val;
    }

    public Operand(String value) {
        dVal = Double.parseDouble(value);
    }

    public void setValue(double v) {
        this.dVal = v;
    }

    public double getValue() {
        return dVal;
    }

    @Override
    public Operand compute(Function<Operand[], Operand> computer, Operand... parameter) {
        return this;
    }

    @Override
    public void setModeData(ModeModel modeData) {
        this.modeData = modeData;
    }

    @Override
    public void onHostClickAction(TextField computeScreen) {

    }

    @Override
    public OperationType getOperationType() {
        return OperationType.NONE;
    }

    @Override
    public String toString() {
        long iVal = (long) dVal;
        if (iVal == dVal) return String.valueOf(iVal);
        return String.valueOf(dVal);
    }

    public static String convertToBinary(double number, int decimalPlaces) {
        // 1. Handle the integer part
        long integerPart = (long) number;
        double fractionalPart = number - integerPart;

        StringBuilder binary = new StringBuilder();
        binary.append(Long.toBinaryString(integerPart));
        binary.append(".");

        // 2. Handle the fractional part
        if (integerPart == number) return binary.toString();
        while (decimalPlaces > 0) {
            fractionalPart *= 2;
            int bit = (int) fractionalPart;
            binary.append(bit);

            // Remove the 1 if it was added to the string
            fractionalPart -= bit;
            decimalPlaces--;

            // Optimization: if fraction becomes 0, we can stop early
            if (fractionalPart == 0) break;
        }

        return binary.toString();
    }

    public static String convertToOctal(double number, int decimalPlaces) {
        // 1. Handle the integer part
        long integerPart = (long) number;
        double fractionalPart = number - integerPart;

        StringBuilder octal = new StringBuilder();
        octal.append(Long.toOctalString(integerPart));
        octal.append(".");

        // 2. Handle the fractional part
        if (integerPart == number) return octal.toString();
        while (decimalPlaces > 0) {
            fractionalPart *= 8;
            int bit = (int) fractionalPart;
            octal.append(bit);

            // Remove the 1 if it was added to the string
            fractionalPart -= bit;
            decimalPlaces--;

            // Optimization: if fraction becomes 0, we can stop early
            if (fractionalPart == 0) break;
        }

        return octal.toString();
    }

    public static String convertToHexadecimal(double number, int decimalPlaces) {
        // 1. Handle the integer part
        long integerPart = (long) number;
        double fractionalPart = number - integerPart;

        StringBuilder hex = new StringBuilder();
        hex.append(Long.toHexString(integerPart));
        hex.append(".");

        // 2. Handle the fractional part
        if (integerPart == number) return hex.toString();
        while (decimalPlaces > 0) {
            fractionalPart *= 16;
            int bit = (int) fractionalPart;
            hex.append(bit);

            // Remove the 1 if it was added to the string
            fractionalPart -= bit;
            decimalPlaces--;

            // Optimization: if fraction becomes 0, we can stop early
            if (fractionalPart == 0) break;
        }

        return hex.toString();
    }
}
