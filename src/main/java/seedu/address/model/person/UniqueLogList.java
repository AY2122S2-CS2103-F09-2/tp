package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicateLogException;
import seedu.address.model.person.exceptions.LogNotFoundException;

/**
 * A list of Logs that enforces the uniqueness between its elements and does not
 * allow nulls.
 *
 * <p>A log is considered unique by comparing {@code Log#isSameLog(Log)}. As such, adding and updating
 * of Logs uses Log#isSameLog(Log) for equality to ensure that the Log being added or updated is unique
 * in terms of "identity" in the UniqueLogList.Deleting of Logs uses Log#equals(Object) for equality to ensure
 * that the Log being deleted is exactly correct.
 *
 * <p>Supports a minimal set of list operations.
 *
 * @see Log#isSameLog(Log)
 */
public class UniqueLogList {

    // observable list allows Java FX listeners to track changes
    private final ObservableList<Log> internalList = FXCollections.observableArrayList();
    private final ObservableList<Log> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent log as the given argument.
     */
    public boolean contains(Log log) {
        requireAllNonNull(log);
        return this.internalList.stream().anyMatch(log::isSameLog);
    }

    /**
     * Returns true if the list contains a log that matches exactly the
     * given {@code Log}. A stronger form of contains.
     */
    public boolean containsExactly(Log log) {
        requireAllNonNull(log);
        return this.internalList.stream().anyMatch(log::equals);
    }

    /**
     * Adds a log to the list.
     * An identical log must not already exist in the list.
     */
    public void addLog(Log log) {
        requireNonNull(log);
        if (this.contains(log)) {
            throw new DuplicateLogException();
        }
        internalList.add(log);
    }

    /**
     * Replaces the {@code target} in the list with the {@code editedLog}.
     * The {@code target} log must exist in the list, and the identity of the {@code editedLog}
     * must be different from all existing logs in the list.
     */
    public void setLog(Log targetLog, Log editedLog) {
        requireAllNonNull(targetLog, editedLog);

        if (!targetLog.isSameLog(editedLog) && this.contains(editedLog)) { // trying to insert something already inside
            throw new DuplicateLogException();
        }

        int index = this.internalList.indexOf(targetLog);
        if (index == -1) {
            throw new LogNotFoundException();
        }

        this.internalList.set(index, editedLog);
    }

    /**
     * Removes the equivalent log from the list.
     * The log must exist in the list.
     */
    public void removeLog(Log toRemove) {
        requireNonNull(toRemove);
        if (!this.internalList.remove(toRemove)) {
            throw new LogNotFoundException();
        }
    }

    /**
     * Replaces the contents of the list with all of {@code logs}.
     * {@code logs} must not contain duplicate log.
     */
    public void setLogs(List<Log> logs) {
        requireAllNonNull(logs);
        if (!this.logsAreUnique(logs)) {
            throw new DuplicateLogException();
        }
        this.internalList.setAll(logs);
    }

    /**
     * Returns true if and only if {@code logs} contains only unique (by title) logs.
     */
    public boolean logsAreUnique(List<Log> logs) {
        int n = logs.size();
        for (int i = 0; i < n - 1; i++) { // iterate like N choose 2
            for (int j = i + 1; j < n; j++) {
                if (logs.get(i).isSameLog(logs.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the list of logs as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Log> asUnmodifiableObservableList() {
        return this.internalUnmodifiableList;
    }

    @Override
    public int hashCode() {
        return this.internalList.hashCode();
    }

}
