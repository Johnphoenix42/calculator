package calculator.buttons;

import calculator.mode.ModeModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ControlButton extends ToggleButton {

    private static final Background NORMAL_BACKGROUND = new Background(
            new BackgroundFill(Color.web("0f0f0f"), null, null));
    public static final Background ACCENT_BACKGROUND = new Background(
            new BackgroundFill(Color.color(0.3, .3, .3), null, null));

    private final String name;
    private final EventHandler<ActionEvent> eHandler;
    private final int column;
    private final int row;
    private final int colSpan;
    private final int rowSpan;
    private ModeModel.ModeConstant modeConstant;

    private static ModeModel modeModel;

    public ControlButton(String name, EventHandler<ActionEvent> eHandler, int column, int row, int colSpan, int rowSpan){
        this.name = name;
        this.eHandler = eHandler;
        this.column = column;
        this.row = row;
        this.colSpan = colSpan;
        this.rowSpan = rowSpan;
        setText(name);
        setTextFill(Color.color(1, 1, 1));
        setFont(Font.font(15));
        setMinSize(60, 30);
        setMaxSize(100, Double.MAX_VALUE);
        setBackground(NORMAL_BACKGROUND);
        setOnMouseEntered(e -> {
            setEffect(new Glow(1));
        });
        setOnMouseExited(e -> {
            setEffect(new Glow(0));
        });
        selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                setBackground(ACCENT_BACKGROUND);
                if (modeConstant instanceof ModeModel.TrigMode) {
                    modeModel.setAngleMode((ModeModel.TrigMode) modeConstant);
                    modeConstant.setButton(this);
                }
                else if (modeConstant instanceof ModeModel.AnswerNotationType) {
                    modeModel.setNotationType((ModeModel.AnswerNotationType) modeConstant);
                    modeConstant.setButton(this);
                }
                else if (modeConstant instanceof ModeModel.AnswerRadix) {
                    modeModel.setAnswerRadix((ModeModel.AnswerRadix) modeConstant);
                    modeConstant.setButton(this);
                }
            } else {
                setBackground(NORMAL_BACKGROUND);
            }
        });
    }

    /**
     * This variant leaves out onActionEventHandler function for the user, defaulting to what
     * is specified in onHostClickAction implemented by SubTypes of Term interface
     * @param name
     * @param constant
     * @param column
     * @param row
     */
    public ControlButton(String name, ModeModel.ModeConstant constant, int column, int row) {
        this(name, e -> {}, column, row, 1, 1);
        this.modeConstant = constant;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public int getColSpan() {
        return colSpan;
    }

    public int getRowSpan() {
        return rowSpan;
    }

    public ModeModel.ModeConstant getModeConstant() {
        return modeConstant;
    }

    public EventHandler<ActionEvent> geteHandler() {
        return eHandler;
    }

    public String getName() {
        return name;
    }

    public static void setModeData(ModeModel modeData) {
        modeModel = modeData;
    }

}
