package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.FileUtil.FILE_EXTENSION_JSON;
import static seedu.address.commons.util.ScheduleUtil.checkStartEndDateTime;
import static seedu.address.commons.util.ScheduleUtil.isValidDuration;
import static seedu.address.logic.Messages.MESSAGE_SCHEDULE_INVALID_DURATION;
import static seedu.address.logic.Messages.MESSAGE_SCHEDULE_START_TIME_BEFORE_END_TIME;
import static seedu.address.model.person.Remark.MAX_REMARK_LENGTH;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.Theme;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.JobRole;
import seedu.address.model.person.Label;
import seedu.address.model.person.Mode;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_FILE_PATH = "File path provided is invalid.";
    public static final String MESSAGE_INVALID_DATE =
            "Invalid date or incorrect format of date. Format of date supported is yyyy-MM-dd (e.g. 2025-02-03)";
    public static final String MESSAGE_INVALID_TIME =
            "Invalid time or incorrect format of time. Format of interview duration's start and end time should be "
                    + "HH:mm HH:mm (e.g. 12:00 13:00)\n"
                    + "End time of interview schedule must be at least 15 minutes after start time and "
                    + "no more than 4 hours later";
    public static final String MESSAGE_REMARK_TOO_LONG = "You have exceeded the maximum character limit for remarks"
            + " (limit: %d characters, provided: %d characters).";
    public static final String MESSAGE_INPUT_TOO_LONG = "Input for %s is too long! Expected length: %d characters.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        if (trimmedPhone.length() > Phone.MAX_LENGTH) {
            throw new ParseException(String.format(MESSAGE_INPUT_TOO_LONG, "Phone", Phone.MAX_LENGTH));
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String jobRole} into an {@code JobRole}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static JobRole parseJobRole(String jobRole) throws ParseException {
        requireNonNull(jobRole);
        String trimmedJobRole = jobRole.trim();
        if (!JobRole.isValidJobRole(trimmedJobRole)) {
            throw new ParseException(JobRole.MESSAGE_NEW_CONSTRAINTS);
        }
        if (trimmedJobRole.length() > JobRole.MAX_LENGTH) {
            throw new ParseException(String.format(MESSAGE_INPUT_TOO_LONG, "Job role", JobRole.MAX_LENGTH));
        }
        return new JobRole(trimmedJobRole);
    }

    /**
     * Parses a {@code Collection<String> jobRole} into an {@code JobRole}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Set<JobRole> parseJobRoles(Collection<String> jobRoles) throws ParseException {
        requireNonNull(jobRoles);
        final Set<JobRole> jobRoleSet = new HashSet<>();
        for (String jobRoleValue : jobRoles) {
            jobRoleSet.add(parseJobRole(jobRoleValue));
        }
        return jobRoleSet;
    }

    /**
     * Parses a {@code String remark} into a {@code Remark}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Remark parseRemark(String remark) throws ParseException {
        String trimmedRemark = remark.trim();
        if (trimmedRemark.length() > MAX_REMARK_LENGTH) {
            throw new ParseException(getLongRemarkErrorMessage(trimmedRemark.length()));
        }
        return new Remark(trimmedRemark);
    }

    /**
     * Parses a {@code String schedule} into a {@code Schedule}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static LocalDate parseDateFromSchedulePrefix(String schedule) throws ParseException {
        String[] args = schedule.trim().split("\\s+");

        if (args.length < 1) {
            throw new ParseException("Interview Date should be in the format: yyyy-MM-dd");
        }

        return ParserUtil.parseDate(args[0]);
    }

    /**
     * Parses a {@code String schedule} into a {@code ArrayList<LocalTime>}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static ArrayList<LocalTime> parseStartEndTimeFromSchedulePrefix(String schedule) throws ParseException {
        String[] args = schedule.trim().split("\\s+");

        if (args.length < 3) {
            throw new ParseException(Schedule.MESSAGE_DATE_TIME_CONSTRAINTS);
        }

        LocalTime startTime = ParserUtil.parseTime(args[1]);
        LocalTime endTime = ParserUtil.parseTime(args[2]);

        if (!checkStartEndDateTime(startTime, endTime)) {
            throw new ParseException(MESSAGE_SCHEDULE_START_TIME_BEFORE_END_TIME);
        }

        if (!isValidDuration(startTime, endTime)) {
            throw new ParseException(MESSAGE_SCHEDULE_INVALID_DURATION);
        }

        ArrayList<LocalTime> starEndTimeList = new ArrayList<>();
        starEndTimeList.add(startTime);
        starEndTimeList.add(endTime);
        return starEndTimeList;
    }



    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String label} into a {@code Label}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code label} is invalid.
     */
    public static Label parseLabel(String label) throws ParseException {
        requireNonNull(label);
        String trimmedLabel = label.trim();
        if (!Label.isValidLabel(trimmedLabel)) {
            throw new ParseException(Label.MESSAGE_CONSTRAINTS);
        }

        return new Label(trimmedLabel);
    }

    /**
     * Parses a {@code String path} into a {@link Path}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException
     */
    public static Path parsePath(String path) throws ParseException {
        requireNonNull(path);
        String trimmedPath = path.trim();

        if (!trimmedPath.isEmpty() && !trimmedPath.endsWith(".json")) {
            trimmedPath += FILE_EXTENSION_JSON;
        }

        if (!FileUtil.isValidPath(trimmedPath)) {
            throw new ParseException(MESSAGE_INVALID_FILE_PATH);
        }

        return Paths.get(trimmedPath);
    }

    /**
     * Parses a {@code String date} into a {@code LocalDate}.
     * The format of the LocalDate will be in the format yyyy-MM-dd.
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDateTime = date.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate formattedDate;
        try {
            formattedDate = java.time.LocalDate.parse(trimmedDateTime, formatter);
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }
        return formattedDate;
    }

    /**
     * Parses a {@code String time} into a {@code LocalTime}.
     * The format of the LocalDate will be in the format HH:mm.
     * @throws ParseException if the given {@code time} is invalid.
     */
    public static LocalTime parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedDateTime = time.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime formattedTime;
        try {
            formattedTime = LocalTime.parse(trimmedDateTime, formatter);
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_TIME);
        }
        return formattedTime;
    }

    /**
     * Parses a {@code String mode} into an {@code Mode}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code mode} is invalid.
     */
    public static Mode parseMode(String mode) throws ParseException {
        requireNonNull(mode);
        String trimmedMode = mode.trim();
        if (Mode.isValidMode(trimmedMode)) {
            return trimmedMode.equalsIgnoreCase("online") ? Mode.ONLINE : Mode.OFFLINE;
        } else {
            throw new ParseException(Mode.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String theme} into an {@code Theme}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code theme} is invalid.
     */
    public static Theme parseTheme(String theme) throws ParseException {
        requireNonNull(theme);
        String trimmedTheme = theme.trim();
        try {
            return Theme.stringToTheme(trimmedTheme);
        } catch (IllegalValueException ive) {
            throw new ParseException(Theme.MESSAGE_CONSTRAINTS);
        }
    }

    private static String getLongRemarkErrorMessage(int providedRemarkCharacterCount) {
        return String.format(ParserUtil.MESSAGE_REMARK_TOO_LONG, MAX_REMARK_LENGTH, providedRemarkCharacterCount);
    }
}
