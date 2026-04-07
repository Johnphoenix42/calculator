package com.qualibits.qualeval.mode;

import com.qualibits.qualeval.buttons.ControlToggleButton;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class ModeView extends GridPane implements OverlayView<GridPane> {

    private final GridPane parentPane;
    private final LinkedList<ControlToggleButton> modeButtons;
    private final Label angleLabel, notationLabel, radixLabel;
    private final ControlToggleButton angleToggleDegrees, angleToggleRadians;
    private final ControlToggleButton standardNotationToggle, scientificNotationToggle;
    private final ControlToggleButton radix2Toggle, radix8Toggle, radix10Toggle, radix16Toggle;

    private static ModeModel modeData;

    public ModeView(GridPane pane, ModeModel modeData) {
        this.parentPane = pane;
        modeButtons = new LinkedList<>();
        angleLabel = new Label("angle");
        angleLabel.setTextFill(Color.web("#bbb"));
        notationLabel = new Label("notation");
        notationLabel.setTextFill(Color.web("#bbb"));
        radixLabel = new Label("radix");
        radixLabel.setTextFill(Color.web("#bbb"));
        angleToggleDegrees = new ControlToggleButton("Degrees", ModeModel.TrigMode.DEGREES, 0, 1);
        angleToggleRadians = new ControlToggleButton("Radians", ModeModel.TrigMode.RADIANS, 1, 1);
        modeData.getAngleMode().setButton(angleToggleDegrees);
        standardNotationToggle = new ControlToggleButton("Dec", ModeModel.AnswerNotationType.STANDARD, 3, 1);
        scientificNotationToggle = new ControlToggleButton("E10", ModeModel.AnswerNotationType.SCIENTIFIC, 4, 1);
        modeData.getNotationType().setButton(standardNotationToggle);
        radix2Toggle = new ControlToggleButton("2", ModeModel.AnswerRadix.BINARY, 1, 2);
        radix8Toggle = new ControlToggleButton("8", ModeModel.AnswerRadix.OCTAL, 2, 2);
        radix10Toggle = new ControlToggleButton("10", ModeModel.AnswerRadix.DECIMAL, 3, 2);
        radix16Toggle = new ControlToggleButton("16", ModeModel.AnswerRadix.HEXADECIMAL, 4, 2);
        modeData.getAnswerRadix().setButton(radix10Toggle);

        modeButtons.add(angleToggleDegrees);
        modeButtons.add(angleToggleRadians);
        modeButtons.add(standardNotationToggle);
        modeButtons.add(scientificNotationToggle);

        ControlToggleButton.setModeData(modeData);
    }

    public static void setModeData(ModeModel modeData) {
        ModeView.modeData = modeData;
    }

    @Override
    public GridPane show() {
        ColumnConstraints colConstraints = new ColumnConstraints(40, 50, 60, Priority.ALWAYS, HPos.LEFT, false);
        getColumnConstraints().add(colConstraints);

        add(angleLabel, 0, 0);
        add(notationLabel, 3, 0);
        add(radixLabel, 0, 2);
        add(angleToggleDegrees, angleToggleDegrees.getColumn(), angleToggleDegrees.getRow());
        add(angleToggleRadians, angleToggleRadians.getColumn(), angleToggleRadians.getRow());
        add(standardNotationToggle, standardNotationToggle.getColumn(), standardNotationToggle.getRow());
        add(scientificNotationToggle, scientificNotationToggle.getColumn(), scientificNotationToggle.getRow());
        add(radix2Toggle, radix2Toggle.getColumn(), radix2Toggle.getRow());
        add(radix8Toggle, radix8Toggle.getColumn(), radix8Toggle.getRow());
        add(radix10Toggle, radix10Toggle.getColumn(), radix10Toggle.getRow());
        add(radix16Toggle, radix16Toggle.getColumn(), radix16Toggle.getRow());

        ToggleGroup angleToggleGroup = new ToggleGroup();
        angleToggleGroup.getToggles().addAll(angleToggleDegrees, angleToggleRadians);
        ControlToggleButton selectedAngleButton = modeData.getAngleMode().getButton();
        selectedAngleButton.setBackground(ControlToggleButton.ACCENT_BACKGROUND);
        angleToggleGroup.selectToggle(selectedAngleButton);

        ToggleGroup notationsToggleGroup = new ToggleGroup();
        notationsToggleGroup.getToggles().addAll(standardNotationToggle, scientificNotationToggle);
        ControlToggleButton selectedNotationButton = modeData.getNotationType().getButton();
        selectedNotationButton.setBackground(ControlToggleButton.ACCENT_BACKGROUND);
        notationsToggleGroup.selectToggle(selectedNotationButton);

        ToggleGroup radixToggleGroup = new ToggleGroup();
        radixToggleGroup.getToggles().addAll(radix2Toggle, radix8Toggle, radix10Toggle, radix16Toggle);
        ControlToggleButton selectedAnswerButton = modeData.getAnswerRadix().getButton();
        selectedAnswerButton.setBackground(ControlToggleButton.ACCENT_BACKGROUND);
        radixToggleGroup.selectToggle(selectedAnswerButton);
        return this;
    }

    @Override
    public void close() {
    }

    @Override
    public int getRow() {
        return 0;
    }

    @Override
    public int getCol() {
        return 0;
    }

    @Override
    public int getRowSpan() {
        return 12;
    }

    @Override
    public int getColSpan() {
        return 1;
    }

    public LinkedList<ControlToggleButton> getModeButtons() {
        return modeButtons;
    }

}
