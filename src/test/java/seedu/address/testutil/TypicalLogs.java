package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.person.Log;

public class TypicalLogs {

    public static final Log LOG_NO_DESCRIPTION = new Log("met in 2013", null);
    public static final Log SHORT_LOG = new Log("birthday coming up", "maybe get a card");
    public static final Log LONG_LOG = new Log("favourite movies",
            "1. Batman\n"
                    + "2. Superman\n"
                    + "Super super into movies, can probably get some themed gift for birthday.");
    public static final Log SHORT_LOG_DIFFERENT_DESC = new Log("birthday coming up",
            "maybe get a card" + "difference");
    public static final Log LONG_LOG_DIFFERENT_DESC = new Log("favourite movies",
            "1. Batman\n"
                    + "2. Superman\n"
                    + "Super super into movies, can probably get some themed gift for birthday."
                    + "\nDifference");

    private TypicalLogs() {} // prevents instantiation

    public static List<Log> getTypicalLogs() {
        return new ArrayList<>(Arrays.asList(LOG_NO_DESCRIPTION, SHORT_LOG, LONG_LOG));
    }

    public static List<Log> getIdenticalButDifferentTypicalLogs() {
        return new ArrayList<>(Arrays.asList(LOG_NO_DESCRIPTION, SHORT_LOG_DIFFERENT_DESC, LONG_LOG_DIFFERENT_DESC));
    }
}
