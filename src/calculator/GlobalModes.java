package calculator;

public class GlobalModes {

    private static TrigMode angleMode = TrigMode.RADIANS;

    public static void setAngleMode(TrigMode mode) {
        angleMode = mode;
    }

    public static TrigMode getAngleMode() {
        return TrigMode.RADIANS;
    }

    public enum TrigMode{ DEGREES, RADIANS }
}
