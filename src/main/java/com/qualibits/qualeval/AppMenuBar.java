package com.qualibits.qualeval;

import com.qualibits.qualeval.buttons.TermButton;
import com.qualibits.qualeval.dialoglayout.ConstantCreationGridPane;
import com.qualibits.qualeval.dialogs.UserButtonCreationDialog;
import com.qualibits.qualeval.term.Operand;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.util.HashMap;

public class AppMenuBar extends MenuBar {

    AppSetting appSetting;
    Menu createMenu;
    Menu displayMenu;
    Menu settingsMenu;
    Menu helpMenu;

    private GridPane mainSetupGrid;
    private TextField computeScreen, expressionScreen;
    private HashMap<String, TermButton<Operand>> userCreatedButtonsMap;

    public AppMenuBar() {
        super();
        userCreatedButtonsMap = new HashMap<>();
        appSetting = AppSetting.getSettings();
        createMenu = createMenu("Customize");
        settingsMenu = createMenu("Settings");
        displayMenu = createMenu("Display");
        helpMenu = createMenu("Help");

        setBackground(new Background(new BackgroundFill(
                new LinearGradient(0, 1, 1, 1, true, CycleMethod.REFLECT,
                        new Stop(0, Color.NAVY), new Stop(0.5, Color.web("#333"))), null, null)));

    }

    public void populate() {
        Menu userOperands = createMenu("User Operands", Color.BLACK);
        addMenuItem("Create", event -> {
            ConstantCreationGridPane constantGridPane = new ConstantCreationGridPane();
            UserButtonCreationDialog userButtonCreationDialog = new UserButtonCreationDialog(constantGridPane);
            userButtonCreationDialog.getUserDefinedButtonOkAction().setExpressionScreen(expressionScreen);
            userButtonCreationDialog.getUserDefinedButtonOkAction().setComputeScreen(computeScreen);
            userButtonCreationDialog.getUserDefinedButtonOkAction().setMainSetupGrid(mainSetupGrid);
            userButtonCreationDialog.setUserCreatedButtonsMap(userCreatedButtonsMap);
            userButtonCreationDialog.show();
        }, userOperands, createMenu);
        addMenuItem("Edit", event -> {
            ListView<String> listView = new ListView<>();
            listView.setOrientation(Orientation.HORIZONTAL);
            listView.setPrefSize(60, 100);
            userCreatedButtonsMap.forEach((buttonName, button) -> listView.getItems().add(buttonName));
            Dialog<String> buttonsSelectionDialog = new Dialog<>();
            ButtonType deleteButton = new ButtonType("Delete");
            DialogPane dialogPane = buttonsSelectionDialog.getDialogPane();
            dialogPane.getButtonTypes().add(deleteButton);
            buttonsSelectionDialog.setTitle("Delete User Created Buttons");
            dialogPane.setContent(createSplitPane());
            buttonsSelectionDialog.show();
        }, userOperands);

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

    public SplitPane createSplitPane() {
        ObservableList<String> obString = FXCollections.observableArrayList("Mango", "Apple", "Orange", "Banana");
        ListView<String> nameListView = new ListView<>(obString);
        nameListView.setEditable(true);
        nameListView.setCellFactory(TextFieldListCell.forListView());
        nameListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<Double> obDouble = FXCollections.observableArrayList(0.1, 2.4, 1.6, 6.8);
        ListView<Double> valueListView = new ListView<>(obDouble);
        valueListView.setEditable(true);
        valueListView.setCellFactory(TextFieldListCell.forListView(new DoubleStringConverter()));
        valueListView.setOnEditCommit(event -> {
            valueListView.getItems().set(event.getIndex(), event.getNewValue());
        });
        Button newConstantButton = createNewConstant(nameListView, valueListView);
        Button editConstantButton = createEditConstant(nameListView, valueListView);
        Button deleteConstantButton = new Button("Delete");
        VBox vBox = new VBox(8, newConstantButton, editConstantButton, deleteConstantButton);
        vBox.setMinWidth(60);
        vBox.setMaxWidth(60);
        vBox.setAlignment(Pos.CENTER);
        SplitPane splitPane = new SplitPane(nameListView, valueListView, vBox);
        SplitPane.setResizableWithParent(vBox, false);
        return splitPane;
    }

    public Button createNewConstant(ListView<String> nameListView, ListView<Double> valueListView) {
        Button newConstantButton = new Button("New");
        newConstantButton.setOnAction(e -> {
            nameListView.getItems().add("");
            valueListView.getItems().add(0D);
        });
        return newConstantButton;
    }

    public Button createEditConstant(ListView<String> nameListView, ListView<Double> valueListView) {
        Button newConstantButton = new Button("Edit");
        newConstantButton.setOnAction(e -> {
            valueListView.edit(valueListView.getFocusModel().getFocusedIndex());
        });
        return newConstantButton;
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

    public void setUserCreatedButtonsMap(HashMap<String, TermButton<Operand>> userCreatedButtonsMap) {
        this.userCreatedButtonsMap = userCreatedButtonsMap;
    }
}
