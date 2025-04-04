package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SCHEDULES;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalJobRoles.JOB_ROLES_NOT_IN_DEFAULT_LIST;
import static seedu.address.testutil.TypicalJobRoles.JOB_ROLE_IN_DEFAULT_LIST;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalSchedules.SCHEDULE_1;
import static seedu.address.testutil.TypicalSchedules.SCHEDULE_2;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.Theme;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.schedule.ScheduleBoard;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.ScheduleBoardBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4, Theme.DARK));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4, Theme.DARK);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void setScheduleBoardFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("schedule/board/file/path");
        modelManager.setScheduleBoardFilePath(path);
        assertEquals(path, modelManager.getScheduleBoardFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void getFirstPerson_nonEmptyList_returnsFirstPerson() {
        modelManager.addPerson(ALICE);
        assertEquals(modelManager.getFilteredPersonList().get(0), modelManager.getFirstPerson());
    }

    @Test
    public void getFirstPerson_emptyList_returnsNull() {
        modelManager.updateFilteredPersonList(p -> false);
        assertEquals(null, modelManager.getFirstPerson());
    }

    @Test
    public void hasJobRole_nullJobRole_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasJobRole(null));
    }

    @Test
    public void hasJobRole_jobRoleNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasJobRole(JOB_ROLES_NOT_IN_DEFAULT_LIST));
    }

    @Test
    public void hasJobRole_nonDefaultJjobRoleInAddressBook_returnsFalse() {
        modelManager.addJobRole(JOB_ROLES_NOT_IN_DEFAULT_LIST);
        assertTrue(modelManager.hasJobRole(JOB_ROLES_NOT_IN_DEFAULT_LIST));
    }

    @Test
    public void hasJobRole_jobRoleInAddressBook_returnsTrue() {
        assertTrue(modelManager.hasJobRole(JOB_ROLE_IN_DEFAULT_LIST));
    }

    @Test
    public void getJobRolesList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredJobRolesList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();
        ScheduleBoard scheduleBoard = new ScheduleBoardBuilder().withSchedule(SCHEDULE_1)
                .withSchedule(SCHEDULE_2).build();
        ScheduleBoard differentScheduleBoard = new ScheduleBoard();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs, scheduleBoard);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs, scheduleBoard);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs, differentScheduleBoard)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs, scheduleBoard)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different filteredSchedules -> returns false
        modelManager.updateFilteredScheduleList(s -> false);
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs, scheduleBoard)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredScheduleList(PREDICATE_SHOW_ALL_SCHEDULES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs, scheduleBoard)));
    }
}
