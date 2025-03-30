package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILE_PATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOLDER_PATH;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.schedule.ScheduleBoard;

public class SaveCommandTest {
    private static final String TEMP_FILE_NAME = "temporary_test.txt";

    private Path validFilePath;
    private Model model;

    @BeforeEach
    public void setUp() {
        this.validFilePath = Path.of(VALID_FILE_PATH);
        this.model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ScheduleBoard());
    }

    @Test
    public void execute_fileExistsAndShouldNotBeOverwritten_failure() throws IOException {
        FileUtil.createIfMissing(validFilePath);
        File file = validFilePath.toFile();

        SaveCommand saveCommand = new SaveCommand(validFilePath, false, false);
        assertCommandFailure(saveCommand, model,
                String.format(Messages.MESSAGE_FILE_EXISTS, validFilePath.toAbsolutePath()));

        file.delete();
    }

    @Test
    public void execute_fileDoesNotExistAndShouldNotBeOverwritten_success() throws IOException {
        File file = validFilePath.toFile();
        File tempNewFile = Path.of(VALID_FOLDER_PATH, TEMP_FILE_NAME).toFile();
        boolean haveMovedFile = false;

        if (file.exists()) {
            haveMovedFile = true;
            file.renameTo(tempNewFile);
        }

        SaveCommand saveCommand = new SaveCommand(validFilePath, false, true);
        String expectedMessage = String.format(SaveCommand.MESSAGE_SAVE_SUCCESS, this.validFilePath.toAbsolutePath());
        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new UserPrefs(), new ScheduleBoard());
        assertCommandSuccess(saveCommand, model, expectedMessage, expectedModel);

        if (haveMovedFile) {
            tempNewFile.renameTo(file);
        }
    }

    @Test
    public void execute_fileExistsAndCanBeOverwritten_success() throws IOException {
        FileUtil.createIfMissing(validFilePath);
        File file = validFilePath.toFile();

        SaveCommand saveCommand = new SaveCommand(validFilePath, false, true);
        String expectedMessage = String.format(SaveCommand.MESSAGE_SAVE_SUCCESS, this.validFilePath.toAbsolutePath());
        Model expectedModel = new ModelManager(
                new AddressBook(model.getAddressBook()), new UserPrefs(), new ScheduleBoard());
        assertCommandSuccess(saveCommand, model, expectedMessage, expectedModel);

        file.delete();
    }

    @Test
    public void equals() {
        final SaveCommand standardCommand = new SaveCommand(validFilePath);

        // same object -> equal
        assertEquals(standardCommand, standardCommand);

        // same values -> equal
        SaveCommand commandWithSameValues = new SaveCommand(validFilePath);
        assertEquals(standardCommand, commandWithSameValues);

        // null -> no equal
        assertNotEquals(null, standardCommand);

        // different types -> not equal
        assertNotEquals(new ClearCommand(), standardCommand);

        // different path -> not equal
        assertNotEquals(new SaveCommand(Path.of("/" + VALID_FILE_PATH)), standardCommand);

        // same path but different format (i.e., relative and absolute) -> equal
        assertEquals(new SaveCommand(validFilePath.toAbsolutePath()), standardCommand);

        // same path but different options -> not equal
        assertNotEquals(new SaveCommand(validFilePath.toAbsolutePath(), true, false), standardCommand);
        assertNotEquals(new SaveCommand(validFilePath.toAbsolutePath(), false, true), standardCommand);
        assertNotEquals(new SaveCommand(validFilePath.toAbsolutePath(), true, true), standardCommand);
    }
}
