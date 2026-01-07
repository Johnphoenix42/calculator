package calculator.mode;

public class ModeModel {

    private TrigMode angleMode;
    private AnswerNotationType notationType;
    private AnswerRadix answerRadix;

    public ModeModel() {
        angleMode = TrigMode.RADIANS;
        notationType = AnswerNotationType.STANDARD;
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

    public enum TrigMode { DEGREES("trig_deg"), RADIANS("trig_rad");

        private final String id;

        TrigMode(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    public enum AnswerNotationType { STANDARD("notation_standard"), SCIENTIFIC("notation_scientific");

        private final String id;

        AnswerNotationType(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    public enum AnswerRadix { BINARY("radix_bin"), DECIMAL("radix_dec"), OCTAL("radix_oct"), HEXADECIMAL("radix_hex");

        private final String id;

        AnswerRadix(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}
