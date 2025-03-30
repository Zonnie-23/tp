package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true)));

        // different new person -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false, ALICE)));

        // different old and new person -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false, ALICE)));

        // ensure that there is no new person
        assertEquals(commandResult.getNewPerson(),
                new CommandResult("feedback", false, false).getNewPerson());

        CommandResult commandResultWithNewPerson = new CommandResult("feedback", true, false, ALICE);
        CommandResult commandResultWithDifferentNewPerson = new CommandResult("feedback", true, false, BENSON);

        assertFalse(commandResult.equals(commandResultWithNewPerson));
        assertTrue(commandResultWithNewPerson.isPersonNew());
        assertFalse(commandResultWithNewPerson.isPersonUpdated());
        assertEquals(ALICE, commandResultWithNewPerson.getNewPerson());
        assertEquals(BENSON, commandResultWithDifferentNewPerson.getNewPerson());
        assertFalse(commandResultWithNewPerson.equals(commandResultWithDifferentNewPerson));

        CommandResult commandResultWithUpdatedPerson = new CommandResult("feedback", false, false, ALICE, BENSON);

        assertFalse(commandResult.equals(commandResultWithUpdatedPerson));
        assertFalse(commandResultWithUpdatedPerson.isPersonNew());
        assertTrue(commandResultWithUpdatedPerson.isPersonUpdated());
        assertEquals(ALICE, commandResultWithUpdatedPerson.getSelectedPerson());
        assertEquals(BENSON, commandResultWithUpdatedPerson.getNewPerson());

    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true).hashCode());

        // different index value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, false, ALICE).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", isPersonNew=" + commandResult.isPersonNew()
                + ", isPersonUpdated=" + commandResult.isPersonUpdated()
                + ", newPerson=" + commandResult.getNewPerson()
                + ", exit=" + commandResult.isExit() + "}";
        assertEquals(expected, commandResult.toString());
    }
}
