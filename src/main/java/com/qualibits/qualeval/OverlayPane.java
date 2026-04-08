package com.qualibits.qualeval;

import com.qualibits.qualeval.mode.OverlayView;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class OverlayPane extends GridPane {

    private static final int GRID_ROW_PARTITION = 5;
    private static final int GRID_COL_PARTITION = 5;

    private final Pane parentPane;
    private OverlayView<? extends Node> currentOverlayView;
    private final Button closeButton;

    private final FadeTransition fadeInTransition, fadeOutTransition;
    private final TranslateTransition translateInTransition, translateOutTransition;
    private boolean isTransitioning = false;

    public OverlayPane(Pane parentPane){
        super();
        this.parentPane = parentPane;
        setBackground(new Background(new BackgroundFill(Color.color(.1, .1, 0, 0.8), null, null)));
        setPadding(new Insets(0, 10, 0, 10));
        setMaxHeight(Double.MAX_VALUE);
        setHgap(5);
        setVgap(5);
        for (int i = 0; i < GRID_COL_PARTITION; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints(50, 70, 70, Priority.NEVER, HPos.LEFT, false);
            colConstraints.setPercentWidth(50);
            getColumnConstraints().add(colConstraints);
        }
        StackPane.setAlignment(this, Pos.CENTER_RIGHT);
        closeButton = new Button("⋁");
        closeButton.setPrefSize(30, 30);
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
        if (getChildren().contains(closeButton)) return;
        GridPane.setHalignment(closeButton, HPos.CENTER);
        GridPane.setValignment(closeButton, VPos.TOP);
        GridPane.setHgrow(closeButton, Priority.ALWAYS);
        closeButton.setTranslateY(-25);
        closeButton.setBackground(new Background(new BackgroundFill(Color.AQUA, new CornerRadii(15), null)));
        add(closeButton, 0, 0, 10, 1);
    }

    public void setView(OverlayView<? extends Node> view) {
        currentOverlayView = view;
    }

    public void show() {
        show(currentOverlayView);
    }

    public <T extends Node> void show(OverlayView<T> view) {
        if (view == null) throw new NullPointerException("view cannot be null");
        ObservableList<Node> parentChildren = parentPane.getChildren();
        if (!parentChildren.contains(this)) parentChildren.add(this);
        fadeInTransition.play();
        translateInTransition.play();
        if (currentOverlayView == view) return;
        if (currentOverlayView != null) currentOverlayView.close();
        T tPane = view.show();
        if (tPane instanceof Node pane) {
            add(pane, view.getRow(), view.getCol(), view.getRowSpan(GRID_ROW_PARTITION), view.getColSpan(GRID_COL_PARTITION));
        } else throw new ClassCastException("View must be a subclass of Node");
        currentOverlayView = view;
    }

    public void onClose() {
        // if in the middle of a transition, do nothing;
        if (isTransitioning) return;
        fadeOutTransition.play();
        translateOutTransition.play();
        isTransitioning = true;
        fadeOutTransition.setOnFinished(e -> {
            parentPane.getChildren().remove(this);
            isTransitioning = false;
        });
    }

}
