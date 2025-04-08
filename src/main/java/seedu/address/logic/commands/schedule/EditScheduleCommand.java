package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.ScheduleUtil.checkStartEndDateTime;
import static seedu.address.logic.Messages.MESSAGE_SCHEDULE_START_TIME_BEFORE_END_TIME;
import static seedu.address.logic.Messages.MESSAGE_SCHEDULE_TIMING_CLASH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SCHEDULES;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.Mode;
import seedu.address.model.person.Name;
import seedu.address.model.schedule.Schedule;


/**
 * Edits the details of an existing schedule in the schedule board.
 */
public class EditScheduleCommand extends Command {

    public static final String COMMAND_WORD = "sedit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the interview "
            + "schedule identified "
            + "by the index number used in the displayed schedule list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: SCHEDULE_INDEX (must be a positive integer) "
            + "[" + PREFIX_SCHEDULE + "SCHEDULE_DATE_AND_DURATION" + "] "
            + "[" + PREFIX_MODE + "MODE" + "]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SCHEDULE + "2020-03-03 10:00 15:00 "
            + PREFIX_MODE + "offline";

    public static final String MESSAGE_EDIT_SCHEDULE_SUCCESS = "Edited schedule: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_SCHEDULE = "There is already a interview schedule"
            + "schedule at that timing.";

    private final Index index;
    private final EditScheduleDescriptor editScheduleDescriptor;

    /**
     * @param index of the schedule in the filtered schedule list to edit
     * @param editScheduleDescriptor details to edit the schedule with
     */
    public EditScheduleCommand(Index index, EditScheduleDescriptor editScheduleDescriptor) {
        requireNonNull(index);
        requireNonNull(editScheduleDescriptor);

        this.index = index;
        this.editScheduleDescriptor = new EditScheduleDescriptor(editScheduleDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Schedule> lastShownList = model.getFilteredScheduleList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX);
        }

        Schedule scheduleToEdit = lastShownList.get(index.getZeroBased());
        Schedule editedSchedule = createEditedSchedule(scheduleToEdit, editScheduleDescriptor);

        if (model.hasSameDateTimeEdit(editedSchedule, scheduleToEdit)) {
            throw new CommandException(MESSAGE_SCHEDULE_TIMING_CLASH);
        }

        model.setSchedule(scheduleToEdit, editedSchedule);
        model.updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULES);
        return new CommandResult(String.format(MESSAGE_EDIT_SCHEDULE_SUCCESS, Messages.format(editedSchedule)));
    }

    /**
     * Creates and returns a {@code schedule} with the details of {@code scheduleToEdit}
     * edited with {@code editScheduleDescriptor}.
     */
    private static Schedule createEditedSchedule(Schedule scheduleToEdit, EditScheduleDescriptor editScheduleDescriptor)
            throws CommandException {

        assert scheduleToEdit != null;

        LocalDate updatedDate = editScheduleDescriptor.getDate()
                .orElse(scheduleToEdit.getDate());

        LocalTime updatedStartTime = editScheduleDescriptor.getStartTime()
                .orElse(scheduleToEdit.getStartTime());

        LocalTime updatedEndTime = editScheduleDescriptor.getEndTime()
                .orElse(scheduleToEdit.getEndTime());

        if (!checkStartEndDateTime(updatedStartTime, updatedEndTime)) {
            throw new CommandException(MESSAGE_SCHEDULE_START_TIME_BEFORE_END_TIME);
        }

        Mode updatedMode = editScheduleDescriptor.getMode().orElse(scheduleToEdit.getMode());
        Name candidateName = scheduleToEdit.getCandidateName();
        Email candidateEmail = scheduleToEdit.getCandidateEmail();

        return new Schedule(updatedDate, updatedStartTime, updatedEndTime,
                updatedMode, candidateName, candidateEmail);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditScheduleCommand)) {
            return false;
        }

        EditScheduleCommand editScheduleCommand = (EditScheduleCommand) other;
        return index.equals(editScheduleCommand.index)
                && editScheduleDescriptor.equals(editScheduleCommand.editScheduleDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editScheduleDescriptor", editScheduleDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the schedule with. Each non-empty field value will replace the
     * corresponding field value of the schedule.
     */
    public static class EditScheduleDescriptor {
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;
        private Mode mode;


        public EditScheduleDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditScheduleDescriptor(EditScheduleDescriptor toCopy) {
            setDate(toCopy.date);
            setStartTime(toCopy.startTime);
            setEndTime(toCopy.endTime);
            setMode(toCopy.mode);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(date, startTime, endTime, mode);
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public Optional<LocalDate> getDate() {
            return Optional.ofNullable(date);
        }

        public void setStartTime(LocalTime startTime) {
            this.startTime = startTime;
        }

        public Optional<LocalTime> getStartTime() {
            return Optional.ofNullable(startTime);
        }

        public void setEndTime(LocalTime endTime) {
            this.endTime = endTime;
        }

        public Optional<LocalTime> getEndTime() {
            return Optional.ofNullable(endTime);
        }

        public void setMode(Mode mode) {
            this.mode = mode;
        }

        public Optional<Mode> getMode() {
            return Optional.ofNullable(mode);
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditScheduleDescriptor)) {
                return false;
            }

            // state check
            EditScheduleDescriptor e = (EditScheduleDescriptor) other;

            return getDate().equals(e.getDate())
                    && getStartTime().equals(e.getStartTime())
                    && getEndTime().equals(e.getEndTime())
                    && getMode().equals(e.getMode());
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("date", this.date)
                    .add("startTime", this.startTime)
                    .add("endTime", this.endTime)
                    .add("mode", this.mode)
                    .toString();
        }
    }
}
