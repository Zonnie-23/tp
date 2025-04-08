package seedu.address.model.person;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LabelTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Label(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Label(invalidName));
    }


    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Label.isValidLabel(null));

        // invalid name
        assertFalse(Label.isValidLabel("")); // empty string
        assertFalse(Label.isValidLabel("Applied"));
        assertFalse(Label.isValidLabel("Realised**1293"));
        assertFalse(Label.isValidLabel(" ")); // spaces only

        // valid name
        assertTrue(Label.isValidLabel("Rejected")); //
        assertTrue(Label.isValidLabel("Accepted")); //
        assertTrue(Label.isValidLabel("Shortlisted")); //
        assertTrue(Label.isValidLabel("Unreviewed")); //
    }



    @Test
    public void equals() {
        Label label = new Label("Unreviewed");

        // same values -> returns true
        assertTrue(label.equals(new Label("Unreviewed")));

        // same object -> returns true
        assertTrue(label.equals(label));

        // null -> returns false
        assertFalse(label.equals(null));

        // different types -> returns false
        assertFalse(label.equals(5.0f));

        // different values -> returns false
        assertFalse(label.equals(new Label("Shortlisted")));
    }

    @Test
    public void testToString() {
        Label label = new Label("UnReVieWed");

        assertTrue(label.toString().equals("Unreviewed"));

        label = new Label("accEpTeD");

        assertTrue(label.toString().equals("Accepted"));

        label = new Label("RejECted");

        assertTrue(label.toString().equals("Rejected"));

        label = new Label("ShoRTLIsted");

        assertTrue(label.toString().equals("Shortlisted"));


    }

}
