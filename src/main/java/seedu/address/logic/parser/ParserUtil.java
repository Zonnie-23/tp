package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

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

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.JobTitle;
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
            "Format of date is not supported. Format of date supported is yyyy-MM-dd (e.g. 2025-02-03)";
    public static final String MESSAGE_INVALID_TIME = "Format of time should be HH:MM";

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
     * Parses a {@code String jobTitle} into an {@code JobTitle}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static JobTitle parseJobTitle(String jobTitle) throws ParseException {
        requireNonNull(jobTitle);
        String trimmedJobTitle = jobTitle.trim();
        if (!JobTitle.isValidJobTitle(trimmedJobTitle)) {
            throw new ParseException(JobTitle.MESSAGE_CONSTRAINTS);
        }
        return new JobTitle(trimmedJobTitle);
    }

    /**
     * Parses a {@code String remark} into a {@code Remark}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Remark parseRemark(String remark) {
        return new Remark(remark.trim());
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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
}
