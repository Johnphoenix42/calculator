package calculator.operator;

import calculator.CalculatorApp;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class OverlayPane extends GridPane {

    public OverlayPane(){
        super();
        setBackground(CalculatorApp.ROOT_BACKGROUND);
        setPadding(new Insets(10));
        setMaxHeight(200);
        StackPane.setAlignment(this, Pos.BOTTOM_CENTER);
    }

    public void addCloseButton() {
        Button button = new Button("⋁");
        button.setMaxSize(30, 30);
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setValignment(button, VPos.TOP);
        GridPane.setHgrow(button, Priority.ALWAYS);
        GridPane.setFillWidth(button, true);
        button.setTranslateY(-25);
        button.setBackground(new Background(new BackgroundFill(Color.AQUA, new CornerRadii(15), null)));
        add(button, 2, 0);
    }
}
