package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ArgumentMultimap.arePrefixesPresent;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOG_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TITLE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditLogCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FriendName;

public class EditLogCommandParser implements Parser<EditLogCommand> {
    public static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditLogCommand.MESSAGE_USAGE);
    public static final String MESSAGE_NO_EDITS_DETECTED = "No edits made!";

    public EditLogCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // tokenize
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_NAME, PREFIX_LOG_INDEX,
                        PREFIX_NEW_TITLE, PREFIX_NEW_DESCRIPTION);

        // check that log index is present
        if (!arePrefixesPresent(argMultimap, PREFIX_LOG_INDEX)) {
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }

        // initialize
        Index personIndex = null;
        Index logIndex = null;
        FriendName personName = null;
        EditLogCommand.EditLogDescriptor descriptor = new EditLogCommand.EditLogDescriptor();
        boolean hasIndex = !argMultimap.getPreamble().isEmpty();
        boolean hasName = arePrefixesPresent(argMultimap, PREFIX_NAME);

        // parse index or name
        if (hasIndex) {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } else if (hasName) {
            if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
                personName = ParserUtil.parseFriendName(argMultimap.getValue(PREFIX_NAME).get());
            }
        }

        // parse other arguments
        if (argMultimap.getValue(PREFIX_LOG_INDEX).isPresent()) {
            logIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_LOG_INDEX).get());
        }
        if (argMultimap.getValue(PREFIX_NEW_TITLE).isPresent()) {
            descriptor.setNewTitle(ParserUtil.parseTitle(
                    argMultimap.getValue(PREFIX_NEW_TITLE).get()));
        }
        if (argMultimap.getValue(PREFIX_NEW_DESCRIPTION).isPresent()) {
            descriptor.setNewDescription(ParserUtil.parseDescription(
                    argMultimap.getValue(PREFIX_NEW_DESCRIPTION).get()));
        }

        // check that an actual edit has taken place
        if (!descriptor.isEdited()) {
            throw new ParseException(MESSAGE_NO_EDITS_DETECTED);
        }

        // create command
        EditLogCommand command;
        if (hasIndex) {
            command = new EditLogCommand(personIndex, logIndex, descriptor);
        } else {
            command = new EditLogCommand(personName, logIndex, descriptor);
        }
        return command;
    }
}
