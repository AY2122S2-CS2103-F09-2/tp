package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a note or log tied to a Person in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTitle(String)}
 */
public class Log {

    // default values
    public static final String DEFAULT_NO_DESCRIPTION = "";

    // constraints
    public static final int TITLE_LENGTH_CONSTRAINT = 50;
    public static final String TITLE_CONSTRAINTS = "Titles of logs must satisfy:\n"
            + "1. Not be trivial (i.e. not empty or contain only spaces\n"
            + "2. Be at most " + Log.TITLE_LENGTH_CONSTRAINT + " characters long. "
            + "This is because of display limitations.";

    // immutable attributes
    private final String title;
    private final String description;


    /**
     * Constructs a Log object.
     *
     * @param title       Title of the log.
     * @param description Description tied to the log. Can be null.
     */
    public Log(String title, String description) {
        requireNonNull(title);
        checkArgument(isValidTitle(title), Log.TITLE_CONSTRAINTS);
        this.title = title;
        this.description = Objects.requireNonNullElse(description, Log.DEFAULT_NO_DESCRIPTION);
    }

    /**
     * Returns true if a given string is a valid title.
     */
    public static boolean isValidTitle(String title) {
        requireNonNull(title);
        return (title.length() > 0) // not empty string
                && (title.split(" ").length > 0) // at least one non-space item
                && (title.length() < Log.TITLE_LENGTH_CONSTRAINT); // within length limit
    }

    public String getDescription() {
        requireNonNull(this.description);
        return this.description;
    }

    public String getTitle() {
        requireNonNull(this.title);
        return this.title;
    }

    /**
     * Returns true if other Log is considered the same as this Log.
     * By convention, we consider two Logs the same if their titles are the same.
     *
     * @param other The other log.
     * @return True if other log is considered the same.
     */
    public boolean isSameLog(Log other) {
        return this.getTitle().equals(other.getTitle());
    }

    @Override
    public String toString() {
        return this.title + "\n" + this.description + "\n\n";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Log
                && (this.getTitle().equals(((Log) other).getTitle())) //getters ensure non-null
                && (this.getDescription().equals(((Log) other).getDescription())));
    }

    @Override
    public int hashCode() {
        return this.title.hashCode();
    }

}
