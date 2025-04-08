package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Theme;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ThemeCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.JobRole;
import seedu.address.model.person.Person;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private CandidateFullDetailsCard candidateFullDetailsCard;
    private ScheduleListPanel scheduleListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane candidateFullDetailsContainer;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane scheduleListPanelPlaceholder;

    @FXML
    private StackPane applicantDetailsPanelPlaceholder;

    @FXML
    private HBox allResultsContainer;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();

        handleStartTheme(logic.getTheme());
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        candidateFullDetailsCard = new CandidateFullDetailsCard(logic.getFirstPerson());
        candidateFullDetailsContainer.getChildren().add(candidateFullDetailsCard.getRoot());
        candidateFullDetailsContainer.maxWidthProperty().bind(allResultsContainer.widthProperty().multiply(0.5));

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath(),
                logic.getScheduleBoardFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        scheduleListPanel = new ScheduleListPanel(logic.getFilteredScheduleList());
        logger.info("Debug: " + logic.getFilteredScheduleList().toString());
        scheduleListPanelPlaceholder.getChildren().add(scheduleListPanel.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY(), logic.getTheme());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        StatisticsWindow statisticsWindow = StatisticsWindow.getInstance();
        if (statisticsWindow.isShowing()) {
            statisticsWindow.hide();
        }
        primaryStage.hide();
    }

    /**
     * Handles displaying a new person's job details.
     */
    @FXML
    private void handleNewPerson(Person person) {
        if (allResultsContainer.getChildren().size() > 1) {
            allResultsContainer.getChildren().remove(1);
        }

        candidateFullDetailsCard.changePerson(person);

        allResultsContainer.getChildren().add(candidateFullDetailsContainer);
        candidateFullDetailsCard.show();
    }

    @FXML
    private void handleHidePerson() {
        while (allResultsContainer.getChildren().size() > 1) {
            allResultsContainer.getChildren().remove(1);
        }
        candidateFullDetailsCard.hide();
    }

    //Solution below inspired by https://stackoverflow.com/questions/53524131
    @FXML
    private void handleDarkTheme() throws CommandException {
        this.helpWindow.setDarkTheme();
        logic.setTheme(Theme.DARK);
        resultDisplay.setFeedbackToUser(String.format(ThemeCommand.MESSAGE_THEME_CHANGE_SUCCESS, Theme.DARK));
        primaryStage.getScene().getStylesheets().clear();
        primaryStage.getScene().getStylesheets().add("view/DarkTheme.css");
        primaryStage.getScene().getStylesheets().add("view/DarkExtensions.css");
    }

    //Solution below inspired by https://stackoverflow.com/questions/53524131
    @FXML
    private void handleLightTheme() throws CommandException {
        this.helpWindow.setLightTheme();
        logic.setTheme(Theme.LIGHT);
        resultDisplay.setFeedbackToUser(String.format(ThemeCommand.MESSAGE_THEME_CHANGE_SUCCESS, Theme.LIGHT));
        primaryStage.getScene().getStylesheets().clear();
        primaryStage.getScene().getStylesheets().add("view/LightTheme.css");
        primaryStage.getScene().getStylesheets().add("view/LightExtensions.css");
    }

    private void handleStartTheme(Theme theme) {
        if (theme.isDarkTheme()) {
            primaryStage.getScene().getStylesheets().clear();
            primaryStage.getScene().getStylesheets().add("view/DarkTheme.css");
            primaryStage.getScene().getStylesheets().add("view/DarkExtensions.css");
        } else {
            primaryStage.getScene().getStylesheets().clear();
            primaryStage.getScene().getStylesheets().add("view/LightTheme.css");
            primaryStage.getScene().getStylesheets().add("view/LightExtensions.css");
        }
    }
    @FXML
    private void handleViewCommand() {
        primaryStage.getScene().getStylesheets().clear();
        primaryStage.getScene().getStylesheets().add("view/LightTheme.css");
        primaryStage.getScene().getStylesheets().add("view/LightExtensions.css");
    }

    private void handleTheme(Theme theme) throws CommandException {
        if (theme.isDarkTheme()) {
            handleDarkTheme();
        } else {
            handleLightTheme();
        }
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.shouldShowNewCandidateFullDetails()) {
                handleNewPerson(commandResult.getCandidateToShow());
            } else {
                handleHidePerson();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.shouldChangeTheme()) {
                handleTheme(commandResult.getTheme());
            }

            if (commandResult.getStatistics() != null) {
                StatisticsWindow statisticsWindow = StatisticsWindow.getInstance();
                List<JobRole> dynamicJobRoles = logic.getFilteredJobRolesList()
                        .stream()
                        .collect(Collectors.toList());
                statisticsWindow.setStatistics(commandResult.getStatistics(), dynamicJobRoles);
                statisticsWindow.show();
            }

            return commandResult;

        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
