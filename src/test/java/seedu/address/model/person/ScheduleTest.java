package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.jobapplication.Schedule;

public class ScheduleTest {

    @Test
    public void equals() {
        Schedule schedule = new Schedule("Hello");

        // same object -> returns true
        assertTrue(schedule.equals(schedule));

        // same values -> returns true
        Schedule scheduleCopy = new Schedule(schedule.value);
        assertTrue(schedule.equals(scheduleCopy));

        // different types -> returns false
        assertFalse(schedule.equals(1));

        // null -> returns false
        assertFalse(schedule.equals(null));

        // different schedule -> returns false
        Schedule differentSchedule = new Schedule("Bye");
        assertFalse(schedule.equals(differentSchedule));
    }
}
