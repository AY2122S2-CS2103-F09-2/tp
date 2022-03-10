package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateLogException extends RuntimeException {
    public DuplicateLogException() {
        super("Operation would result in duplicate logs");
    }
}
