package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.FLAG_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOG_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.DeleteLogCommandParser.MESSAGE_INVALID_FORMAT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LOG;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LOG;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteLogCommand;

public class DeleteLogCommandParserTest {

    private static final DeleteLogCommandParser parser = new DeleteLogCommandParser();
    private static final String MESSAGE_INVALID_INDEX = ParserUtil.MESSAGE_INVALID_INDEX;

    @Test
    public void parse_invalidFormat_failure() {

        Index personIndex = INDEX_FIRST_PERSON;
        Index logIndex = INDEX_FIRST_LOG;

        String args;

        // missing person index without all flag
        args = "" + PREFIX_LOG_INDEX + logIndex.getOneBased();
        assertParseFailure(parser, args, MESSAGE_INVALID_FORMAT);


        // missing log index without all flag
        args = "" + personIndex.getOneBased();
        assertParseFailure(parser, args, MESSAGE_INVALID_FORMAT);

        // valid person and log index but all flag present
        args = personIndex.getOneBased() + " " + PREFIX_LOG_INDEX + logIndex.getOneBased() + " " + FLAG_ALL;
        assertParseFailure(parser, args, MESSAGE_INVALID_FORMAT);

        // arguments after all flag
        args = FLAG_ALL + "some garbage";
        assertParseFailure(parser, args, MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_invalidPersonIndex_failure() {

        Index logIndex = INDEX_FIRST_LOG;

        String args;

        // empty string person index
        args = "" + PREFIX_LOG_INDEX + logIndex.getOneBased();
        assertParseFailure(parser, args, MESSAGE_INVALID_FORMAT);

        // non-numeric person index
        args = "qwerty" + " " + FLAG_ALL;
        assertParseFailure(parser, args, MESSAGE_INVALID_INDEX);
        args = "qwerty" + " " + PREFIX_LOG_INDEX + logIndex.getOneBased();
        assertParseFailure(parser, args, MESSAGE_INVALID_INDEX);

        // negative person index
        args = "-5" + " " + PREFIX_LOG_INDEX + logIndex.getOneBased();
        assertParseFailure(parser, args, MESSAGE_INVALID_INDEX);
        args = "-5" + " " + FLAG_ALL;
        assertParseFailure(parser, args, MESSAGE_INVALID_INDEX);

    }

    @Test
    public void parse_invalidLogIndex_failure() {

        Index personIndex = INDEX_FIRST_PERSON;

        String args;

        // empty string log index
        args = personIndex.getOneBased() + " " + PREFIX_LOG_INDEX + "";
        assertParseFailure(parser, args, MESSAGE_INVALID_INDEX);

        // non-numeric log index
        args = personIndex.getOneBased() + " " + PREFIX_LOG_INDEX + "qwerty";
        assertParseFailure(parser, args, MESSAGE_INVALID_INDEX);

        // negative log index
        args = personIndex.getOneBased() + " " + PREFIX_LOG_INDEX + "-5";
        assertParseFailure(parser, args, MESSAGE_INVALID_INDEX);

    }

    @Test
    public void parse_invalidPrefixesPresent_failure() {

        String args;

        // random garbage input
        args = "1 i/ string";
        assertParseFailure(parser, args, MESSAGE_INVALID_FORMAT);
        args = "1 i/string /g something else";
        assertParseFailure(parser, args, MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_validFormat_success() {

        DeleteLogCommand expectedCommand;
        Index personIndex = INDEX_FIRST_PERSON;
        Index logIndex = INDEX_FIRST_LOG;
        String args;

        // case 1: person and log
        args = personIndex.getOneBased() + " " + PREFIX_LOG_INDEX + logIndex.getOneBased();
        expectedCommand = new DeleteLogCommand(true, false, personIndex, logIndex);
        assertParseSuccess(parser, args, expectedCommand);

        // case 2: all of person
        args = personIndex.getOneBased() + " " + FLAG_ALL;
        expectedCommand = new DeleteLogCommand(true, true, personIndex, null);
        assertParseSuccess(parser, args, expectedCommand);

        // case 3: all persons
        args = " " + FLAG_ALL;
        expectedCommand = new DeleteLogCommand(false, true, null, null);
        assertParseSuccess(parser, args, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        DeleteLogCommand expectedCommand;
        Index personIndex = INDEX_FIRST_PERSON;
        Index logIndex1 = INDEX_FIRST_LOG;
        Index logIndex2 = INDEX_SECOND_LOG;
        String args;

        // case 1: person and log
        args = personIndex.getOneBased() + " "
                + PREFIX_LOG_INDEX + logIndex1.getOneBased() + " "
                + PREFIX_LOG_INDEX + logIndex2.getOneBased() + " ";
        expectedCommand = new DeleteLogCommand(true, false, personIndex, logIndex2);
        assertParseSuccess(parser, args, expectedCommand);

        // case 2: all of person
        args = personIndex.getOneBased() + " "
                + FLAG_ALL;
        expectedCommand = new DeleteLogCommand(true, true, personIndex, null);
        assertParseSuccess(parser, args, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedInvalidFieldsButLastValid_success() {
        DeleteLogCommand expectedCommand;
        Index personIndex = INDEX_SECOND_PERSON;
        String invalidLogIndex = " ";
        Index logIndex = INDEX_SECOND_LOG;
        String args;

        // case 1: person and log
        args = personIndex.getOneBased() + " "
                + PREFIX_LOG_INDEX + invalidLogIndex + " "
                + PREFIX_LOG_INDEX + logIndex.getOneBased() + " ";
        expectedCommand = new DeleteLogCommand(true, false, personIndex, logIndex);
        assertParseSuccess(parser, args, expectedCommand);

        // case 2: all of person
        args = personIndex.getOneBased() + " "
                + FLAG_ALL;
        expectedCommand = new DeleteLogCommand(true, true, personIndex, null);
        assertParseSuccess(parser, args, expectedCommand);
    }

}
