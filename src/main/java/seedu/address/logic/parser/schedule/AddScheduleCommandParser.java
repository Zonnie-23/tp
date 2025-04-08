package seedu.address.logic.parser.schedule;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CANDIDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.schedule.AddScheduleCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Mode;
import seedu.address.model.schedule.Schedule;

/**
 * Parses input arguments and creates a new AddScheduleCommand object
 */
public class AddScheduleCommandParser implements Parser<AddScheduleCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddScheduleCommand
     * and returns an AddScheduleCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddScheduleCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScheduleCommand.MESSAGE_USAGE));
        }
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CANDIDATE, PREFIX_SCHEDULE, PREFIX_MODE);

        argMultimap.verifyNoMissingPrefixes(PREFIX_CANDIDATE, PREFIX_SCHEDULE, PREFIX_MODE);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CANDIDATE, PREFIX_SCHEDULE, PREFIX_MODE);

        if (!arePrefixesPresent(argMultimap, PREFIX_CANDIDATE, PREFIX_SCHEDULE, PREFIX_MODE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScheduleCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CANDIDATE).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddScheduleCommand.MESSAGE_USAGE), pe);
        }

        LocalDate date = ParserUtil.parseDateFromSchedulePrefix(argMultimap.getValue(PREFIX_SCHEDULE).get());
        ArrayList<LocalTime> startEndTime =
                ParserUtil.parseStartEndTimeFromSchedulePrefix(argMultimap.getValue(PREFIX_SCHEDULE).get());

        Mode mode = ParserUtil.parseMode(argMultimap.getValue(PREFIX_MODE).get());

        Schedule schedule = new Schedule(date, startEndTime.get(0), startEndTime.get(1), mode);

        return new AddScheduleCommand(index, schedule);
    }
}

