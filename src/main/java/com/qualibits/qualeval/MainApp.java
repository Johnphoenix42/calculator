package com.qualibits.qualeval;

import com.qualibits.qualeval.buttons.BaseButton;
import com.qualibits.qualeval.buttons.ButtonName;
import com.qualibits.qualeval.buttons.TermButton;
import com.qualibits.qualeval.exec.ExecutionStackEntry;
import com.qualibits.qualeval.exec.ExpressionParser;
import com.qualibits.qualeval.mode.ModeModel;
import com.qualibits.qualeval.mode.ModeView;
import com.qualibits.qualeval.term.*;
import com.qualibits.qualeval.term.operator.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class MainApp extends Application {

    public static final String APP_NAME = "Qual-Eval";
    public static final Background ROOT_BACKGROUND = new Background(new BackgroundFill(
            new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.color(0.1,0.1, 0.1)),
                    new Stop(1, Color.color(0.18, 0.2, 0.18))),
                null, null)
            );
    private static final int MAX_EXECUTION_STACK_SIZE = 32; // You could allow the user to set this from a settings menu in the future

    private static final int USER_OPERAND_BUTTON_ROW_START = 2;
    private static final int USER_OPERAND_BUTTON_COLUMN_START = 7;
    private static final int USER_OPERAND_BUTTON_ROW_COUNT = 3;
    private static final int USER_OPERAND_BUTTON_COLUMN_COUNT = 3;

    private final VBox rootPane;
    private final StackPane appStackPane;
    private final TextField expressionScreen;
    private final TextField computeScreen;
    private final OverlayPane overlayPane;

    private final ExpressionParser expressionParser;
    private final LinkedList<Term> expressionQueue;
    private final LinkedList<ExecutionStackEntry> executionStack;

    private AppSetting appSetting;
    private ModeModel modeData;

    private final TrigOperator sinHOperator, cosHOperator, tanHOperator, atanOperator, asinOperator, acosOperator, sinOperator, cosOperator, tanOperator;
    private final HypotenuseOperator hypotenuseOperator;
    private final ExponentOperator xPowerY, tenPowerX;
    private final InverseOperator inverseOperator;
    private final FactorialOperator factorialOperator;
    private final SquareOperator squareOperator;
    private final RootOperator rootOperator;
    private final ModulusOperator modulusOperator;
    private final MultiplicationOperator multiplicationOperator;
    private final DivisionOperator divisionOperator;
    private final AdditionOperator additionOperator;
    private final SubtractionOperator subtractionOperator;
    private final LogarithmOperator logarithmicOperator;
    private final NaturalLogOperator naturalLogOperator;
    private final Parenthesis openParenthesis, closeParenthesis;
    private final Operand piOperand, eulerOperand;
    private final PartialOperand sevenOperand, eightOperand, nineOperand, fourOperand, fiveOperand, sixOperand, oneOperand, twoOperand, threeOperand, zeroOperand;
    private final DecimalPointOperator decimalPointOperator;

    private boolean shouldCloseParenthesis = false;
    private int userCreatedButtonIndex = 0;

    public MainApp() {
        rootPane = new VBox();
        appStackPane = new StackPane();
        computeScreen = new TextField();
        expressionScreen = new TextField();
        expressionScreen.setEditable(false);
        expressionScreen.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.NONE, new CornerRadii(5), BorderStroke.DEFAULT_WIDTHS)));
        overlayPane = new OverlayPane(appStackPane);
        expressionQueue = new LinkedList<>();
        executionStack = new LinkedList<>();

        appSetting = AppSetting.getSettings();
        expressionParser = new ExpressionParser(expressionQueue);
        ExpressionParser.setExecutionMemory(executionStack);
        modeData = new ModeModel();

        TermButton.setComputeScreen(computeScreen);
        sinHOperator = new TrigOperator(TrigOperator.TrigOperatorType.SINH);
        cosHOperator = new TrigOperator(TrigOperator.TrigOperatorType.COSH);
        tanHOperator = new TrigOperator(TrigOperator.TrigOperatorType.TANH);
        atanOperator = new TrigOperator(TrigOperator.TrigOperatorType.ARC_TAN);
        asinOperator = new TrigOperator(TrigOperator.TrigOperatorType.ARC_SIN);
        acosOperator = new TrigOperator(TrigOperator.TrigOperatorType.ARC_COS);
        sinOperator = new TrigOperator(TrigOperator.TrigOperatorType.SIN);
        cosOperator = new TrigOperator(TrigOperator.TrigOperatorType.COS);
        tanOperator = new TrigOperator(TrigOperator.TrigOperatorType.TAN);
        hypotenuseOperator = new HypotenuseOperator();
        xPowerY = new ExponentOperator();
        tenPowerX = new ExponentOperator(10D);
        inverseOperator = new InverseOperator();
        factorialOperator = new FactorialOperator();
        squareOperator = new SquareOperator();
        rootOperator = new RootOperator(2);
        modulusOperator = new ModulusOperator();
        multiplicationOperator = new MultiplicationOperator();
        divisionOperator = new DivisionOperator();
        additionOperator = new AdditionOperator();
        subtractionOperator = new SubtractionOperator();
        logarithmicOperator = new LogarithmOperator(10);
        naturalLogOperator = new NaturalLogOperator();
        openParenthesis = new Parenthesis(true);
        closeParenthesis = new Parenthesis(false);

        piOperand = new Operand(Math.PI, "⫪");
        eulerOperand = new Operand(Math.E, "e");
        sevenOperand = new PartialOperand("7");
        eightOperand = new PartialOperand("8");
        nineOperand = new PartialOperand("9");
        fourOperand = new PartialOperand("4");
        fiveOperand = new PartialOperand("5");
        sixOperand = new PartialOperand("6");
        oneOperand = new PartialOperand("1");
        twoOperand = new PartialOperand("2");
        threeOperand = new PartialOperand("3");
        zeroOperand = new PartialOperand("0");
        decimalPointOperator = new DecimalPointOperator();
    }

    LinkedList<BaseButton> getCalcButtons(int col, int row) {
        LinkedList<BaseButton> buttonList = new LinkedList<>();
        buttonList.add(new TermButton<>(ButtonName.OPEN_PARENTHESIS, event -> expressionScreen.setText(printExpressionQueue(openParenthesis)), openParenthesis, col, row - 2));
        buttonList.add(new TermButton<>(ButtonName.CLOSE_PARENTHESIS, event -> expressionScreen.setText(printExpressionQueue(closeParenthesis)), closeParenthesis, 1 + col, row - 2));
        buttonList.add(new TermButton<>(ButtonName.SINH, event -> handleTrigButtonsPressed(sinHOperator), sinHOperator, col + 2, row - 2));
        buttonList.add(new TermButton<>(ButtonName.COSH, event -> handleTrigButtonsPressed(cosHOperator), cosHOperator, col + 3, row-2));
        buttonList.add(new TermButton<>(ButtonName.TANH, event -> handleTrigButtonsPressed(tanHOperator), tanHOperator, col + 4, row-2));

        buttonList.add(new TermButton<>(ButtonName.HYPOTENUSE, event -> expressionScreen.setText(printExpressionQueue(hypotenuseOperator)), hypotenuseOperator, col, row - 1));
        buttonList.add(new TermButton<>(ButtonName.FACTORIAL, event -> expressionScreen.setText(printExpressionQueue(factorialOperator)), factorialOperator, col + 1, row - 1));
        buttonList.add(new TermButton<>(ButtonName.ARC_SIN, event -> handleTrigButtonsPressed(asinOperator), asinOperator, col + 2, row-1));
        buttonList.add(new TermButton<>(ButtonName.ARC_COS, event -> handleTrigButtonsPressed(acosOperator), acosOperator, col + 3, row-1));
        buttonList.add(new TermButton<>(ButtonName.ARC_TAN, event -> handleTrigButtonsPressed(atanOperator), atanOperator, 4 + col, row - 1));
        buttonList.add(new TermButton<>(ButtonName.SIN, event -> handleTrigButtonsPressed(sinOperator), sinOperator, col + 2, row));
        buttonList.add(new TermButton<>(ButtonName.COS, event -> handleTrigButtonsPressed(cosOperator), cosOperator, col + 3, row));
        buttonList.add(new TermButton<>(ButtonName.TAN, event -> handleTrigButtonsPressed(tanOperator), tanOperator, col + 4, row));

        buttonList.add(new TermButton<>(ButtonName.X_POWER_Y, event -> expressionScreen.setText(printExpressionQueue(xPowerY)), xPowerY, col, row));
        buttonList.add(new TermButton<>(ButtonName.INVERSE, event -> expressionScreen.setText(printExpressionQueue(inverseOperator)), inverseOperator, col + 1, row));

        buttonList.add(new TermButton<>(ButtonName.SQUARE, event -> expressionScreen.setText(printExpressionQueue(squareOperator)), squareOperator, col, row + 1));
        buttonList.add(new TermButton<>(ButtonName.PI, event -> {
            expressionScreen.setText(printExpressionQueue(piOperand));
            computeScreen.setText(piOperand.toString());
        }, piOperand, col + 1, row + 1));
        buttonList.add(new TermButton<>(ButtonName.EULER, event -> {
            expressionScreen.setText(printExpressionQueue(eulerOperand));
            computeScreen.setText(eulerOperand.toString());
        }, eulerOperand, 2 + col, row + 1));
        buttonList.add(new TermButton<>(ButtonName.MODULO, event -> expressionScreen.setText(printExpressionQueue(modulusOperator)), modulusOperator, 3 + col, row + 1));
        buttonList.add(new TermButton<>(ButtonName.DIVISION, event -> expressionScreen.setText(printExpressionQueue(divisionOperator)), divisionOperator, 4 + col, row + 1));

        buttonList.add(new TermButton<>(ButtonName.SQUARE_ROOT, event -> expressionScreen.setText(printExpressionQueue(rootOperator)), rootOperator, col, row + 2));
        buttonList.add(new TermButton<>(ButtonName.TEN_POWER_X, event -> expressionScreen.setText(printExpressionQueue(tenPowerX)), tenPowerX, col, row + 3));

        buttonList.add(new TermButton<>(ButtonName.LOG, event -> expressionScreen.setText(printExpressionQueue(logarithmicOperator)), logarithmicOperator, col, row + 4));
        buttonList.add(new TermButton<>(ButtonName.LN, event -> expressionScreen.setText(printExpressionQueue(naturalLogOperator)), naturalLogOperator, col, row + 5));
        buttonList.add(new TermButton<>(ButtonName.POINT, decimalPointOperator, 1 + col, row + 5));
        addOperandButtons(buttonList, col, row);

        buttonList.add(new BaseButton(ButtonName.ANS, this::handleRecallLastAnswer, 3 + col, row + 5));

        buttonList.add(new TermButton<>(ButtonName.MULTIPLICATION, event -> expressionScreen.setText(printExpressionQueue(multiplicationOperator)), multiplicationOperator, 4 + col, row + 2));
        buttonList.add(new TermButton<>(ButtonName.SUBTRACTION, event -> expressionScreen.setText(printExpressionQueue(subtractionOperator)), subtractionOperator, 4 + col, row + 3));
        buttonList.add(new TermButton<>(ButtonName.ADDITION, event -> expressionScreen.setText(printExpressionQueue(additionOperator)), additionOperator, 4 + col, row + 4));

        buttonList.add(new BaseButton(ButtonName.EQUALS, this::handleEqualsPressed, 4 + col, row + 5));
        return buttonList;
    }

    private void handleTrigButtonsPressed(TrigOperator trigOperator){
        trigOperator.setModeData(modeData);
        expressionScreen.setText(printExpressionQueue(trigOperator));
    }

    private void handleRecallLastAnswer(ActionEvent event) {
        ExecutionStackEntry lastEntry = executionStack.peek();
        if (lastEntry == null) return;
        Operand lastAnsTerm = new Operand(lastEntry.answer().getValue());
        computeScreen.setText(lastAnsTerm.toString());
        printExpressionQueue(lastAnsTerm);
    }

    private void addOperandButtons(LinkedList<BaseButton> buttonList, int col, int row) {
        buttonList.add(new TermButton<>(ButtonName.SEVEN, sevenOperand, 1 + col, row + 2));
        buttonList.add(new TermButton<>(ButtonName.EIGHT, eightOperand, 2 + col, row + 2));
        buttonList.add(new TermButton<>(ButtonName.NINE, nineOperand, 3 + col, row + 2));
        buttonList.add(new TermButton<>(ButtonName.FOUR, fourOperand, 1 + col, row + 3));
        buttonList.add(new TermButton<>(ButtonName.FIVE, fiveOperand, 2 + col, row + 3));
        buttonList.add(new TermButton<>(ButtonName.SIX, sixOperand, 3 + col, row + 3));
        buttonList.add(new TermButton<>(ButtonName.ONE, oneOperand, 1 + col, row + 4));
        buttonList.add(new TermButton<>(ButtonName.TWO, twoOperand, 2 + col, row + 4));
        buttonList.add(new TermButton<>(ButtonName.THREE, threeOperand, 3 + col, row + 4));
        buttonList.add(new TermButton<>(ButtonName.ZERO, zeroOperand, 2 + col, row + 5));
    }

    private void handleEqualsPressed(ActionEvent event){
        expressionScreen.setText(printExpressionQueue(null));
        Operand answer = new Operand(Double.NaN);
        try {
            answer = expressionParser.evaluateExpressionQueue();
            ModeModel.AnswerRadix radixMode = modeData.getAnswerRadix();
            String answerString = answer.toString();
            switch (radixMode) {
                case BINARY:
                    computeScreen.setText(Operand.convertToBinary(answer.getValue(), 6));
                    break;
                case OCTAL:
                    computeScreen.setText(Operand.convertToOctal(answer.getValue(), 6));
                    break;
                case HEXADECIMAL:
                    computeScreen.setText(Operand.convertToHexadecimal(answer.getValue(), 6));
                    break;
                default:
                    computeScreen.setText(answerString);
            }
        } catch (ArithmeticException ae) {
            computeScreen.setText("Error: " + ae.getMessage());
        } catch (NumberFormatException ne) {
            computeScreen.setText("Error: Incorrect Format");
        }
        if (executionStack.size() >= MAX_EXECUTION_STACK_SIZE) executionStack.removeFirst();
        executionStack.push(new ExecutionStackEntry(new LinkedList<>(expressionQueue), answer));
        expressionQueue.clear();
    }

    /**
     * Prints an expression unto a String object from the operation queue.
     * @return a String containing the expression
     */
    private String printExpressionQueue(Term token) {
        StringBuilder expressionString = new StringBuilder();
        if (!PartialOperand.getStringValue().isEmpty()) {
            Operand operand = new Operand(Double.parseDouble(PartialOperand.getStringValue()), PartialOperand.getStringValue());
            normalizeExpression(operand);
            expressionQueue.addLast(operand);
            if (shouldCloseParenthesis) expressionQueue.addLast(new Parenthesis(false));
            shouldCloseParenthesis = false;
        }

        if (token != null) {
            normalizeExpression(token);
            expressionQueue.addLast(token);
            if (shouldCloseParenthesis) expressionQueue.addLast(new Parenthesis(false));
            shouldCloseParenthesis = false;
        }

        for (Term term : expressionQueue) {
            String shownString = term.toString();
            // if term is an instance of Operand and operand alone, not PartialOperand
            if (term instanceof Operand && !(term instanceof PartialOperand)) {
                boolean shouldUseVariableName = appSetting.getScreenSettings().shouldUseVariableName();
                shownString = shouldUseVariableName ? ((Operand) term).getDenotation() : shownString;
            }
            expressionString.append(shownString);
        }
        PartialOperand.setStringValue("");
        return expressionString.toString();
    }

    /**
     * In the context of this app, normalizations refer to the adjustments made to the input to convert
     * inconsistent or undecipherable expressions in the computing system input and making it
     * decipherable so that the app can execute it safely without triggering errors based on certain
     * assumptions
     * The user should be able to opt out of the normalization
     * @param token the term just entered. The adjustment will be made before adding it to the
     *              expressionQueue
     */
    private void normalizeExpression(Term token){
        try {
            Term lastToken = expressionQueue.getLast();
            if (lastToken instanceof Operator && !(lastToken instanceof Functions)) return;
            // if the last token is not an open parenthesis, and token is of either type Operand,
            // open parenthesis or function operator
            if (!(lastToken instanceof Parenthesis p && p.isOpen()) && !(lastToken instanceof Functions) &&
                    ((token instanceof Operand || token instanceof Parenthesis p && p.isOpen()) ||
                    token instanceof Functions)) {
                expressionQueue.addLast(new MultiplicationOperator());
            }
            if (token instanceof Operand && lastToken instanceof Functions) {
                expressionQueue.addLast(new Parenthesis(true));
                shouldCloseParenthesis = true;
            }
        }catch (NoSuchElementException ne) {
            System.err.println("normalizeExpression >> "+ne.getMessage());
        }
    }

    private LinkedList<BaseButton> createControlButton() {
        LinkedList<BaseButton> controlButtons = new LinkedList<>();
        ModeView modeView = new ModeView(modeData);
        ModeView.setModeData(modeData);

        BaseButton modeButton = new BaseButton(ButtonName.MODE, e -> {
            //overlayPane.setView(modeView);
            overlayPane.show(modeView);
            overlayPane.addCloseButton();
        }, 0, 3, 2, 1);

        BaseButton clearButton = new BaseButton(ButtonName.CLEAR, event -> {
            PartialOperand.setStringValue("");
            expressionScreen.setText("");
            computeScreen.setText("0");
            expressionQueue.clear();
        }, 3, 3);

        BaseButton backSpaceButton = new BaseButton(ButtonName.CURTAIL, event -> {
            if (!PartialOperand.getStringValue().isEmpty()) {
                String text = PartialOperand.getStringValue();
                PartialOperand.setStringValue(text.substring(0, text.length() - 1));
                computeScreen.setText(PartialOperand.getStringValue().isEmpty() ? "0" : PartialOperand.getStringValue());
            } else {
                if(!expressionQueue.isEmpty()) {
                    expressionQueue.removeLast();
                    expressionScreen.setText(printExpressionQueue(null));
                    computeScreen.setText("0");
                }
            }
        }, 4, 3);

        controlButtons.add(modeButton);
        controlButtons.add(clearButton);
        controlButtons.add(backSpaceButton);

        return controlButtons;
    }

    public GridPane setupGrid() {
        GridPane gridPane = new GridPane();
        setConstraints(gridPane);

        expressionScreen.setMaxWidth(Double.MAX_VALUE);
        expressionScreen.setAlignment(Pos.CENTER_RIGHT);
        expressionScreen.setBackground(new Background(new BackgroundFill(Color.gray(0.7), null, null)));
        gridPane.add(expressionScreen, 0, 0, 5, 1);

        gridPane.add(addDisplayScreen(), 0, 1, 5, 1);
        for (BaseButton calculatorButton : getCalcButtons(0, 5)) {
            gridPane.add(calculatorButton, calculatorButton.getColumn(), calculatorButton.getRow(),
                    calculatorButton.getColSpan(), calculatorButton.getRowSpan());
        }

        LinkedList<BaseButton> controlButtons = createControlButton();
        for (int i = 0; i < controlButtons.size(); ++i){
            gridPane.add(controlButtons.get(i), controlButtons.get(i).getColumn(), (i+10)/5);
        }

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(2);
        gridPane.setHgap(2);
        return gridPane;
    }

    private void setConstraints(GridPane gridPane){
        for (int i = 0; i < 5; ++i) {
            ColumnConstraints columnConstraints = new ColumnConstraints(45, 45, 60, Priority.ALWAYS, HPos.LEFT, false);
            columnConstraints.setFillWidth(true);
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        for(int i = 0; i < 11; ++i) {
            RowConstraints rowConstraints = new RowConstraints(30, 40, 70, Priority.ALWAYS, VPos.CENTER, true);
            gridPane.getRowConstraints().add(rowConstraints);
        }
        gridPane.getRowConstraints().get(1).setMinHeight(60);
        gridPane.getRowConstraints().get(1).setPrefHeight(70);
    }

    private VBox addDisplayScreen(){
        GridPane modeScreenDisplayGrid = new GridPane();
        modeScreenDisplayGrid.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        Label angleLabel = new Label(modeData.getAngleMode().name().substring(0, 3));
        angleLabel.setFont(Font.font(10));
        modeScreenDisplayGrid.add(angleLabel, 0, 0);

        computeScreen.setEditable(false);
        computeScreen.setEffect(new Glow(0));
        computeScreen.setFont(new Font("Arial", 18));
        computeScreen.setPrefHeight(45);
        computeScreen.setMaxHeight(60);
        computeScreen.setAlignment(Pos.CENTER_RIGHT);
        expressionScreen.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.NONE, new CornerRadii(5), BorderStroke.DEFAULT_WIDTHS)));

        return new VBox(modeScreenDisplayGrid, computeScreen);
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

    public void start(Stage primaryStage) {
        //String css = Objects.requireNonNull(getClass().getResource("/main.css")).toExternalForm();
        GridPane mainSetupGrid = setupGrid();
        Menu createMenu = createMenu("Create");
        Menu createMenuItem = createMenu("User Operands", Color.BLACK);
        addMenuItem("As Buttons", event -> {
            CreateConstantGridPane constantGridPane = new CreateConstantGridPane();
            ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
            Dialog<UserConstant> dialog = new Dialog<>();
            dialog.setTitle("Create User Operand");
            dialog.getDialogPane().setContent(constantGridPane);
            dialog.getDialogPane().getButtonTypes().add(createButtonType);
            ((Button) dialog.getDialogPane().lookupButton(createButtonType)).setBackground(ROOT_BACKGROUND);
            dialog.setResultConverter((resultButtonType) -> new UserConstant(constantGridPane.nameField.getText(),
                    constantGridPane.valueField.getValue()));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.showAndWait().filter(constant-> !constant.name().isEmpty() && !constant.value().isNaN())
                    .ifPresent(constant -> {
                        var userCreatedOperand = new Operand( constant.value(), constant.name());
                        TermButton<Operand> userCreatedButton = new TermButton<>(constant.name(), buttonEvent -> {
                            boolean shouldUseVariableName = appSetting.getScreenSettings().shouldUseVariableName();
                            expressionScreen.setText(printExpressionQueue(userCreatedOperand));
                            computeScreen.setText(shouldUseVariableName ? userCreatedOperand.getDenotation() : userCreatedOperand.toString());
                        }, userCreatedOperand,
                                USER_OPERAND_BUTTON_COLUMN_START + (userCreatedButtonIndex % USER_OPERAND_BUTTON_COLUMN_COUNT),
                                USER_OPERAND_BUTTON_ROW_START + (userCreatedButtonIndex / USER_OPERAND_BUTTON_ROW_COUNT));
                        mainSetupGrid.add(userCreatedButton, userCreatedButton.getColumn(), userCreatedButton.getRow());
                        userCreatedButtonIndex++;
                    });
        }, createMenuItem, createMenu);

        Menu displayMenu = createMenu("Display");
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

        Menu helpMenu = createMenu("Help");
        MenuItem aboutMenuItem = new MenuItem("About");
        aboutMenuItem.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "Qual-Eval v1.1.3\nOpen source calculator\nContributors\nJohn Ibiwoye",  ButtonType.CLOSE);
            alert.showAndWait();
        });
        helpMenu.getItems().add(aboutMenuItem);
        Menu[] appMenu = {createMenu, displayMenu, createMenu("Settings"), helpMenu};
        MenuBar menuBar = new MenuBar(appMenu);
        menuBar.setBackground(new Background(new BackgroundFill(
                new LinearGradient(0, 1, 1, 1, true, CycleMethod.REFLECT,
                        new Stop(0, Color.NAVY), new Stop(0.5, Color.web("#333"))), null, null)));

        rootPane.getChildren().addAll(menuBar, appStackPane);
        rootPane.setBackground(ROOT_BACKGROUND);
        appStackPane.setAlignment(Pos.CENTER);
        appStackPane.getChildren().add(mainSetupGrid);
        Scene mainScene = new Scene(rootPane, 350, 500, Color.GRAY);
        //mainScene.getStylesheets().add(css);

        primaryStage.setScene(mainScene);
        primaryStage.setTitle(APP_NAME);
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.toFront();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    static class CreateConstantGridPane extends GridPane {

        private final TextField nameField;
        private final Spinner<Double> valueField;

        CreateConstantGridPane(){
            super();
            ColumnConstraints columnConstraints = new ColumnConstraints(40, 50, 60, Priority.ALWAYS, HPos.CENTER, true);
            getColumnConstraints().add(columnConstraints);
            for (int i = 0; i < 2; ++i) {
                getRowConstraints().add(new RowConstraints(40, 50, 60, Priority.ALWAYS, VPos.CENTER, true));
            }
            nameField = new TextField();
            valueField = new Spinner<>(Double.MIN_VALUE, Double.MAX_VALUE, 0, 0.1);
            add(new Label("Name"), 0, 0);
            add(nameField, 1, 0);
            add(new Label("Value"), 0, 1);
            add(valueField, 1, 1);

            valueField.setEditable(true);
        }
    }

    private record UserConstant(String name, Double value){}

}
