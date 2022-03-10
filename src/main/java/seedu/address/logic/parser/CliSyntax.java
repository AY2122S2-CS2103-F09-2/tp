package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_TITLE = new Prefix("ttl/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/"); // TODO: 7/3/2022  modify documentation accordingly
    public static final Prefix PREFIX_LOG_INDEX = new Prefix("id/");
    public static final Prefix FLAG_ALL = new Prefix("-a");

    public static final Prefix PREFIX_DATETIME = new Prefix("dt/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_FRIEND_NAME = new Prefix("f/");
}
