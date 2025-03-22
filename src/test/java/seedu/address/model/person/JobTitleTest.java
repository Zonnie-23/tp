package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.jobapplication.JobTitle;

public class JobTitleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new JobTitle(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new JobTitle(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> JobTitle.isValidJobTitle(null));

        // invalid name
        assertFalse(JobTitle.isValidJobTitle("")); // empty string
        assertFalse(JobTitle.isValidJobTitle(" ")); // spaces only
        assertFalse(JobTitle.isValidJobTitle("^")); // only non-alphanumeric characters
        assertFalse(JobTitle.isValidJobTitle("engineer*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(JobTitle.isValidJobTitle("engineer")); // alphabets only
        assertTrue(JobTitle.isValidJobTitle("software engineer")); // numbers only
        assertTrue(JobTitle.isValidJobTitle("software engineer (level 7)")); // alphanumeric characters
        assertTrue(JobTitle.isValidJobTitle("Product Manager")); // with capital letters
        assertTrue(JobTitle.isValidJobTitle("UI/UX Designer")); // with selected special characters
    }

    @Test
    public void equals() {
        JobTitle jobTitle = new JobTitle("Valid Job Title");

        // same values -> returns true
        assertTrue(jobTitle.equals(new JobTitle("Valid Job Title")));

        // same object -> returns true
        assertTrue(jobTitle.equals(jobTitle));

        // null -> returns false
        assertFalse(jobTitle.equals(null));

        // different types -> returns false
        assertFalse(jobTitle.equals(5.0f));

        // different values -> returns false
        assertFalse(jobTitle.equals(new JobTitle("Other Valid Job Title")));
    }
}
