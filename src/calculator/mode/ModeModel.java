package calculator.mode;

public class ModeModel {

    private TrigMode angleMode;
    private AnswerNotationType notationType;
    private AnswerRadix answerRadix;

    public ModeModel() {
        angleMode = TrigMode.RADIANS;
        notationType = AnswerNotationType.STANDARD;
        System.out.println(">>Quick test - What are enum declarations initialized to automatically? \n"+
                "answerRadix is initialized to BINARY?: " + answerRadix);
        answerRadix = AnswerRadix.BINARY;
    }

    public void setAngleMode(TrigMode mode) {
        angleMode = mode;
    }

    public TrigMode getAngleMode() {
        return TrigMode.RADIANS;
    }

    public AnswerNotationType getNotationType() {
        return notationType;
    }

    public void setNotationType(AnswerNotationType notationType) {
        this.notationType = notationType;
    }

    public enum TrigMode { DEGREES, RADIANS }

    public enum AnswerNotationType { STANDARD, SCIENTIFIC }

    public enum AnswerRadix { BINARY, DECIMAL, OCTAL, HEXADECIMAL }
}
