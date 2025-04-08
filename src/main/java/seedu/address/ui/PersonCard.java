package seedu.address.ui;

import java.util.Comparator;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String MESSAGE_JOBROLE = "Job Role: ";

    private static final String MESSAGE_STATUS = "Status: %s";

    private static final String MESSAGE_REMARK = "Remark: %s";

    private static final String STYLE_LABEL = "cell_small_label";

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
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
    private FlowPane jobRoles;
    @FXML
    private FlowPane schedule;
    @FXML
    private FlowPane tags;
    @FXML
    private Label remark;
    @FXML
    private Label label;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        email.setText(person.getEmail().value);
        label.setText(String.format(MESSAGE_STATUS, person.getLabel().value));

        String jobRolesMerged = person.getJobRoles().stream()
                .sorted(Comparator.comparing(jobRole -> jobRole.value))
                .map(jobRole -> jobRole.value)
                .collect(Collectors.joining(", "));
        jobRoles.getChildren().add(new Label(MESSAGE_JOBROLE + jobRolesMerged));

        String remarkValue = person.getRemark().value;
        if (!remarkValue.isEmpty()) {
            this.showRemark(remarkValue);
        } else {
            this.hideRemark();
        }

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private void showRemark(String text) {
        this.remark.setText(String.format(MESSAGE_REMARK, text));
        this.remark.setVisible(true);
    }

    private void hideRemark() {
        this.remark.setText(String.format(MESSAGE_REMARK, ""));
        this.remark.setVisible(false);
    }
}
