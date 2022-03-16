package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_ADDFRIEND_DESC_A;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_ADDFRIEND_DESC_B;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATETIME_DESC_A;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATETIME_DESC_B;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DESCRIPTION_DESC_A;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DESCRIPTION_DESC_B;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_A;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_B;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_REMOVEFRIEND_DESC_A;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_REMOVEFRIEND_DESC_B;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_ADDFRIEND_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_REMOVE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATETIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATETIME_OTHER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION_OTHER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_OTHER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_FRIENDNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_FRIENDNAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.DateTime;
import seedu.address.model.person.Name;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditEventCommandParserTest {

    private static final String ADDFRIENDS_EMPTY = " " + PREFIX_ADD_FRIENDNAME;
    private static final String REMOVEFRIENDS_EMPTY = " " + PREFIX_REMOVE_FRIENDNAME;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE);

    private EditEventCommandParser parser = new EditEventCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_EVENT_NAME, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditEventCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_EVENT_NAME, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_EVENT_DESCRIPTION, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_EVENT_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_EVENT_DATETIME_DESC, DateTime.MESSAGE_CONSTRAINTS); // invalid Datetime
//        assertParseFailure(parser, "1" + INVALID_EVENT_DESCRIPTION_DESC, Description.MESSAGE_CONSTRAINTS); // invalid Description
        assertParseFailure(parser, "1" + INVALID_EVENT_ADDFRIEND_DESC, Name.MESSAGE_CONSTRAINTS); // invalid Name
        assertParseFailure(parser, "1" + INVALID_EVENT_REMOVE_DESC, Name.MESSAGE_CONSTRAINTS); // invalid Name

        // invalid Datetime followed by valid description
        assertParseFailure(parser, "1" + INVALID_EVENT_DATETIME_DESC + VALID_EVENT_DESCRIPTION, DateTime.MESSAGE_CONSTRAINTS);

        // valid Datetime followed by invalid description. The test case for invalid description followed by valid Datetime
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + EVENT_DESCRIPTION_DESC_A + INVALID_EVENT_DATETIME_DESC, DateTime.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + EVENT_ADDFRIEND_DESC_A + EVENT_ADDFRIEND_DESC_B + ADDFRIENDS_EMPTY, Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + EVENT_REMOVEFRIEND_DESC_A + REMOVEFRIENDS_EMPTY + EVENT_REMOVEFRIEND_DESC_B, Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + ADDFRIENDS_EMPTY + EVENT_ADDFRIEND_DESC_A + EVENT_REMOVEFRIEND_DESC_A, Name.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_EVENT_NAME_DESC + INVALID_EVENT_DATETIME_DESC + VALID_EVENT_DESCRIPTION,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() throws ParseException {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + EVENT_NAME_DESC_A + EVENT_DATETIME_DESC_A
                + EVENT_DESCRIPTION_DESC_A + EVENT_ADDFRIEND_DESC_A + EVENT_REMOVEFRIEND_DESC_B;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withName(VALID_EVENT_NAME)
                .withDateTime(VALID_EVENT_DATETIME)
                .withDescription(VALID_EVENT_DESCRIPTION)
                .withAddFriend(VALID_NAME_AMY)
                .withRemoveFriend(VALID_NAME_BOB).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + EVENT_NAME_DESC_A + EVENT_DATETIME_DESC_A;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withName(VALID_EVENT_NAME)
                .withDateTime(VALID_EVENT_DATETIME).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + EVENT_NAME_DESC_A;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withName(VALID_EVENT_NAME).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + EVENT_DATETIME_DESC_A;
        descriptor = new EditEventDescriptorBuilder().withDateTime(VALID_EVENT_DATETIME).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EVENT_DESCRIPTION_DESC_A;
        descriptor = new EditEventDescriptorBuilder().withDescription(VALID_EVENT_DESCRIPTION).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + EVENT_ADDFRIEND_DESC_A;
        descriptor = new EditEventDescriptorBuilder().withAddFriend(VALID_NAME_AMY).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + EVENT_REMOVEFRIEND_DESC_A;
        descriptor = new EditEventDescriptorBuilder().withRemoveFriend(VALID_NAME_AMY).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + EVENT_NAME_DESC_A + EVENT_DATETIME_DESC_A + EVENT_DESCRIPTION_DESC_A
                 + EVENT_NAME_DESC_A + EVENT_DATETIME_DESC_A + EVENT_DESCRIPTION_DESC_A
                 + EVENT_NAME_DESC_B + EVENT_DATETIME_DESC_B + EVENT_DESCRIPTION_DESC_B
                + EVENT_ADDFRIEND_DESC_B + EVENT_REMOVEFRIEND_DESC_B;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withName(VALID_EVENT_NAME_OTHER)
                .withDateTime(VALID_EVENT_DATETIME_OTHER).withDescription(VALID_EVENT_DESCRIPTION_OTHER)
                .withAddFriend(VALID_NAME_BOB)
                .withRemoveFriend(VALID_NAME_BOB).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_EVENT_NAME_DESC + EVENT_NAME_DESC_A;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withName(VALID_EVENT_NAME).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + EVENT_NAME_DESC_A + INVALID_EVENT_DATETIME_DESC + EVENT_DESCRIPTION_DESC_A
                + EVENT_DATETIME_DESC_A;
        descriptor = new EditEventDescriptorBuilder().withName(VALID_EVENT_NAME).withDateTime(VALID_EVENT_DATETIME)
                .withDescription(VALID_EVENT_DESCRIPTION).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}