package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_SCHEDULE_TIMING_CLASH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CANDIDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Schedule;

/**
 * Adds a schedule into board.
 */
public class AddScheduleCommand extends Command {

    public static final String COMMAND_WORD = "sadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an interview schedule to board. \n"
            + "Parameters: "
            + PREFIX_CANDIDATE + "INDEX (must be a positive integer) "
            + PREFIX_SCHEDULE + "SCHEDULE_DATE_AND_DURATION "
            + PREFIX_MODE + "MODE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CANDIDATE + "1 "
            + PREFIX_SCHEDULE + "2020-03-03 10:00 14:00 "
            + PREFIX_MODE + "online";

    public static final String MESSAGE_SUCCESS = "New interview schedule added: %1$s";
    public static final String MESSAGE_DUPLICATE_SCHEDULE = "There is already an interview schedule at that timing!";

    private final Logger logger = LogsCenter.getLogger(getClass());
    private final Index index;
    private final Schedule toAdd;

    /**
     * Creates an AddScheduleCommand to add the specified {@code Schedule}
     */
    public AddScheduleCommand(Index index, Schedule schedule) {
        requireAllNonNull(index, schedule);
        this.index = index;
        toAdd = schedule;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();


        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (model.hasSameDateTime(toAdd)) {
            throw new CommandException(MESSAGE_SCHEDULE_TIMING_CLASH);
        }

        Person candidate = lastShownList.get(index.getZeroBased());
        Email candidateEmail = new Email(candidate.getEmail().toString());
        Name candidateName = new Name(candidate.getName().toString());
        toAdd.setCandidateEmail(candidateEmail);
        toAdd.setCandidateName(candidateName);


        model.addSchedule(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddScheduleCommand // instanceof handles nulls
                && toAdd.equals(((AddScheduleCommand) other).toAdd));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
