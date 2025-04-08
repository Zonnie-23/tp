package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class JobRoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new JobRole(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new JobRole(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> JobRole.isValidJobRole(null));

        // invalid name
        assertFalse(JobRole.isValidJobRole("")); // empty string
        assertFalse(JobRole.isValidJobRole(" ")); // spaces only
        assertFalse(JobRole.isValidJobRole("^")); // only non-alphanumeric characters
        assertFalse(JobRole.isValidJobRole("engineer*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(JobRole.isValidJobRole("engineer")); // alphabets only
        assertTrue(JobRole.isValidJobRole("software engineer")); // numbers only
        assertTrue(JobRole.isValidJobRole("software engineer (level 7)")); // alphanumeric characters
        assertTrue(JobRole.isValidJobRole("Product Manager")); // with capital letters
        assertTrue(JobRole.isValidJobRole("UI Designer")); // with selected special characters
    }

    @Test
    public void equals() {
        JobRole jobRole = new JobRole("Valid Job Role");

        // same values -> returns true
        assertTrue(jobRole.equals(new JobRole("Valid Job Role")));

        // same case-insensitve values -> returns true
        assertTrue(jobRole.equals(new JobRole("ValiD JoB RolE")));

        // same object -> returns true
        assertTrue(jobRole.equals(jobRole));

        // null -> returns false
        assertFalse(jobRole.equals(null));

        // different types -> returns false
        assertFalse(jobRole.equals(5.0f));

        // different values -> returns false
        assertFalse(jobRole.equals(new JobRole("Other Valid Job Role")));
    }
}
