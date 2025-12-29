package calculator.mode;

import calculator.buttons.ControlButton;
import javafx.animation.FadeTransition;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.LinkedList;

public class ModeView {

    private GridPane parentPane;
    private LinkedList<ControlButton> modeButtons;
    private ControlButton angleToggleDegrees;
    private ControlButton angleToggleRadians;

    private FadeTransition fadeTransition;

    private ModeModel modeData;

    public ModeView(GridPane pane, ModeModel modeData) {
        this.parentPane = pane;
        this.modeData = modeData;
        modeButtons = new LinkedList<>();
        angleToggleDegrees = new ControlButton("Degrees", 0, 0);
        angleToggleRadians = new ControlButton("Radians", 1, 0);

        modeButtons.add(angleToggleDegrees);
        modeButtons.add(angleToggleRadians);

        fadeTransition = new FadeTransition(Duration.millis(1000), pane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);
    }

    public void show() {
        parentPane.getChildren().clear();
        parentPane.add(angleToggleDegrees, angleToggleDegrees.getColumn(), angleToggleDegrees.getRow());
        parentPane.add(angleToggleRadians, angleToggleRadians.getColumn(), angleToggleRadians.getRow());
        ToggleGroup angleToggleGroup = new ToggleGroup();
        angleToggleGroup.getToggles().addAll(angleToggleDegrees, angleToggleRadians);
        angleToggleGroup.selectToggle(angleToggleRadians);
    }

    public LinkedList<ControlButton> getModeButtons() {
        return modeButtons;
    }
}
