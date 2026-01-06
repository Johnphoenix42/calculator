package calculator.mode;

import calculator.buttons.ControlButton;
import javafx.animation.FadeTransition;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.LinkedList;

public class ModeView implements OverlayView {

    private final GridPane parentPane;
    private final LinkedList<ControlButton> modeButtons;
    private final Label angleLabel, notationLabel, radixLabel;
    private final ControlButton angleToggleDegrees, angleToggleRadians;
    private final ControlButton standardNotationToggle, scientificNotationToggle;
    private final ControlButton radix2Toggle, radix8Toggle, radix10Toggle, radix16Toggle;

    private final ModeModel modeData;

    public ModeView(GridPane pane, ModeModel modeData) {
        this.parentPane = pane;
        this.modeData = modeData;
        modeButtons = new LinkedList<>();
        angleLabel = new Label("angle");
        angleLabel.setTextFill(Color.web("#bbb"));
        notationLabel = new Label("notation");
        notationLabel.setTextFill(Color.web("#bbb"));
        radixLabel = new Label("radix");
        radixLabel.setTextFill(Color.web("#bbb"));
        angleToggleDegrees = new ControlButton("Degrees", 0, 1);
        angleToggleRadians = new ControlButton("Radians", 1, 1);
        standardNotationToggle = new ControlButton("Dec", 3, 1);
        scientificNotationToggle = new ControlButton("E10", 4, 1);
        radix2Toggle = new ControlButton("2", 1, 2);
        radix8Toggle = new ControlButton("8", 2, 2);
        radix10Toggle = new ControlButton("10", 3, 2);
        radix16Toggle = new ControlButton("16", 4, 2);

        modeButtons.add(angleToggleDegrees);
        modeButtons.add(angleToggleRadians);
        modeButtons.add(standardNotationToggle);
        modeButtons.add(scientificNotationToggle);
    }

    @Override
    public void show() {
        parentPane.getChildren().clear();

        ColumnConstraints colConstraints = new ColumnConstraints(40, 50, 60, Priority.ALWAYS, HPos.LEFT, false);
        parentPane.getColumnConstraints().add(colConstraints);

        parentPane.add(angleLabel, 0, 0);
        parentPane.add(notationLabel, 3, 0);
        parentPane.add(radixLabel, 0, 2);
        parentPane.add(angleToggleDegrees, angleToggleDegrees.getColumn(), angleToggleDegrees.getRow());
        parentPane.add(angleToggleRadians, angleToggleRadians.getColumn(), angleToggleRadians.getRow());
        parentPane.add(standardNotationToggle, standardNotationToggle.getColumn(), standardNotationToggle.getRow());
        parentPane.add(scientificNotationToggle, scientificNotationToggle.getColumn(), scientificNotationToggle.getRow());
        parentPane.add(radix2Toggle, radix2Toggle.getColumn(), radix2Toggle.getRow());
        parentPane.add(radix8Toggle, radix8Toggle.getColumn(), radix8Toggle.getRow());
        parentPane.add(radix10Toggle, radix10Toggle.getColumn(), radix10Toggle.getRow());
        parentPane.add(radix16Toggle, radix16Toggle.getColumn(), radix16Toggle.getRow());

        ToggleGroup angleToggleGroup = new ToggleGroup();
        angleToggleGroup.getToggles().addAll(angleToggleDegrees, angleToggleRadians);
        angleToggleGroup.selectToggle(angleToggleRadians);

        ToggleGroup notationsToggleGroup = new ToggleGroup();
        notationsToggleGroup.getToggles().addAll(standardNotationToggle, scientificNotationToggle);
        notationsToggleGroup.selectToggle(scientificNotationToggle);

        ToggleGroup radixToggleGroup = new ToggleGroup();
        radixToggleGroup.getToggles().addAll(radix2Toggle, radix8Toggle, radix10Toggle, radix16Toggle);
        radixToggleGroup.selectToggle(radix10Toggle);
    }

    @Override
    public void close() {
    }

    public LinkedList<ControlButton> getModeButtons() {
        return modeButtons;
    }
}
