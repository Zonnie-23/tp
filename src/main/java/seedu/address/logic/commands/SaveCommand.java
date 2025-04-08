package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.isUnique;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.LogicManager.FILE_OPS_ERROR_FORMAT;
import static seedu.address.logic.LogicManager.FILE_OPS_PERMISSION_ERROR_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_FILE_EXISTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CANDIDATES_FILE_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULES_FILE_PATH;
import static seedu.address.logic.parser.CliSyntax.SUFFIX_OVERWRITE_FILE;
import static seedu.address.logic.parser.CliSyntax.SUFFIX_SAVE_ALL;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.schedule.ScheduleBoard;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonScheduleBoardStorage;
import seedu.address.storage.ManualStorage;
import seedu.address.storage.ManualStorageManager;
import seedu.address.storage.ScheduleBoardStorage;

/**
 * Exports the address book to a file
 */
public class SaveCommand extends Command {
    public static final String COMMAND_WORD = "save";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Saves all candidates' data in the application into "
            + "the file at the path provided. "
            + "Existing files **will not** be overwritten.\n"
            + "Parameters: " + PREFIX_CANDIDATES_FILE_PATH + "CANDIDATE_DETAILS_FILE_PATH (must be a valid file path)"
            + " " + PREFIX_SCHEDULES_FILE_PATH + "INTERVIEW_SCHEDULES_FILE_PATH (must be a valid file path)"
            + " [" + SUFFIX_SAVE_ALL + " (save all)] [" + SUFFIX_OVERWRITE_FILE + " (overwrite file)]\n"
            + "You should provide at least one out of the two required file paths.\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_CANDIDATES_FILE_PATH + "candidates_archive.json";
    public static final String MESSAGE_SAVE_CANDIDATES_FILE_SUCCESS = "Saved candidates' data to file at: '%1$s'";
    public static final String MESSAGE_SAVE_SCHEDULES_FILE_SUCCESS = "Saved interview schedules to file at: '%1$s'";

    private final ManualStorageManager storage;
    private final boolean shouldSaveAllData;
    private final boolean shouldOverwriteFile;

    /**
     * Constructor for {@code SaveCommand}. At least one of {@code candidateDetailsFilePath}
     * and {@code schedulesFilePath} must not be {@link ManualStorage#EMPTY_PATH}
     *
     * @param candidateDetailsFilePath Path to file to save candidates data to
     * @param schedulesFilePath Path to file to save interview schedule data to
     *                          (must be different from {@code candidateDetailsFilePath})
     * @param shouldSaveAllData Whether to save all data (instead of saving filtered ones)
     * @param shouldOverwriteFile Whether to overwrite existing file data
     */
    public SaveCommand(Path candidateDetailsFilePath, Path schedulesFilePath,
                       boolean shouldSaveAllData, boolean shouldOverwriteFile) {
        assertFilePathsAreValid(candidateDetailsFilePath, schedulesFilePath);

        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(candidateDetailsFilePath);
        ScheduleBoardStorage scheduleBoardStorage = new JsonScheduleBoardStorage(schedulesFilePath);
        this.storage = new ManualStorageManager(addressBookStorage, scheduleBoardStorage);
        this.shouldSaveAllData = shouldSaveAllData;
        this.shouldOverwriteFile = shouldOverwriteFile;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        StringBuilder successMessage = new StringBuilder();

        try {
            String saveAddressBookSuccessMessage = saveAddressBookAndGetMessage(model);
            String saveScheduleSuccessMessage = saveScheduleBoardAndGetMessage(model);

            successMessage.append(saveAddressBookSuccessMessage);
            if (!successMessage.isEmpty() && !saveScheduleSuccessMessage.isEmpty()) {
                successMessage.append(System.lineSeparator());
            }
            successMessage.append(saveScheduleSuccessMessage);
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return new CommandResult(successMessage.toString());
    }

    /**
     * Defensive programming: Asserts to ensure file paths provided are valid
     *
     * @param candidateDetailsFilePath a {@code Path} object referring to candidate details file
     * @param schedulesFilePath a {@code Path} object referring to interview schedule details file
     */
    private void assertFilePathsAreValid(Path candidateDetailsFilePath, Path schedulesFilePath) {
        requireAllNonNull(candidateDetailsFilePath, schedulesFilePath);

        boolean hasCandidatesFilePath = !candidateDetailsFilePath.equals(ManualStorage.EMPTY_PATH);
        boolean hasSchedulesFilePath = !schedulesFilePath.equals(ManualStorage.EMPTY_PATH);
        boolean hasAtLeastOneFilePath = hasCandidatesFilePath || hasSchedulesFilePath;
        boolean areBothFilesUnique = isUnique(List.<Path>of(candidateDetailsFilePath, schedulesFilePath));

        assert hasAtLeastOneFilePath && areBothFilesUnique;
    }

    /**
     * Helper method to get address book file path
     */
    private Path getAddressBookFilePath() {
        return this.storage.getAddressBookFilePath().toAbsolutePath().normalize();
    }

    /**
     * Helper method to get schedule board file path
     */
    private Path getScheduleBoardFilePath() {
        return this.storage.getScheduleBoardFilePath().toAbsolutePath().normalize();
    }

    private String saveAddressBookAndGetMessage(Model model) throws CommandException, IOException {
        if (canSaveAddressBook()) {
            AddressBook addressBookToSave = this.getAddressBookToSave(model);
            this.storage.saveAddressBook(addressBookToSave);
            return generateSuccessMessage(MESSAGE_SAVE_CANDIDATES_FILE_SUCCESS, this.getAddressBookFilePath());
        } else {
            return "";
        }
    }

    private String saveScheduleBoardAndGetMessage(Model model) throws CommandException, IOException {
        if (canSaveScheduleBoard()) {
            ScheduleBoard scheduleBoardToSave = this.getScheduleBoardToSave(model);
            this.storage.saveScheduleBoard(scheduleBoardToSave);
            return generateSuccessMessage(MESSAGE_SAVE_SCHEDULES_FILE_SUCCESS, this.getScheduleBoardFilePath());
        } else {
            return "";
        }
    }

    private boolean canSaveAddressBook() throws CommandException {
        if (!this.storage.hasAddressBookFilePath()) {
            return false;
        }
        return this.canWriteToFile(this.getAddressBookFilePath(), this.shouldOverwriteFile);
    }

    private boolean canSaveScheduleBoard() throws CommandException {
        if (!this.storage.hasScheduleBoardFilePath()) {
            return false;
        }
        return this.canWriteToFile(this.getScheduleBoardFilePath(), this.shouldOverwriteFile);
    }

    private boolean canWriteToFile(Path filePath, boolean shouldOverwriteFile) throws CommandException {
        if (filePath.toFile().exists() && !shouldOverwriteFile) {
            throw new CommandException(generateFailureMessage(MESSAGE_FILE_EXISTS, filePath));
        }
        return true;
    }

    private AddressBook getAddressBookToSave(Model model) {
        AddressBook addressBookToSave = new AddressBook(model.getAddressBook());
        if (!shouldSaveAllData) {
            addressBookToSave.setPersons(model.getFilteredPersonList());
        }
        return addressBookToSave;
    }

    private ScheduleBoard getScheduleBoardToSave(Model model) {
        ScheduleBoard scheduleBoardToSave = new ScheduleBoard(model.getScheduleBoard());
        if (!shouldSaveAllData) {
            scheduleBoardToSave.setSchedules(model.getFilteredScheduleList());
        }
        return scheduleBoardToSave;
    }

    private String getFilePathAsString(Path filePath) {
        return filePath.toAbsolutePath().toString();
    }

    private String generateSuccessMessage(String successMessageFormat, Path filePath) {
        return String.format(successMessageFormat, getFilePathAsString(filePath));
    }

    private String generateFailureMessage(String messageFormat, Path filePath) {
        return String.format(messageFormat, getFilePathAsString(filePath));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SaveCommand otherSaveCommand)) {
            return false;
        }

        return this.storage.equals(otherSaveCommand.storage)
                && this.shouldSaveAllData == otherSaveCommand.shouldSaveAllData
                && this.shouldOverwriteFile == otherSaveCommand.shouldOverwriteFile;
    }
}
