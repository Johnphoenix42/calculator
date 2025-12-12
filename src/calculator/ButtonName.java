package calculator;

public enum ButtonName {
    XPOWERY("xªy"),
    INVERSE("⅟x"),
    FACTORIAL("n!"),
    SQUARE("x²"),
    PI("⫪"),
    EULER("e"),
    MODULO("mod"),
    DIVISION("÷"),
    ADDITION("+"),

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
    MULTIPLICATION("⨉");


    private String name;

    ButtonName(String nameString) {
        this.name = nameString;
    }

    public String getName() {
        return name;
    }
}
