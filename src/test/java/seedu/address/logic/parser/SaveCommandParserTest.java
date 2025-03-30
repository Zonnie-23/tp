package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILE_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.address.logic.parser.CliSyntax.SUFFIX_OVERRIDE_FILE;
import static seedu.address.logic.parser.CliSyntax.SUFFIX_SAVE_ALL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SaveCommand;

public class SaveCommandParserTest {
    private SaveCommandParser parser;
    private String nonEmptyFilePath;

    @BeforeEach
    public void setUp() {
        this.nonEmptyFilePath = VALID_FILE_PATH;
        this.parser = new SaveCommandParser();
    }

    @Test
    public void parse_filePathSpecified_success() {
        String userInput = " " + PREFIX_FILE + this.nonEmptyFilePath;
        SaveCommand expectedCommand = new SaveCommand(Path.of(this.nonEmptyFilePath), false, false);
        assertParseSuccess(this.parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allFlagsProvided_success() {
        // Flag to save all data provided (either at the front or at the back)
        SaveCommand expectedCommand = new SaveCommand(Path.of(this.nonEmptyFilePath), true, false);
        String userInput = " " + SUFFIX_SAVE_ALL + " " + PREFIX_FILE + this.nonEmptyFilePath;
        assertParseSuccess(this.parser, userInput, expectedCommand);
        userInput = " " + PREFIX_FILE + this.nonEmptyFilePath + " " + SUFFIX_SAVE_ALL;
        assertParseSuccess(this.parser, userInput, expectedCommand);

        // Flag to override file provided (either at the front or at the back)
        expectedCommand = new SaveCommand(Path.of(this.nonEmptyFilePath), false, true);
        userInput = " " + PREFIX_FILE + this.nonEmptyFilePath + " " + SUFFIX_OVERRIDE_FILE;
        assertParseSuccess(this.parser, userInput, expectedCommand);
        userInput = " " + SUFFIX_OVERRIDE_FILE + " " + PREFIX_FILE + this.nonEmptyFilePath;
        assertParseSuccess(this.parser, userInput, expectedCommand);

        // All optional flags provided (at the front, at the back, or both)
        expectedCommand = new SaveCommand(Path.of(this.nonEmptyFilePath), true, true);
        userInput = " " + PREFIX_FILE + this.nonEmptyFilePath + " " + SUFFIX_OVERRIDE_FILE + " " + SUFFIX_SAVE_ALL;
        assertParseSuccess(this.parser, userInput, expectedCommand);
        userInput = " " + SUFFIX_SAVE_ALL + " " + SUFFIX_OVERRIDE_FILE + " " + PREFIX_FILE + this.nonEmptyFilePath;
        assertParseSuccess(this.parser, userInput, expectedCommand);
        userInput = " " + SUFFIX_OVERRIDE_FILE + " " + PREFIX_FILE + this.nonEmptyFilePath + " " + SUFFIX_SAVE_ALL;
        assertParseSuccess(this.parser, userInput, expectedCommand);
    }

    @Test
    public void parse_filePathNotSpecified_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE);

        // with trailing whitespace (if user mistyped)
        assertParseFailure(this.parser, " ", expectedMessage);

        // without trailing whitespace
        assertParseFailure(this.parser, "", expectedMessage);

        // optional flags given without file path
        assertParseFailure(this.parser, " " + SUFFIX_SAVE_ALL + " " + SUFFIX_OVERRIDE_FILE, expectedMessage);
    }

    @Test
    public void parse_optionalFlagsProvidedWithArguments_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE);

        String userInput = " " + SUFFIX_OVERRIDE_FILE + "yes";
        assertParseFailure(this.parser, userInput, expectedMessage);
        userInput = " " + SUFFIX_SAVE_ALL + "true";
        assertParseFailure(this.parser, userInput, expectedMessage);
        userInput = " " + SUFFIX_OVERRIDE_FILE + "1 " + SUFFIX_SAVE_ALL + "1";
        assertParseFailure(this.parser, userInput, expectedMessage);
    }
}
