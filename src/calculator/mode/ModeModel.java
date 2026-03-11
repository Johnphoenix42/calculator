package calculator.mode;

import calculator.buttons.ControlButton;

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

        ControlButton selectedButton;

        TrigMode() {}

        @Override
        public ControlButton getButton() {
            return selectedButton;
        }

        @Override
        public void setButton(ControlButton controlButton) {
            this.selectedButton = controlButton;
        }
    }

    public enum AnswerNotationType implements ModeConstant { STANDARD, SCIENTIFIC;

        ControlButton selectedButton;

        AnswerNotationType() {}

        @Override
        public ControlButton getButton() {
            return selectedButton;
        }

        @Override
        public void setButton(ControlButton controlButton) {
            this.selectedButton = controlButton;
        }
    }

    public enum AnswerRadix implements ModeConstant { BINARY, DECIMAL, OCTAL, HEXADECIMAL;

        ControlButton selectedButton;

        AnswerRadix() {}

        @Override
        public ControlButton getButton() {
            return selectedButton;
        }

        @Override
        public void setButton(ControlButton controlButton) {
            this.selectedButton = controlButton;
        }
    }

    public interface ModeConstant {

        ControlButton getButton();

        void setButton(ControlButton controlButton);
    }
}
