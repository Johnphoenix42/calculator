package com.qualibits.qualeval;

import com.qualibits.qualeval.dialoglayout.ConstantCreationGridPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Modality;


public class AppMenu extends MenuBar {

    AppSetting appSetting;
    Menu createMenu;
    Menu displayMenu;
    Menu settingsMenu;
    Menu helpMenu;

    private GridPane mainSetupGrid;
    private TextField computeScreen, expressionScreen;

    AppMenu() {
        super();

        appSetting = AppSetting.getSettings();
        createMenu = createMenu("Create");
        settingsMenu = createMenu("Settings");


        setBackground(new Background(new BackgroundFill(
                new LinearGradient(0, 1, 1, 1, true, CycleMethod.REFLECT,
                        new Stop(0, Color.NAVY), new Stop(0.5, Color.web("#333"))), null, null)));

    }

    public void populate() {
        ConstantCreationGridPane.UserDefinedButtonOkAction constantCreationOkAction = new ConstantCreationGridPane.UserDefinedButtonOkAction(appSetting);
        constantCreationOkAction.setComputeScreen(computeScreen);
        constantCreationOkAction.setExpressionScreen(expressionScreen);
        constantCreationOkAction.setMainSetupGrid(mainSetupGrid);
        Menu createMenuItem = createMenu("User Operands", Color.BLACK);
        addMenuItem("As Buttons", event -> {
            ConstantCreationGridPane constantGridPane = new ConstantCreationGridPane();
            ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
            Dialog<ConstantCreationGridPane.UserConstant> dialog = new Dialog<>();
            dialog.setTitle("Create User Operand");
            dialog.getDialogPane().setContent(constantGridPane);
            dialog.getDialogPane().getButtonTypes().add(createButtonType);
            ((Button) dialog.getDialogPane().lookupButton(createButtonType)).setBackground(MainApp.ROOT_BACKGROUND);
            dialog.setResultConverter((resultButtonType) -> new ConstantCreationGridPane.UserConstant(constantGridPane.getNameField().getText(),
                    constantGridPane.getValueField().getValue()));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.showAndWait().filter(constant-> !constant.name().isEmpty() && !constant.value().isNaN())
                    .ifPresent(constantCreationOkAction);
        }, createMenuItem, createMenu);

        displayMenu = createMenu("Display");
        addMenuItem("Show User Operands As Variables", false, opAsVarEvent -> {
            CheckMenuItem menuItem = (CheckMenuItem) opAsVarEvent.getSource();
            boolean isChecked = menuItem.isSelected();
            appSetting.getScreenSettings().setUseVariableName(isChecked);
        }, displayMenu);
        Menu turnoffNormalization = createMenu("Turn off Normalization");
        addMenuItem("Display only", false, opAsVarEvent -> {
            CheckMenuItem menuItem = (CheckMenuItem) opAsVarEvent.getSource();
            boolean isChecked = menuItem.isSelected();
            appSetting.getScreenSettings().setTurnOffNormalizationForDisplay(isChecked);
        }, turnoffNormalization, displayMenu);
        addMenuItem("Completely", false, opAsVarEvent -> {
            CheckMenuItem menuItem = (CheckMenuItem) opAsVarEvent.getSource();
            boolean isChecked = menuItem.isSelected();
            appSetting.getScreenSettings().setTurnOffNormalizationCompletely(isChecked);
        }, turnoffNormalization, displayMenu);

        helpMenu = createMenu("Help");
        MenuItem aboutMenuItem = new MenuItem("About");
        aboutMenuItem.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "Qual-Eval v1.1.3\nOpen source calculator\nContributors\nJohn Ibiwoye",  ButtonType.CLOSE);
            alert.showAndWait();
        });
        helpMenu.getItems().add(aboutMenuItem);
        Menu[] appMenu = {createMenu, displayMenu, settingsMenu, helpMenu};
        getMenus().addAll(appMenu);
    }

    private Menu createMenu(String menuName) {
        Label label = new Label(menuName);
        label.setTextFill(Color.WHITE);
        Menu menu = new Menu("", label);
        menu.setStyle("-fx-focus-color: green");
        return menu;
    }

    private Menu createMenu(String menuName, Paint color) {
        Label label = new Label(menuName);
        label.setTextFill(color);
        Menu menu = new Menu("", label);
        menu.setStyle("-fx-focus-color: green");
        return menu;
    }

    /**
     * This method adds a MenuItem to the menu tree. The menu is modeled as a tree with menuItems as
     * external nodes and menus as internal nodes. The ancestor of each MenuItem that is to be added
     * is specified in parent in order of decreasing depth, i.e., immediate parent along the path
     * is specified first and the root (if present), last.
     * @param menuItemName The name of the MenuItem to be created
     * @param handler The ActionEvent handler that is to be bound to it.
     * @param parent The ancestor menu(s) that it should be added under.
     */
    private void addMenuItem(String menuItemName, EventHandler<ActionEvent> handler, Menu... parent){
        MenuItem menuItem = new MenuItem(menuItemName);
        menuItem.setOnAction(handler);
        for (int i = 1; i < parent.length; ++i) {
            if (!parent[i].getItems().contains(parent[i - 1])) {
                parent[i].getItems().add(parent[i - 1]);
                parent[i].setStyle("fx-background-color: black");
            }
        }
        parent[0].getItems().add(menuItem);
    }

    /**
     * This method adds a CheckMenuItem to the menu tree. The menu is modeled as a tree with menuItems as
     * external nodes and menus as internal nodes. The ancestor of each MenuItem that is to be added
     * is specified in parent in order of decreasing depth, i.e., immediate parent along the path
     * is specified first and the root (if present), last.
     * @param menuItemName The name of the CheckMenuItem to be created
     * @param handler The ActionEvent handler that is to be bound to it.
     * @param parent The ancestor menu(s) that it should be added under.
     */
    private void addMenuItem(String menuItemName, boolean checked, EventHandler<ActionEvent> handler, Menu... parent){
        CheckMenuItem menuItem = new CheckMenuItem(menuItemName);
        menuItem.setSelected(checked);
        menuItem.setOnAction(handler);
        for (int i = 1; i < parent.length; ++i) {
            if (!parent[i].getItems().contains(parent[i - 1])) {
                parent[i].getItems().add(parent[i - 1]);
                parent[i].setStyle("fx-background-color: black");
            }
        }
        parent[0].getItems().add(menuItem);
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
