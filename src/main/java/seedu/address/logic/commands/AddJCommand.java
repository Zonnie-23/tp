package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.JobRole;

/**
 * Adds a new job role to saved list of job roles.
 */
public class AddJCommand extends Command {
    public static final String COMMAND_WORD = "addJ";
    public static final String MESSAGE_SUCCESS = "New job role added: %s";
    public static final String MESSAGE_DUPLICATE_JOB_ROLE = "This job role already exists.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds input job role into saved list.\n"
            + "Parameters: jobRole \n"
            + "Example: " + COMMAND_WORD + " Software Test Engineer";

    private final JobRole title;

    /**
     * Creates an AddJCommand to add the specified {@code JobRole}
     */
    public AddJCommand(JobRole title) {
        requireNonNull(title);
        this.title = title;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasJobRole(title)) {
            throw new CommandException(MESSAGE_DUPLICATE_JOB_ROLE);
        }
        model.addJobRole(title);
        return new CommandResult(String.format(MESSAGE_SUCCESS, title.value));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddJCommand)) {
            return false;
        }

        AddJCommand otherAddCommand = (AddJCommand) other;
        return title.equals(otherAddCommand.title);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", title)
                .toString();
    }

}
