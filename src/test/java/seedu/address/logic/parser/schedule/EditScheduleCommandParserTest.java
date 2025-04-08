package seedu.address.logic.parser.schedule;


import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_SCHEDULE_INVALID_DURATION;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCHEDULE_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCHEDULE_DURATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCHEDULE_DURATION_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCHEDULE_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCHEDULE_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.SCHEDULE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_DATE;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_TIME;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.schedule.EditScheduleCommand;
import seedu.address.logic.commands.schedule.EditScheduleCommand.EditScheduleDescriptor;
import seedu.address.model.person.Mode;
import seedu.address.testutil.EditScheduleDescriptorBuilder;

public class EditScheduleCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditScheduleCommand.MESSAGE_USAGE);

    private final EditScheduleCommandParser parser = new EditScheduleCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_SCHEDULE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + SCHEDULE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + SCHEDULE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_SCHEDULE_DATE_DESC, MESSAGE_INVALID_DATE); // invalid date
        assertParseFailure(parser, "1" + INVALID_SCHEDULE_START_TIME_DESC,
                MESSAGE_INVALID_TIME); // invalid start time
        assertParseFailure(parser, "1" + INVALID_SCHEDULE_END_TIME_DESC,
                MESSAGE_INVALID_TIME); // invalid end time

        assertParseFailure(parser, "1" + INVALID_SCHEDULE_DURATION_DESC,
                MESSAGE_SCHEDULE_INVALID_DURATION); // invalid duration

        assertParseFailure(parser, "1" + INVALID_SCHEDULE_DURATION_DESC_2,
                MESSAGE_SCHEDULE_INVALID_DURATION); // invalid duration

        assertParseFailure(parser, "1" + INVALID_MODE_DESC, Mode.MESSAGE_CONSTRAINTS); // invalid mode

        // invalid schedule followed by valid mode
        assertParseFailure(parser, "1" + INVALID_SCHEDULE_DATE_DESC + VALID_MODE_DESC,
                MESSAGE_INVALID_DATE);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_SCHEDULE_DATE_DESC + INVALID_MODE_DESC,
                MESSAGE_INVALID_DATE);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + VALID_SCHEDULE_DESC + VALID_MODE_DESC;

        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder().withDate(VALID_DATE)
                .withStartTime(VALID_START_TIME).withEndTime(VALID_END_TIME).withMode(VALID_MODE).build();
        EditScheduleCommand expectedCommand = new EditScheduleCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + VALID_SCHEDULE_DESC;

        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder().withDate(VALID_DATE)
                .withStartTime(VALID_START_TIME).withEndTime(VALID_END_TIME).build();
        EditScheduleCommand expectedCommand = new EditScheduleCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
