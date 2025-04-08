package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Schedule;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_FILE_EXISTS = "File at '%1$s' already exists!";
    public static final String MESSAGE_SCHEDULE_START_TIME_BEFORE_END_TIME =
            "The end time of the interview schedule should be strictly later than "
                    + "start time of the interview schedule. \n"
                    + "End time of interview schedule must be at least 15 minutes after start time and "
                    + "no more than 4 hours later";;
    public static final String MESSAGE_SCHEDULE_INVALID_DURATION =
            "End time of interview schedule must be at least 15 minutes after start time and "
                    + "no more than 4 hours later";
    public static final String MESSAGE_SCHEDULE_TIMING_CLASH =
            "This interview schedule's timing conflicts with an existing interview schedule. \n"
                    + "The schedule board supports only non-overlapping intervals. \n"
                    + "For schedules on the same day, the start time of one schedule must be at or "
                    + "after the end time of the other.";

    public static final String MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX =
            "The schedule index provided is invalid.";

    public static final String MESSAGE_MISSING_FIELDS =
            "Missing the following values: ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    public static String getErrorMessageForMissingPrefixes(Prefix... missingPrefixes) {
        assert missingPrefixes.length > 0;
        Set<String> missingFields =
                Stream.of(missingPrefixes).map(Prefix::getPrefixField).collect(Collectors.toSet());
        return MESSAGE_MISSING_FIELDS + String.join(", ", missingFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Applicable Job Roles: ");
        person.getJobRoles().forEach(builder::append);
        builder.append("; Label: ")
                .append(person.getLabel())
                .append("; Remark: ")
                .append("'").append(person.getRemark()).append("'")
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }


    /**
     * Formats the {@code schedule} for display to the user.
     */
    public static String format(Schedule schedule) {
        final StringBuilder builder = new StringBuilder();
        builder.append(schedule.getDate())
                .append("; Start time: ")
                .append(schedule.getStartTime())
                .append("; End time: ")
                .append(schedule.getEndTime())
                .append("; Mode: ")
                .append(schedule.getMode())
                .append("; Candidate name: ")
                .append(schedule.getCandidateName())
                .append("; Candidate email: ")
                .append(schedule.getCandidateEmail());

        return builder.toString();
    }

}
