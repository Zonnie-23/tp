package seedu.address.model.jobapplication;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's schedule in the address book.
 * Guarantees: immutable; is always valid
 */
public class Schedule {
    public final String value;

    /**
     * Constructs a {@code Schedule}.
     *
     * @param schedule A valid datetime.
     */
    public Schedule(String schedule) {
        requireNonNull(schedule);
        value = schedule;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Schedule // instanceof handles nulls
                && value.equals(((Schedule) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
