package seedu.address.logic.parser.schedule;


import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_SCHEDULE_INVALID_DURATION;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CANDIDATE_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCHEDULE_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCHEDULE_DURATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCHEDULE_DURATION_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCHEDULE_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCHEDULE_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CANDIDATE_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CANDIDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_DATE;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_TIME;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.schedule.AddScheduleCommand;
import seedu.address.model.person.Mode;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.ScheduleBuilder;

public class AddScheduleCommandParserTest {
    private AddScheduleCommandParser parser = new AddScheduleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Schedule expectedSchedule = new ScheduleBuilder().build();
        Index index = Index.fromZeroBased(VALID_INDEX);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_CANDIDATE_INDEX_DESC
                        + VALID_SCHEDULE_DESC
                        + VALID_MODE_DESC,
                new AddScheduleCommand(index, expectedSchedule));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.getErrorMessageForMissingPrefixes(PREFIX_CANDIDATE));;

        // missing index prefix
        assertParseFailure(parser, VALID_INDEX + VALID_SCHEDULE_DESC + VALID_MODE_DESC, expectedMessage);

        expectedMessage = String.format(Messages.getErrorMessageForMissingPrefixes(PREFIX_SCHEDULE));;

        // missing schedule prefix
        assertParseFailure(parser, VALID_CANDIDATE_INDEX_DESC + VALID_SCHEDULE + VALID_MODE_DESC,
                expectedMessage);

        expectedMessage = String.format(Messages.getErrorMessageForMissingPrefixes(PREFIX_MODE));
        // missing mode prefix
        assertParseFailure(parser, VALID_CANDIDATE_INDEX_DESC + VALID_SCHEDULE_DESC + VALID_MODE,
                expectedMessage);
        expectedMessage = String.format(Messages.getErrorMessageForMissingPrefixes(PREFIX_CANDIDATE,
                PREFIX_SCHEDULE, PREFIX_MODE));
        // all prefixes missing
        assertParseFailure(parser, VALID_INDEX + VALID_SCHEDULE + VALID_MODE, expectedMessage);
    }

    @Test
    public void parse_invalidCandidateIndex_throwsParseException() {
        String invalidCandidateIndexDesc = " c/abc";
        String userInput = invalidCandidateIndexDesc + VALID_SCHEDULE_DESC + VALID_MODE_DESC;

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScheduleCommand.MESSAGE_USAGE);

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid candidate index
        assertParseFailure(parser, " " + INVALID_CANDIDATE_INDEX_DESC + INVALID_SCHEDULE_DATE_DESC
                + VALID_MODE_DESC, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScheduleCommand.MESSAGE_USAGE));

        // invalid date
        assertParseFailure(parser, VALID_CANDIDATE_INDEX_DESC + INVALID_SCHEDULE_DATE_DESC
                        + VALID_MODE_DESC, MESSAGE_INVALID_DATE);

        // invalid startTime
        assertParseFailure(parser, VALID_CANDIDATE_INDEX_DESC + INVALID_SCHEDULE_START_TIME_DESC
                        + VALID_MODE_DESC, MESSAGE_INVALID_TIME);

        // invalid endTime
        assertParseFailure(parser, VALID_CANDIDATE_INDEX_DESC + INVALID_SCHEDULE_END_TIME_DESC
                        + VALID_MODE_DESC, MESSAGE_INVALID_TIME);

        // invalid duration
        assertParseFailure(parser, VALID_CANDIDATE_INDEX_DESC + INVALID_SCHEDULE_DURATION_DESC
                + VALID_MODE_DESC, MESSAGE_SCHEDULE_INVALID_DURATION);

        // invalid duration
        assertParseFailure(parser, VALID_CANDIDATE_INDEX_DESC + INVALID_SCHEDULE_DURATION_DESC_2
                + VALID_MODE_DESC, MESSAGE_SCHEDULE_INVALID_DURATION);

        // invalid mode
        assertParseFailure(parser, VALID_CANDIDATE_INDEX_DESC + VALID_SCHEDULE_DESC
                        + INVALID_MODE_DESC, Mode.MESSAGE_CONSTRAINTS);


        // two invalid values, only first invalid value reported
        assertParseFailure(parser, VALID_CANDIDATE_INDEX_DESC + INVALID_SCHEDULE_START_TIME_DESC
                        + INVALID_MODE_DESC, MESSAGE_INVALID_TIME);


        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_CANDIDATE_INDEX_DESC + VALID_SCHEDULE_DESC
                        + VALID_MODE_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddScheduleCommand.MESSAGE_USAGE));
    }
}
