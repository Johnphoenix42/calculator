package com.qualibits.qualeval.dialoglayout;

import com.qualibits.qualeval.AppSetting;
import com.qualibits.qualeval.buttons.TermButton;
import com.qualibits.qualeval.exec.ExpressionParser;
import com.qualibits.qualeval.term.Operand;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class ConstantCreationGridPane extends GridPane {

    private final TextField nameField;
    private final Spinner<Double> valueField;

    public record UserConstant(String name, Double value){}

    public ConstantCreationGridPane(){
        super();
        ColumnConstraints columnConstraints = new ColumnConstraints(40, 50, 60, Priority.ALWAYS, HPos.CENTER, true);
        getColumnConstraints().add(columnConstraints);
        for (int i = 0; i < 2; ++i) {
            getRowConstraints().add(new RowConstraints(40, 50, 60, Priority.ALWAYS, VPos.CENTER, true));
        }
        nameField = new TextField();
        valueField = new Spinner<>(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0, 0.1);
        add(new Label("Name"), 0, 0);
        add(nameField, 1, 0);
        add(new Label("Value"), 0, 1);
        add(valueField, 1, 1);

        valueField.setEditable(true);
    }

    public Spinner<Double> getValueField() {
        return valueField;
    }

    public TextField getNameField() {
        return nameField;
    }

    public static class UserDefinedButtonOkAction implements Consumer<UserConstant> {

        private static final int USER_OPERAND_BUTTON_ROW_START = 3;
        private static final int USER_OPERAND_BUTTON_COLUMN_START = 7;
        private static final int USER_OPERAND_BUTTON_ROW_COUNT = 3;
        private static final int USER_OPERAND_BUTTON_COLUMN_COUNT = 3;

        private final AppSetting appSetting;
        private GridPane mainSetupGrid;
        private TextField computeScreen, expressionScreen;
        private final ExpressionParser parser;

        private int userCreatedButtonIndex = 0;

        public UserDefinedButtonOkAction(AppSetting setting) {
            this.appSetting = setting;
            parser = appSetting.getParser();
        }

        @Override
        public void accept(UserConstant constant) {
            var userCreatedOperand = new Operand(BigDecimal.valueOf(constant.value()).doubleValue(), constant.name());
            createUserButton(userCreatedOperand, constant);
            userCreatedButtonIndex++;
        }

        private void createUserButton(Operand userCreatedOperand, UserConstant constant) {
            TermButton<Operand> userCreatedButton = new TermButton<>(constant.name(), buttonEvent -> {
                boolean shouldUseVariableName = appSetting.getScreenSettings().shouldUseVariableName();
                expressionScreen.setText(parser.printExpressionQueue(userCreatedOperand));
                computeScreen.setText(shouldUseVariableName ? userCreatedOperand.getDenotation() : userCreatedOperand.toString());
            }, userCreatedOperand,
                    USER_OPERAND_BUTTON_COLUMN_START + (userCreatedButtonIndex % USER_OPERAND_BUTTON_COLUMN_COUNT),
                    USER_OPERAND_BUTTON_ROW_START + (userCreatedButtonIndex / USER_OPERAND_BUTTON_ROW_COUNT));

            mainSetupGrid.add(userCreatedButton, userCreatedButton.getColumn(), userCreatedButton.getRow());
        }


        public void setMainSetupGrid(GridPane mainSetupGrid) {
            this.mainSetupGrid = mainSetupGrid;
        }

        public void setComputeScreen(TextField computeScreen) {
            this.computeScreen = computeScreen;
        }

        public void setExpressionScreen(TextField expressionScreen) {
            this.expressionScreen = expressionScreen;
        }

    }
}
