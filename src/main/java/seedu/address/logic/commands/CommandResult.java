package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    private final boolean isPersonNew;

    private final boolean isPersonUpdated;

    private final Person oldPerson;

    private final Person newPerson;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.isPersonNew = false;
        this.isPersonUpdated = false;
        this.showHelp = showHelp;
        this.exit = exit;
        this.oldPerson = null;
        this.newPerson = null;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields - for view command.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, Person newPerson) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.isPersonNew = true;
        this.isPersonUpdated = false;
        this.showHelp = showHelp;
        this.exit = exit;
        this.oldPerson = null;
        this.newPerson = newPerson;
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields - for commands which update person.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, Person oldPerson, Person newPerson) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.isPersonNew = false;
        this.isPersonUpdated = true;
        this.showHelp = showHelp;
        this.exit = exit;
        this.oldPerson = oldPerson;
        this.newPerson = newPerson;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public Person getSelectedPerson() {
        return this.oldPerson;
    }

    public Person getNewPerson() {
        return this.newPerson;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isPersonNew() {
        return isPersonNew;
    }

    public boolean isPersonUpdated() {
        return isPersonUpdated;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && isPersonNew == otherCommandResult.isPersonNew
                && isPersonUpdated == otherCommandResult.isPersonUpdated
                && Objects.equals(newPerson, otherCommandResult.newPerson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, isPersonNew, isPersonUpdated, newPerson);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("isPersonNew", isPersonNew)
                .add("isPersonUpdated", isPersonUpdated)
                .add("newPerson", newPerson)
                .add("exit", exit)
                .toString();
    }

}
