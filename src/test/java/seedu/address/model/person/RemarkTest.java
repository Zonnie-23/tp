package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.jobapplication.Remark;

public class RemarkTest {
    @Test
    public void equals() {
        Remark remark = new Remark("Hello");

        // same values -> equal
        Remark remarkCopy = new Remark(remark.value);
        assertEquals(remark, remarkCopy);

        // same object -> equal
        assertEquals(remark, remark);

        // different types -> not equal
        assertNotEquals(1, remark);

        // null -> not equal
        assertNotEquals(null, remark);

        // different remark -> not equal
        Remark differentRemark = new Remark("Bye");
        assertNotEquals(differentRemark, remark);
    }
}
