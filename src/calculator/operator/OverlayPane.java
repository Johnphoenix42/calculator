package calculator.operator;

import calculator.CalculatorApp;
import calculator.mode.OverlayView;
import javafx.animation.FadeTransition;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class OverlayPane extends GridPane {

    private OverlayView overlayView;
    private final Button closeButton;

    private final FadeTransition fadeTransition;

    public OverlayPane(){
        super();
        setBackground(CalculatorApp.ROOT_BACKGROUND);
        setPadding(new Insets(10));
        setMaxHeight(200);
        setHgap(5);
        setVgap(5);
        StackPane.setAlignment(this, Pos.BOTTOM_CENTER);
        closeButton = new Button("⋁");
        closeButton.setMaxSize(30, 30);
        closeButton.setOnAction(e -> onClose());

        fadeTransition = new FadeTransition(Duration.millis(500), this);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);
    }

    public void addCloseButton() {
        GridPane.setHalignment(closeButton, HPos.CENTER);
        GridPane.setValignment(closeButton, VPos.TOP);
        GridPane.setHgrow(closeButton, Priority.ALWAYS);
        closeButton.setTranslateY(-25);
        closeButton.setBackground(new Background(new BackgroundFill(Color.AQUA, new CornerRadii(15), null)));
        add(closeButton, 0, 0, 10, 1);
    }

    public <T extends OverlayView> void setView(T t) {
        overlayView = t;
    }

    public void show() throws NullPointerException {
        if (overlayView == null) throw new NullPointerException("Call setView(T t) first before this");
        overlayView.show();
        fadeTransition.play();
    }

    public void onClose() {
        if (overlayView == null) throw new NullPointerException("Call setView(T t) first before this");
        overlayView.close();
        fadeTransition.play();
        //fadeTransition.setOnFinished(t.);
        getChildren().clear();
    }

}
