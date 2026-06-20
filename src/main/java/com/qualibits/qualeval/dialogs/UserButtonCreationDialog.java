package com.qualibits.qualeval.dialogs;

import com.qualibits.qualeval.AppSetting;
import com.qualibits.qualeval.MainApp;
import com.qualibits.qualeval.buttons.TermButton;
import com.qualibits.qualeval.dialoglayout.ConstantCreationGridPane;
import com.qualibits.qualeval.exec.ExpressionParser;
import com.qualibits.qualeval.term.Operand;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.function.Consumer;

public class UserButtonCreationDialog {

    private static final AppSetting appSetting = AppSetting.getSettings();
    private final Dialog<UserConstant> dialog;

    private final UserDefinedButtonOkAction constantCreationOkAction;
    private static HashMap<String, TermButton<Operand>> userCreatedButtonsMap;

    public record UserConstant(String name, Double value){}

    public UserButtonCreationDialog(ConstantCreationGridPane constantGridPane) {
        userCreatedButtonsMap = new HashMap<>();
        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog = new Dialog<>();
        dialog.setTitle("Create User Operand");
        dialog.getDialogPane().setContent(constantGridPane);
        dialog.getDialogPane().getButtonTypes().add(createButtonType);
        ((Button) dialog.getDialogPane().lookupButton(createButtonType)).setBackground(MainApp.ROOT_BACKGROUND);
        dialog.setResultConverter((resultButtonType) -> new UserConstant(
                constantGridPane.getNameField().getText(), constantGridPane.getValueField().getValue()
        ));
        dialog.initModality(Modality.APPLICATION_MODAL);

        constantCreationOkAction = new UserDefinedButtonOkAction();

    }

    public void show(){
        dialog.showAndWait().filter(constant-> !constant.name().isEmpty() && !constant.value().isNaN())
                .ifPresent(constantCreationOkAction);
    }

    public UserDefinedButtonOkAction getUserDefinedButtonOkAction() {
        return constantCreationOkAction;
    }

    public void setUserCreatedButtonsMap(HashMap<String, TermButton<Operand>> userCreatedButtonsMap) {
        UserButtonCreationDialog.userCreatedButtonsMap = userCreatedButtonsMap;
    }

    public HashMap<String, TermButton<Operand>> getUserButtonsMap() {
        return userCreatedButtonsMap;
    }

    public static class UserDefinedButtonOkAction implements Consumer<UserConstant> {

        private static final int USER_OPERAND_BUTTON_ROW_START = 3;
        private static final int USER_OPERAND_BUTTON_COLUMN_START = 7;
        private static final int USER_OPERAND_BUTTON_ROW_COUNT = 3;
        private static final int USER_OPERAND_BUTTON_COLUMN_COUNT = 3;

        private final ExpressionParser parser;
        private GridPane mainSetupGrid;
        private TextField computeScreen, expressionScreen;

        private int userCreatedButtonIndex = 0;

        public UserDefinedButtonOkAction() {
            parser = appSetting.getParser();
        }

        @Override
        public void accept(UserConstant constant) {
            var userCreatedOperand = new Operand(BigDecimal.valueOf(constant.value()).doubleValue(), constant.name());
            TermButton<Operand> button = createUserButton(userCreatedOperand, constant);
            userCreatedButtonsMap.put(constant.name(), button);
            userCreatedButtonIndex++;
        }

        private TermButton<Operand> createUserButton(Operand userCreatedOperand, UserConstant constant) {
            TermButton<Operand> userCreatedButton = new TermButton<>(constant.name(), buttonEvent -> {
                boolean shouldUseVariableName = appSetting.getScreenSettings().shouldUseVariableName();
                expressionScreen.setText(parser.printExpressionQueue(userCreatedOperand));
                computeScreen.setText(shouldUseVariableName ? userCreatedOperand.getDenotation() : userCreatedOperand.toString());
            }, userCreatedOperand,
                    USER_OPERAND_BUTTON_COLUMN_START + (userCreatedButtonIndex % USER_OPERAND_BUTTON_COLUMN_COUNT),
                    USER_OPERAND_BUTTON_ROW_START + (userCreatedButtonIndex / USER_OPERAND_BUTTON_ROW_COUNT));
            mainSetupGrid.add(userCreatedButton, userCreatedButton.getColumn(), userCreatedButton.getRow());
            return userCreatedButton;
        }

        public void setExpressionScreen(TextField expressionScreen) {
            this.expressionScreen = expressionScreen;
        }

        public void setMainSetupGrid(GridPane mainSetupGrid) {
            this.mainSetupGrid = mainSetupGrid;
        }

        public void setComputeScreen(TextField computeScreen) {
            this.computeScreen = computeScreen;
        }
    }
}
