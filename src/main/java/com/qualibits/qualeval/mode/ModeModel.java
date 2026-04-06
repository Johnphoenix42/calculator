package com.qualibits.qualeval.mode;

import com.qualibits.qualeval.buttons.ControlToggleButton;

public class ModeModel {

    private TrigMode angleMode;
    private AnswerNotationType notationType;
    private AnswerRadix answerRadix;

    public ModeModel() {
        angleMode = TrigMode.DEGREES;
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

        ControlToggleButton selectedButton;

        TrigMode() {}

        @Override
        public ControlToggleButton getButton() {
            return selectedButton;
        }

        @Override
        public void setButton(ControlToggleButton controlButton) {
            this.selectedButton = controlButton;
        }
    }

    public enum AnswerNotationType implements ModeConstant { STANDARD, SCIENTIFIC;

        ControlToggleButton selectedButton;

        AnswerNotationType() {}

        @Override
        public ControlToggleButton getButton() {
            return selectedButton;
        }

        @Override
        public void setButton(ControlToggleButton controlToggleButton) {
            this.selectedButton = controlToggleButton;
        }
    }

    public enum AnswerRadix implements ModeConstant { BINARY, DECIMAL, OCTAL, HEXADECIMAL;

        ControlToggleButton selectedButton;

        AnswerRadix() {}

        @Override
        public ControlToggleButton getButton() {
            return selectedButton;
        }

        @Override
        public void setButton(ControlToggleButton controlToggleButton) {
            this.selectedButton = controlToggleButton;
        }
    }

    public interface ModeConstant {

        ControlToggleButton getButton();

        void setButton(ControlToggleButton controlToggleButton);
    }
}
