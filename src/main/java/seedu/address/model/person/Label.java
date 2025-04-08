package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a persons Label as Unreviewed, Accepted, Rejected or Shortlisted
 */
public class Label {

    public static final String MESSAGE_CONSTRAINTS =
            "Labels can only be Unreviewed, Accepted, Rejected or Shortlisted";

    public static final String VALIDATION_REGEX = "^(?i)(Unreviewed|Accepted|Rejected|Shortlisted)$";

    public final String value;

    /**
     * Constructs a {@code Label}.
     *
     * @param labelName
     */
    public Label(String labelName) {
        requireNonNull(labelName);
        checkArgument(isValidLabel(labelName), MESSAGE_CONSTRAINTS);
        this.value = labelName;

    }

    /**
     * Returns true if test is a valid {@code Label}.
     */
    public static boolean isValidLabel(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        String output = this.value.toLowerCase();
        return output.substring(0, 1).toUpperCase() + output.substring(1);
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Label)) {
            return false;
        }

        Label otherName = (Label) other;
        return value.equalsIgnoreCase(otherName.value);
    }

}
