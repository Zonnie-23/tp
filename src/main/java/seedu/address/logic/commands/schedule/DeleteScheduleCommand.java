package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.schedule.Schedule;

/**
 * Deletes a schedule identified using it's displayed index from the Schedule Board.
 */
public class DeleteScheduleCommand extends Command {

    public static final String COMMAND_WORD = "sdelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the interview schedule identified by the index number used in the displayed schedule board.\n"
            + "Parameters: " + "SCHEDULE_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_DELETE_SCHEDULE_SUCCESS = "Deleted schedule: %1$s";

    private final Index targetIndex;

    public DeleteScheduleCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Schedule> lastShownList = model.getFilteredScheduleList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX);
        }

        Schedule scheduleToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteSchedule(scheduleToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_SCHEDULE_SUCCESS, Messages.format(scheduleToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteScheduleCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteScheduleCommand) other).targetIndex)); // state check
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
