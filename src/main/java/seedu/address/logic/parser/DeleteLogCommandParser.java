package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ArgumentMultimap.arePrefixesPresent;
import static seedu.address.logic.parser.CliSyntax.FLAG_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOG_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteLogCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the input arguments and creates a new DeleteLogCommand object.
 */
public class DeleteLogCommandParser implements Parser<DeleteLogCommand> {

    public static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLogCommand.MESSAGE_USAGE);


    /**
     * Parses a string of arguments and creates a {@code DeleteLogCommand}.
     *
     * @throws ParseException if arguments are formatted wrongly.
     */
    public DeleteLogCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // tokenize
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_LOG_INDEX, FLAG_ALL);

        // sanity check
        if (!(arePrefixesPresent(argMultimap, PREFIX_LOG_INDEX)
                || arePrefixesPresent(argMultimap, FLAG_ALL))) {
            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }

        // initialize
        Index personIndex = null;
        Index logIndex = null;
        boolean isForOnePerson = false;
        boolean isForDeletingAllLogs = false;

        // read possible arguments
        if (!argMultimap.getPreamble().isEmpty()) {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
            isForOnePerson = true;
        }
        if (argMultimap.getValue(PREFIX_LOG_INDEX).isPresent()) {
            logIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_LOG_INDEX).get());
        }
        if (arePrefixesPresent(argMultimap, FLAG_ALL)) {
            isForDeletingAllLogs = true;
        }

        // check for validity
        if (!((isForOnePerson && !isForDeletingAllLogs
                && personIndex != null && logIndex != null) // case 1: delete specific log of specific person
                || (isForDeletingAllLogs && isForOnePerson
                && personIndex != null && logIndex == null) // case 2: delete all logs of specific person
                || (isForDeletingAllLogs && !isForOnePerson
                && personIndex == null && logIndex == null))) { // case 3: delete all logs all persons

            throw new ParseException(MESSAGE_INVALID_FORMAT);
        }
        return new DeleteLogCommand(isForOnePerson, isForDeletingAllLogs, personIndex, logIndex);
    }
}
