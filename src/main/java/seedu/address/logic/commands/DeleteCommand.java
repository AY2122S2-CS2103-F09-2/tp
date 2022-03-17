package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;


/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "deletefriend";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the friend identified by index or name.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME (name of a friend who exists in Amigos) OR "
            + "INDEX (a positive integer)\n"
            + "Example 1: " + COMMAND_WORD + " " + PREFIX_NAME + "John Doe\n"
            + "Example 2: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted friend: %1$s";

    private final Name nameOfPersonToDelete;
    private final Index targetIndex;
    private final boolean isDeletionByIndex;

    /**
     * Constructs a DeleteCommand for deletion by name
     *
     * @param nameOfPersonToDelete The name of the person to be deleted
     */
    public DeleteCommand(Name nameOfPersonToDelete) {
        this.nameOfPersonToDelete = nameOfPersonToDelete;
        this.targetIndex = null;
        this.isDeletionByIndex = false;
    }

    /**
     * Constructs a DeleteCommand for deletion by index
     *
     * @param targetIndex The index of the person to be deleted on the filtered list on GUI
     */
    public DeleteCommand(Index targetIndex) {
        this.nameOfPersonToDelete = null;
        this.targetIndex = targetIndex;
        this.isDeletionByIndex = true;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);

        if (isDeletionByIndex) { //deletion by index
            List<Person> lastShownList = model.getFilteredPersonList();
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deletePerson(personToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));

        } else { //deletion by name
            Person personWithNameToDelete = new Person(nameOfPersonToDelete);

            if (!model.hasPerson(personWithNameToDelete)) { //model.hasPerson considers 2 Persons with same name
                //to be the same Person
                throw new CommandException(Messages.MESSAGE_PERSON_DOES_NOT_EXIST);
            }
            //todo : make this more oop
            ObservableList<Person> personList = model.getAddressBook().getPersonList();
            Person personToDelete = personWithNameToDelete; //dummy person

            for (Person personFromList : personList) {
                if (personFromList.isSamePerson(personWithNameToDelete)) {
                    personToDelete = new Person(personFromList.getName(),
                            personFromList.getPhone(), personFromList.getEmail(),
                            personFromList.getAddress(), personFromList.getDescription(),
                            personFromList.getTags(), personFromList.getLogs());
                    model.deletePerson(personFromList);
                    break;
                }
            }
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof DeleteCommand) {
            DeleteCommand otherDeleteCommand = (DeleteCommand) other;
            if (otherDeleteCommand.isDeletionByIndex && this.isDeletionByIndex) {
                //assertion to ensure that if it is deletion by index, then targetIndex will not be null
                assert(otherDeleteCommand.targetIndex != null && this.targetIndex != null);
                return otherDeleteCommand.targetIndex.equals(this.targetIndex);
            } else if (!otherDeleteCommand.isDeletionByIndex && !this.isDeletionByIndex) {
                //assertion to ensure that if it is deletion by name, then name will not be null
                assert(otherDeleteCommand.nameOfPersonToDelete != null && this.nameOfPersonToDelete != null);
                return otherDeleteCommand.nameOfPersonToDelete.equals(this.nameOfPersonToDelete);
            }
        }
        return false;
    }
}
