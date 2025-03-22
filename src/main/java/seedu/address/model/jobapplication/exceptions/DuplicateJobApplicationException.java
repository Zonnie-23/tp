package seedu.address.model.jobapplication.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateJobApplicationException extends RuntimeException {
    public DuplicateJobApplicationException() {
        super("Operation would result in duplicate applications");
    }
}
