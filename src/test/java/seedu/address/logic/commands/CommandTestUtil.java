package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CANDIDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBTITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.schedule.EditScheduleCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditScheduleDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_JOBTITLE_AMY = "Front End Developer";
    public static final String VALID_JOBTITLE_BOB = "IT Administrator";
    public static final String VALID_SCHEDULE_AMY = "10/02/2025 10:00";
    public static final String VALID_SCHEDULE_BOB = "11/03/2025 10:00";
    public static final String VALID_REMARK_LEETCODE = "Likes to solve leetcode problems.";
    public static final String VALID_REMARK_PASTTIME = "Likes to play video games.";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_LABEL_AMY = "Accepted";
    public static final String VALID_LABEL_BOB = "Rejected";
    public static final String VALID_FOLDER_PATH = "for_tests_only";
    public static final String VALID_FILE_PATH = VALID_FOLDER_PATH + "test.json";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String JOBTITLE_DESC_AMY = " " + PREFIX_JOBTITLE + VALID_JOBTITLE_AMY;
    public static final String JOBTITLE_DESC_BOB = " " + PREFIX_JOBTITLE + VALID_JOBTITLE_BOB;
    public static final String SCHEDULE_DESC_AMY = " " + PREFIX_SCHEDULE + VALID_SCHEDULE_AMY;
    public static final String SCHEDULE_DESC_BOB = " " + PREFIX_SCHEDULE + VALID_SCHEDULE_BOB;
    public static final String REMARK_DESC_AMY = " " + PREFIX_REMARK + VALID_REMARK_LEETCODE;
    public static final String REMARK_DESC_BOB = " " + PREFIX_REMARK + VALID_REMARK_LEETCODE;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String LABEL_DESC_AMY = " " + PREFIX_LABEL + VALID_LABEL_AMY;
    public static final String LABEL_DESC_BOB = " " + PREFIX_LABEL + VALID_LABEL_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_JOBTITLE_DESC = " " + PREFIX_JOBTITLE + "U1/UX D3s!ng3r"; // '!' not allowed
    // in job titles
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_LABEL_DESC = " " + PREFIX_LABEL + "yabdabadoo";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final Index INDEX_FIRST_PERSON = Index.fromZeroBased(0);
    public static final Index INDEX_SECOND_PERSON = Index.fromZeroBased(1);

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final String VALID_DATE = "2025-03-15";
    public static final String VALID_START_TIME = "09:00";
    public static final String VALID_END_TIME = "12:00";
    public static final String VALID_SCHEDULE = "2025-03-15 09:00 12:00";
    public static final String VALID_MODE = "online";
    public static final int VALID_INDEX = 2;
    public static final String VALID_DATE_2 = "2025-05-15";
    public static final String VALID_START_TIME_2 = "10:00";
    public static final String VALID_END_TIME_2 = "17:00";
    public static final String VALID_MODE_2 = "offline";

    public static final String INVALID_DATE = "2020-13-10";
    public static final String INVALID_START_TIME = "27:00";
    public static final String INVALID_END_TIME = "27:00";
    public static final String INVALID_MODE = PREFIX_MODE + "hybrid!";
    public static final EditScheduleCommand.EditScheduleDescriptor DESC_SCHEDULE_1;
    public static final EditScheduleCommand.EditScheduleDescriptor DESC_SCHEDULE_2;

    public static final String VALID_SCHEDULE_DESC = " " + PREFIX_SCHEDULE + VALID_SCHEDULE;
    public static final String VALID_MODE_DESC = " " + PREFIX_MODE + VALID_MODE;
    public static final String VALID_CANDIDATE_INDEX_DESC = " " + PREFIX_CANDIDATE + VALID_INDEX;

    public static final String INVALID_SCHEDULE_DESC = " " + PREFIX_SCHEDULE + INVALID_DATE;
    public static final String INVALID_SCHEDULE_DATE_DESC = " " + PREFIX_SCHEDULE + INVALID_DATE + " "
            + VALID_START_TIME + " " + VALID_END_TIME;
    public static final String INVALID_SCHEDULE_START_TIME_DESC = " " + PREFIX_SCHEDULE + VALID_DATE + " "
            + INVALID_START_TIME + " " + VALID_END_TIME;

    public static final String INVALID_SCHEDULE_END_TIME_DESC = " " + PREFIX_SCHEDULE + VALID_DATE + " "
            + VALID_START_TIME + " " + INVALID_END_TIME;
    public static final String INVALID_MODE_DESC = " " + PREFIX_MODE + INVALID_MODE;
    public static final String INVALID_CANDIDATE_INDEX_DESC = PREFIX_CANDIDATE + "-2";

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withJobTitle(VALID_JOBTITLE_AMY)
                .withLabel(VALID_LABEL_AMY).withTags(VALID_TAG_FRIEND).build();

        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withJobTitle(VALID_JOBTITLE_BOB)
                .withLabel(VALID_LABEL_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

        DESC_SCHEDULE_1 = new EditScheduleDescriptorBuilder().withDate(VALID_DATE)
                .withStartTime(VALID_START_TIME).withEndTime(VALID_END_TIME)
                .withMode(VALID_MODE).build();

        DESC_SCHEDULE_2 = new EditScheduleDescriptorBuilder().withDate(VALID_DATE_2)
                .withStartTime(VALID_START_TIME_2).withEndTime(VALID_END_TIME_2)
                .withMode(VALID_MODE_2).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }
}
