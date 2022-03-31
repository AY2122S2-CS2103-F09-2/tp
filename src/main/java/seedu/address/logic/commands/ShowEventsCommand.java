package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import seedu.address.model.Model;

/**
 * Lists all events in the address book to the user.
 */
public class ShowEventsCommand extends Command {

    public static final String COMMAND_WORD = "listevents";
    public static final String COMMAND_ALIAS = "le";

    public static final String MESSAGE_USAGE = "showevents [-a]";

    public static final String MESSAGE_SUCCESS = "Listed upcoming events";

    public static final String MESSAGE_SUCCESS_ALL = "Listed all events";

    private final boolean isShowAllEvents;

    public ShowEventsCommand(Boolean showAllEvents) {
        this.isShowAllEvents = showAllEvents;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (!isShowAllEvents) {
            model.updateFilteredEventList(event -> event.getDateTime().isAfterNow());
            return new CommandResult(MESSAGE_SUCCESS, false, false, true);
        }
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(MESSAGE_SUCCESS_ALL, false, false, true);
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof ShowEventsCommand);
    }

}
