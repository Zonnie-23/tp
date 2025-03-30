package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILE_PATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_LEETCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SaveCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.ViewStatsCommand;
import seedu.address.logic.commands.schedule.AddScheduleCommand;
import seedu.address.logic.commands.schedule.ClearScheduleCommand;
import seedu.address.logic.commands.schedule.DeleteScheduleCommand;
import seedu.address.logic.commands.schedule.ListScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDetailsContainKeywordsPredicate;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.ScheduleBuilder;
import seedu.address.testutil.ScheduleUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new PersonDetailsContainKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_remark() throws Exception {
        assertTrue(parser.parseCommand(RemarkCommand.COMMAND_WORD
                + " " + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + VALID_REMARK_LEETCODE)
                instanceof RemarkCommand);
    }

    @Test
    public void parseCommand_save() throws Exception {
        assertTrue(parser.parseCommand(SaveCommand.COMMAND_WORD
                + " " + PREFIX_FILE + VALID_FILE_PATH) instanceof SaveCommand);
    }

    @Test
    public void parseCommand_view() throws Exception {
        assertTrue(parser.parseCommand(ViewCommand.COMMAND_WORD
                + " " + INDEX_FIRST_PERSON.getOneBased())
                instanceof ViewCommand);
    }

    @Test
    public void parseCommand_viewstats() throws Exception {
        assertTrue(parser.parseCommand(ViewStatsCommand.COMMAND_WORD) instanceof ViewStatsCommand);
        assertTrue(parser.parseCommand(ViewStatsCommand.COMMAND_WORD + " 3") instanceof ViewStatsCommand);
    }

    @Test
    public void parseCommand_addSchedule() throws Exception {
        Schedule schedule = new ScheduleBuilder().build();
        AddScheduleCommand command = (AddScheduleCommand) parser.parseCommand(
                ScheduleUtil.getAddScheduleCommand(schedule));
        assertEquals(new AddScheduleCommand(INDEX_FIRST, schedule), command);
    }

    @Test
    public void parseCommand_listSchedule() throws Exception {
        assertTrue(parser.parseCommand(ListScheduleCommand.COMMAND_WORD) instanceof ListScheduleCommand);
        assertTrue(parser.parseCommand(ListScheduleCommand.COMMAND_WORD + " 3") instanceof ListScheduleCommand);
    }

    @Test
    public void parseCommand_clearSchedule() throws Exception {
        assertTrue(parser.parseCommand(ClearScheduleCommand.COMMAND_WORD) instanceof ClearScheduleCommand);
        assertTrue(parser.parseCommand(ClearScheduleCommand.COMMAND_WORD + " 3")
                instanceof ClearScheduleCommand);
    }

    @Test
    public void parseCommand_deleteSchedule() throws Exception {
        DeleteScheduleCommand command = (DeleteScheduleCommand) parser.parseCommand(
                DeleteScheduleCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteScheduleCommand(INDEX_FIRST), command);
    }
}
