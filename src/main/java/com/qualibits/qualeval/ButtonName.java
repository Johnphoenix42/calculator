package com.qualibits.qualeval;

public enum ButtonName {
    SINH("Sinh"),
    COSH("Cosh"),
    TANH("Tanh"),
    ARC_SIN("Sin⁻¹"),
    ARC_COS("Cos⁻¹"),
    ARC_TAN("Tan⁻¹"),
    SIN("Sin"),
    COS("Cos"),
    TAN("Tan"),
    X_POWER_Y("xª"),
    INVERSE("⅟x"),
    FACTORIAL("n!"),
    SQUARE("x²"),
    PI("⫪"),
    EULER("e"),
    MODULO("mod"),
    DIVISION("÷"),
    ADDITION("+"),
    SUBTRACTION("-"),

    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    ZERO("0"),
    POINT("."),
    ANS("Ans"),
    SQUARE_ROOT("√"),
    X_ROOT("ª√"),
    MULTIPLICATION("⨉"),
    TEN_POWER_X("10^x"),
    LOG("log"),
    LN("ln"),
    OPEN_PARENTHESIS("("),
    CLOSE_PARENTHESIS(")"),
    HYPOTENUSE("Hyp");


    private String name;

    ButtonName(String nameString) {
        this.name = nameString;
    }

    public String getName() {
        return name;
    }
}
