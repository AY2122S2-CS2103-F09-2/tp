package seedu.address.logic.commands;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Adds a log to a person in the address book.
 */
public class AddLogCommand extends Command {

    public static final String COMMAND_WORD = "addlog";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a log to an existing friend in Amigos. "
            + "Parameters: "
            +  "INDEX "
            + PREFIX_TITLE + "TITLE"
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_TITLE + "Likes apples";

    public static final String MESSAGE_ADD_LOG_SUCCESS = "New log added!";
    public static final String MESSAGE_DUPLICATE_LOG = "This log already exists for this friend.";
    public static final String MESSAGE_TITLE_NOT_INCLUDED = "Title of new log must be provided.";

    private final Index index;
    private final AddLogDescriptor addLogDescriptor;

    /**
     * Creates an AddLogCommand to add the specified {@code Log} to the
     * specified {@code Person}.
     */
    public AddLogCommand(Index index, AddLogDescriptor addLogDescriptor) {
        requireAllNonNull(index, addLogDescriptor);
        this.index = index;
        this.addLogDescriptor = addLogDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // get list of persons from model
        List<Person> lastShownList = model.getFilteredPersonList();

        // get person and modify
        if (this.index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToEdit = lastShownList.get(this.index.getZeroBased());
        Person addedLogPerson = createAddedLogPerson(personToEdit, this.addLogDescriptor);

        // add to address book
        model.setPerson(personToEdit, addedLogPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_ADD_LOG_SUCCESS);
    }

    /**
     * Creates a {@code Person} with the details of {@code personToEdit}, with logs modified by
     * {@code editPersonLogsDescriptor}.
     */
    private static Person createAddedLogPerson(Person personToEdit, AddLogDescriptor addLogDescriptor) throws CommandException {
        requireAllNonNull(personToEdit, addLogDescriptor);
        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Set<Tag> tags = personToEdit.getTags();
        List<Log> updatedLogs = addLogDescriptor.getLogs(personToEdit); // main logic encompassed here
        return new Person(name, phone, email, address, tags, updatedLogs);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddLogCommand)) {
            return false;
        }

        // cast
        AddLogCommand a = (AddLogCommand) other;
        return this.index.equals(a.index) &&
                this.addLogDescriptor.equals(a.addLogDescriptor);
    }

    /**
     * Stores the details of the edited log to edit a person's logs with, as well as the person's
     * original details.
     */
    public static class AddLogDescriptor {

        private String newTitle;
        private String newDescription;

        /**
         * Constructs a new {@code AddLogDescriptor} object.
         */
        public AddLogDescriptor() {
            this.newTitle = null;
            this.newDescription = null;
        }

        public void setNewTitle(String newTitle) {
            this.newTitle = newTitle;
        }

        public void setNewDescription(String newDescription) {
            this.newDescription = newDescription;
        }

        /**
         * Returns true if title has been edited.
         */
        public boolean isTitleEdited() {
            return this.newTitle != null;
        }

        /**
         * Returns a list of {@code Log} objects that include the {@code Person}'s original logs
         * as well as the new logs.
         */
        public List<Log> getLogs(Person personToEdit) throws CommandException {
            Log toAdd = new Log(this.newTitle, this.newDescription); // create log to be added
            if (personToEdit.containsLog(toAdd)) {
                throw new CommandException(MESSAGE_DUPLICATE_LOG); // ensure not a duplicate log being inserted
            }
            List<Log> newLogs = personToEdit.getLogs();
            newLogs.add(toAdd); // add log
            return newLogs;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof
            if (!(other instanceof AddLogDescriptor)) {
                return false;
            }

            // cast and check by wrapping into log object
            AddLogDescriptor a = (AddLogDescriptor) other;
            Log thisLog = new Log(this.newTitle, this.newDescription);
            Log otherLog = new Log(a.newTitle, a.newDescription);
            return thisLog.equals(otherLog);
        }
    }


}
