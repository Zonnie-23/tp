package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's job role in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidJobRole(String)}
 */
public class JobRole {
    public static final String MESSAGE_EXISTING_CONSTRAINTS =
            "Job role not recognised! Job role should be in saved list!\n"
            + "Use addJ to add another job role into the list.\n"
            + "Use listJ to list all job roles in saved list.";

    public static final String MESSAGE_NEW_CONSTRAINTS =
            "Invalid job role! Job role should only contain alphanumeric characters, round brackets: () and spaces.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}()][\\p{Alnum}() ]*";

    public static final int MAX_LENGTH = 30;

    public final String value;

    /**
     * Constructs a {@code JobRole}.
     *
     * @param jobRole A valid job role that the candidate applied for.
     */
    public JobRole(String jobRole) {
        requireNonNull(jobRole);
        checkArgument(isValidJobRole(jobRole), MESSAGE_NEW_CONSTRAINTS);
        value = jobRole;
    }

    /**
     * Returns true if a given string is a valid job role.
     */
    public static boolean isValidJobRole(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the raw value of this job role.
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "[" + value + "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof JobRole)) {
            return false;
        }

        JobRole otherName = (JobRole) other;
        return value.equalsIgnoreCase(otherName.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
