package com.qualibits.jeval;

import com.qualibits.jeval.mode.OverlayView;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class OverlayPane extends GridPane {

    private final Pane parentPane;
    private OverlayView overlayView;
    private final Button closeButton;

    private final FadeTransition fadeInTransition, fadeOutTransition;
    private final TranslateTransition translateInTransition, translateOutTransition;

    public OverlayPane(Pane parentPane){
        super();
        this.parentPane = parentPane;
        setBackground(MainApp.ROOT_BACKGROUND);
        setPadding(new Insets(10));
        setMaxHeight(150);
        setHgap(5);
        setVgap(5);
        StackPane.setAlignment(this, Pos.BOTTOM_CENTER);
        closeButton = new Button("⋁");
        closeButton.setMaxSize(30, 30);
        closeButton.setOnAction(e -> onClose());

        fadeInTransition = new FadeTransition(Duration.millis(500), this);
        fadeInTransition.setFromValue(0);
        fadeInTransition.setToValue(1);
        fadeInTransition.setCycleCount(1);
        fadeInTransition.setAutoReverse(false);
        fadeOutTransition = new FadeTransition(Duration.millis(500), this);
        fadeOutTransition.setFromValue(1);
        fadeOutTransition.setToValue(0);
        fadeOutTransition.setCycleCount(1);
        fadeOutTransition.setAutoReverse(false);

        translateInTransition = new TranslateTransition(Duration.millis(500), this);
        translateInTransition.setFromY(100);
        translateInTransition.setByY(-100);
        translateInTransition.setCycleCount(1);

        translateOutTransition = new TranslateTransition(Duration.millis(500), this);
        translateOutTransition.setFromY(0);
        translateOutTransition.setByY(100);
        translateOutTransition.setCycleCount(1);

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
        fadeInTransition.play();
        translateInTransition.play();
    }

    public void onClose() {
        if (overlayView == null) throw new NullPointerException("Call setView(T t) first before this");
        overlayView.close();
        fadeOutTransition.play();
        translateOutTransition.play();
        fadeOutTransition.setOnFinished(e -> {
            getChildren().clear();
            parentPane.getChildren().remove(this);
        });
    }

}
