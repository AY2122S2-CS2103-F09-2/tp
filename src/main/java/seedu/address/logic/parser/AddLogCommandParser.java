package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ArgumentMultimap.arePrefixesPresent;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddLogCommand;
import seedu.address.logic.commands.AddLogCommand.AddLogDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddLogCommand object
 */
public class AddLogCommandParser implements Parser<AddLogCommand> {

    public static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLogCommand.MESSAGE_USAGE);

    /**
     * Parses a string of arguments and creates an {@code AddLogCommand}.
     *
     * @throws ParseException if command string is formatted wrongly
     */
    public AddLogCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TITLE, PREFIX_DESCRIPTION);

        Index index;

        // ensure title prefix and index are present
        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE)) {
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }
        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }

        // parse
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_FORMAT, pe);
        }

        // wrap new log into helper class
        AddLogDescriptor addLogDescriptor = new AddLogDescriptor();

        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            addLogDescriptor.setNewTitle(ParserUtil.parseTitle(argMultimap
                    .getValue(PREFIX_TITLE).get()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            addLogDescriptor.setNewDescription(ParserUtil.parseDescription(argMultimap
                    .getValue(PREFIX_DESCRIPTION).get()));
        }

        // check validity
        if (!addLogDescriptor.isTitleEdited()) {
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }

        return new AddLogCommand(index, addLogDescriptor);
    }
}
