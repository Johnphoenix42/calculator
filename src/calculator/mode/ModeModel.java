package calculator.mode;

public class ModeModel {

    private TrigMode angleMode;
    private AnswerNotationType notationType;
    private AnswerRadix answerRadix;

    public ModeModel() {
        angleMode = TrigMode.RADIANS;
        notationType = AnswerNotationType.STANDARD;
        answerRadix = AnswerRadix.DECIMAL;
    }

    public void setAngleMode(TrigMode mode) {
        angleMode = mode;
    }

    public TrigMode getAngleMode() {
        return angleMode;
    }

    public AnswerNotationType getNotationType() {
        return notationType;
    }

    public void setNotationType(AnswerNotationType notationType) {
        this.notationType = notationType;
    }

    public AnswerRadix getAnswerRadix() {
        return answerRadix;
    }

    public void setAnswerRadix(AnswerRadix answerRadix) {
        this.answerRadix = answerRadix;
    }

    public enum TrigMode implements ModeConstant { DEGREES, RADIANS;

        TrigMode() {}
    }

    public enum AnswerNotationType implements ModeConstant { STANDARD, SCIENTIFIC;

        AnswerNotationType() {}

    }

    public enum AnswerRadix implements ModeConstant { BINARY, DECIMAL, OCTAL, HEXADECIMAL;

        AnswerRadix() {}

    }

    public interface ModeConstant {}
}
