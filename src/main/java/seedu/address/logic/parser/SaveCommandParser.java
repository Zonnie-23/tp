package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.address.logic.parser.CliSyntax.SUFFIX_OVERRIDE_FILE;
import static seedu.address.logic.parser.CliSyntax.SUFFIX_SAVE_ALL;

import java.nio.file.Path;

import seedu.address.logic.commands.SaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser input arguments and creates a new {@code SaveCommand} object
 */
public class SaveCommandParser implements Parser<SaveCommand> {
    @Override
    public SaveCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_FILE, SUFFIX_SAVE_ALL, SUFFIX_OVERRIDE_FILE);

        if (argMultimap.getValue(PREFIX_FILE).isEmpty()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_FILE, SUFFIX_SAVE_ALL, SUFFIX_OVERRIDE_FILE);

        Path path = ParserUtil.parsePath(argMultimap.getValue(PREFIX_FILE).get());
        boolean shouldSaveAllData = validateBooleanFlag(argMultimap, SUFFIX_SAVE_ALL);
        boolean shouldOverrideFile = validateBooleanFlag(argMultimap, SUFFIX_OVERRIDE_FILE);

        return new SaveCommand(path, shouldSaveAllData, shouldOverrideFile);
    }

    private static boolean validateBooleanFlag(ArgumentMultimap argMultimap, Prefix prefix) throws ParseException {
        boolean hasFlag = argMultimap.getValue(prefix).isPresent();

        if (hasFlag) {
            if (!argMultimap.getValue(prefix).get().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
            }
        }

        return hasFlag;
    }
}
