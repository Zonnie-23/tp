package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalJobRoles.JOB_ROLES_NOT_IN_DEFAULT_LIST;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.Theme;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.JobRole;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.ReadOnlyScheduleBoard;
import seedu.address.model.schedule.Schedule;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code AddJCommand}.
 */
public class AddJCommandTest {

    @Test
    public void constructor_nullJobRole_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddJCommand(null));
    }

    @Test
    public void execute_jobRoleAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingJobRoleAdded modelStub = new ModelStubAcceptingJobRoleAdded();
        AddJCommand addJCommand = new AddJCommand(JOB_ROLES_NOT_IN_DEFAULT_LIST);

        CommandResult commandResult = addJCommand.execute(modelStub);

        String expectedMessage = String.format(AddJCommand.MESSAGE_SUCCESS, JOB_ROLES_NOT_IN_DEFAULT_LIST.value);
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(JOB_ROLES_NOT_IN_DEFAULT_LIST), modelStub.jobRoleAdded);
    }

    @Test
    public void execute_duplicateJobRole_throwsCommandException() {
        ModelStubWithJobRole modelStub = new ModelStubWithJobRole(JOB_ROLES_NOT_IN_DEFAULT_LIST);
        AddJCommand addJCommand = new AddJCommand(JOB_ROLES_NOT_IN_DEFAULT_LIST);
        assertThrows(CommandException.class, () -> addJCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        JobRole anothorJobRole = new JobRole("HR Manager");
        AddJCommand addValidJCommand = new AddJCommand(JOB_ROLES_NOT_IN_DEFAULT_LIST);
        AddJCommand addInvalidJCommand = new AddJCommand(anothorJobRole);

        // same object -> returns true
        assertTrue(addValidJCommand.equals(addValidJCommand));

        // same values -> returns true
        AddJCommand addValidCommandCopy = new AddJCommand(JOB_ROLES_NOT_IN_DEFAULT_LIST);
        assertTrue(addValidJCommand.equals(addValidCommandCopy));

        // different types -> returns false
        assertFalse(addValidJCommand.equals(1));

        // null -> returns false
        assertFalse(addValidJCommand.equals(null));

        // different job role -> returns false
        assertFalse(addValidJCommand.equals(addInvalidJCommand));
    }

    @Test
    public void toStringMethod() {
        AddJCommand addJCommand = new AddJCommand(JOB_ROLES_NOT_IN_DEFAULT_LIST);
        String expected = AddJCommand.class.getCanonicalName() + "{toAdd=" + JOB_ROLES_NOT_IN_DEFAULT_LIST + "}";
        assertEquals(expected, addJCommand.toString());
    }

    /**
     * A Model stub that has a job role already present.
     */
    private class ModelStubWithJobRole extends ModelStub {
        private final JobRole jobRole;

        ModelStubWithJobRole(JobRole jobRole) {
            requireNonNull(jobRole);
            this.jobRole = jobRole;
        }

        @Override
        public boolean hasJobRole(JobRole jobRole) {
            requireNonNull(jobRole);
            return this.jobRole.equals(jobRole);
        }
    }

    /**
     * A Model stub that accepts the job role being added.
     */
    private class ModelStubAcceptingJobRoleAdded extends ModelStub {
        final ArrayList<JobRole> jobRoleAdded = new ArrayList<>();

        @Override
        public boolean hasJobRole(JobRole jobRole) {
            requireNonNull(jobRole);
            return jobRoleAdded.stream().anyMatch(jobRole::equals);
        }

        @Override
        public void addJobRole(JobRole jobRole) {
            jobRoleAdded.add(jobRole);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A default Model stub that have all methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<seedu.address.model.person.Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person getFirstPerson() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSchedule(Schedule schedule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addSchedule(Schedule schedule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteSchedule(Schedule target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSchedule(Schedule scheduleToEdit, Schedule editedSchedule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void editCandidateInSchedule(Schedule target, Person editedCandidate) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public ObservableList<Schedule> getFilteredScheduleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSameDateTime(Schedule schedule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSameDateTimeEdit(Schedule schedule, Schedule scheduleToEdit) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredScheduleList(Predicate<Schedule> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyScheduleBoard getScheduleBoard() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Path getScheduleBoardFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setScheduleBoardFilePath(Path scheduleBoardFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setScheduleBoard(ReadOnlyScheduleBoard readOnlyScheduleBoard) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean hasJobRole(JobRole jobRole) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean hasJobRoles(Set<JobRole> jobRoles) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void deleteJobRoles(JobRole target) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void addJobRole(JobRole jobRole) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ObservableList<JobRole> getFilteredJobRolesList() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public Theme getTheme() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void setTheme(Theme theme) {
            throw new AssertionError("This method should not be called");
        }
    }
}
