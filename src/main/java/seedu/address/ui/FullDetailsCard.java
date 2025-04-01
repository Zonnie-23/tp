package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class FullDetailsCard extends UiPart<Region> {

    private static final String MESSAGE_SCHEDULE = "Interview Date and Time: %s";
    private static final String MESSAGE_REMARK = "Remark: %s";

    private static final String STYLE_LABEL = "cell_small_label";

    private static final String FXML = "FullDetailsCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private Person person;

    @FXML
    private HBox fullCardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label jobTitle;
    @FXML
    private FlowPane remark;
    @FXML
    private FlowPane tags;
    @FXML
    private Label label;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     * Could
     */
    public FullDetailsCard(Person person) {
        super(FXML);
        this.person = person;
        savePersonDetails(person);
    }

    /**
     * Handles the new person to view, or clear the current entry when the person is deleted
     * @param person The person to be viewed
     */
    public void changePerson(Person person) {
        clear();
        this.person = person;
        savePersonDetails(person);
    }

    /**
     * Helper function to save the given person into
     * @param person
     */
    private void savePersonDetails(Person person) {
        if (person == null) {
            clear();
            return;
        }

        this.person = person;

        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        jobTitle.setText(person.getJobTitle().value);
        label.setText(person.getLabel().value);

        String remarkValue = person.getRemark().value;
        if (!remarkValue.isEmpty()) {
            Label remarkLabel = createLabel(String.format(MESSAGE_REMARK, remarkValue));
            remark.getChildren().add(remarkLabel);
        }

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private Label createLabel(String text) {
        Label uiLabel = new Label(text);
        uiLabel.getStyleClass().addAll(FullDetailsCard.STYLE_LABEL);
        return uiLabel;
    }

    /**
     * To show the Application Card
     */
    public void show() {
        getRoot().setVisible(true);
        getRoot().setManaged(true);
    }

    /**
     * To show the Application Card
     */
    public void hide() {
        getRoot().setVisible(false);
        getRoot().setManaged(false);
    }

    /**
     * Clear command to remove all text fields
     */
    public void clear() {
        name.setText("");
        phone.setText("");
        address.setText("");
        email.setText("");
        label.setText("");
        jobTitle.setText("");
        remark.getChildren().clear();
        tags.getChildren().clear();
    }
}
