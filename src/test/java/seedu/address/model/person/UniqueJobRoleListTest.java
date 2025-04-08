package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalJobRoles.JOB_ROLES_NOT_IN_DEFAULT_LIST;
import static seedu.address.testutil.TypicalJobRoles.JOB_ROLES_NOT_IN_DEFAULT_LIST_2;
import static seedu.address.testutil.TypicalJobRoles.JOB_ROLE_IN_DEFAULT_LIST;
import static seedu.address.testutil.TypicalJobRoles.JOB_ROLE_IN_DEFAULT_LIST_2;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicateJobRoleException;
import seedu.address.model.person.exceptions.JobRoleNotFoundException;

public class UniqueJobRoleListTest {

    private UniqueJobRoleList uniqueJobRoleList = new UniqueJobRoleList();

    @Test
    public void contains_nullJobRole_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueJobRoleList.contains(null));
    }

    @Test
    public void contains_jobRoleNotInList_returnsFalse() {
        assertFalse(uniqueJobRoleList.contains(JOB_ROLES_NOT_IN_DEFAULT_LIST));
    }

    @Test
    public void contains_jobRoleInList_returnsTrue() {
        uniqueJobRoleList.add(JOB_ROLES_NOT_IN_DEFAULT_LIST);
        assertTrue(uniqueJobRoleList.contains(JOB_ROLES_NOT_IN_DEFAULT_LIST));
        uniqueJobRoleList.remove(JOB_ROLES_NOT_IN_DEFAULT_LIST);
    }

    @Test
    public void contains_defaultJobRoleInList_returnsTrue() {
        assertTrue(uniqueJobRoleList.contains(JOB_ROLE_IN_DEFAULT_LIST));
    }

    @Test
    public void contains_jobRoleSetInList_returnsTrue() {
        assertTrue(uniqueJobRoleList.containsAll(new HashSet<>(Arrays.asList(JOB_ROLE_IN_DEFAULT_LIST,
                JOB_ROLE_IN_DEFAULT_LIST_2))));
    }

    @Test
    public void contains_jobRoleSetOneNotInList_returnsFalse() {
        assertFalse(uniqueJobRoleList.containsAll(new HashSet<>(Arrays.asList(JOB_ROLE_IN_DEFAULT_LIST,
                JOB_ROLES_NOT_IN_DEFAULT_LIST))));
    }

    @Test
    public void contains_jobRoleSetAllNotInList_returnsFalse() {
        assertFalse(uniqueJobRoleList.containsAll(new HashSet<>(Arrays.asList(JOB_ROLES_NOT_IN_DEFAULT_LIST,
                 JOB_ROLES_NOT_IN_DEFAULT_LIST_2))));
    }

    @Test
    public void add_nullJobRole_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueJobRoleList.add(null));
    }

    @Test
    public void add_duplicateJobRole_throwsDuplicateJobRoleException() {
        assertThrows(DuplicateJobRoleException.class, () -> uniqueJobRoleList.add(JOB_ROLE_IN_DEFAULT_LIST));
    }

    @Test
    public void remove_nullJobRole_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueJobRoleList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsJobRoleNotFoundException() {
        assertThrows(JobRoleNotFoundException.class, () -> uniqueJobRoleList.remove(JOB_ROLES_NOT_IN_DEFAULT_LIST));
    }

    @Test
    public void remove_existingJobRole_removesJobRole() {
        uniqueJobRoleList.add(JOB_ROLES_NOT_IN_DEFAULT_LIST);
        uniqueJobRoleList.remove(JOB_ROLES_NOT_IN_DEFAULT_LIST);
        UniqueJobRoleList expectedUniqueJobRoleList = new UniqueJobRoleList();
        assertEquals(expectedUniqueJobRoleList, uniqueJobRoleList);
    }

    @Test
    public void setJobRoles_nullUniqueJobRoleList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueJobRoleList.setJobRoles((UniqueJobRoleList) null));
    }

    @Test
    public void setJobRoles_list_replacesOwnListWithProvidedUniqueJobRoleList() {
        UniqueJobRoleList expectedUniqueJobRoleList = new UniqueJobRoleList();
        expectedUniqueJobRoleList.remove(JOB_ROLE_IN_DEFAULT_LIST);
        uniqueJobRoleList.setJobRoles(expectedUniqueJobRoleList);
        assertEquals(expectedUniqueJobRoleList, uniqueJobRoleList);
    }

    @Test
    public void setJobRoles_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueJobRoleList.setJobRoles((List<JobRole>) null));
    }

    @Test
    public void setJobRoles_list_replacesOwnListWithProvidedList() {
        List<JobRole> jobRoleList = Collections.singletonList(JOB_ROLE_IN_DEFAULT_LIST);
        uniqueJobRoleList.setJobRoles(jobRoleList);
        UniqueJobRoleList expectedUniqueJobRoleList = new UniqueJobRoleList();
        expectedUniqueJobRoleList.setJobRoles(Collections.emptyList());
        expectedUniqueJobRoleList.add(JOB_ROLE_IN_DEFAULT_LIST);
        assertEquals(expectedUniqueJobRoleList, uniqueJobRoleList);
    }

    @Test
    public void setJobRoles_listWithDuplicateJobRoles_throwsDuplicateJobRoleException() {
        List<JobRole> listWithDuplicateJobRoles = Arrays.asList(JOB_ROLE_IN_DEFAULT_LIST, JOB_ROLE_IN_DEFAULT_LIST);
        assertThrows(DuplicateJobRoleException.class, () -> uniqueJobRoleList
                .setJobRoles(listWithDuplicateJobRoles));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueJobRoleList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void equals() {
        UniqueJobRoleList uniqueJobRoleList2 = new UniqueJobRoleList();
        uniqueJobRoleList2.add(JOB_ROLES_NOT_IN_DEFAULT_LIST);

        // same object -> returns true
        assertTrue(uniqueJobRoleList.equals(uniqueJobRoleList));

        // same values -> returns true
        UniqueJobRoleList uniqueJobRoleListCopy = new UniqueJobRoleList();
        assertTrue(uniqueJobRoleList.equals(uniqueJobRoleListCopy));

        // different types -> returns false
        assertFalse(uniqueJobRoleList.equals(1));

        // null -> returns false
        assertFalse(uniqueJobRoleList.equals(null));

        // different person -> returns false
        assertFalse(uniqueJobRoleList.equals(uniqueJobRoleList2));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueJobRoleList.asUnmodifiableObservableList().toString(),
                uniqueJobRoleList.toString());
    }
}
