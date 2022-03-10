package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LogTest {

    @Test
    public void isValidLogTitle() {
        // null
        assertThrows(NullPointerException.class, () -> Log.isValidTitle(null));

        // blank title
        assertFalse(Log.isValidTitle("")); // empty string
        assertFalse(Log.isValidTitle("              ")); // spaces only

        // valid length?
        String longTitle = "";
        for (int i = 0; i < Log.TITLE_LENGTH_CONSTRAINT - 1; i++) {
            longTitle += "c";
        }
        assertTrue(Log.isValidTitle(longTitle)); // exactly as many characters
        assertFalse(Log.isValidTitle(longTitle + "c")); // >limit characters

        // valid
        assertTrue(Log.isValidTitle(" peter jack")); // start with space
        assertTrue(Log.isValidTitle("12345")); // numbers only
        assertTrue(Log.isValidTitle("peter the 2nd")); // alphanumeric characters
        assertTrue(Log.isValidTitle("Capital Tan")); // with capital letters
        assertTrue(Log.isValidTitle("David Roger Jackson Ray Jr 2nd")); // long name
        assertTrue(Log.isValidTitle("`~!@#$%^&*()_-+={[}]|\\:;\"\'<,>.?/")); // random characters
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Log(null, null));
    }

    @Test
    public void constructor_invalidTitle_throwsIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> new Log("", null)); // empty
        assertThrows(IllegalArgumentException.class, () -> new Log("      ", null)); // spaces only

        String longTitle = "";
        for (int i = 0; i < Log.TITLE_LENGTH_CONSTRAINT; i++) {
            longTitle += "c";
        }
        final String tooLongTitle = longTitle;
        assertThrows(IllegalArgumentException.class, () -> new Log(tooLongTitle, null)); // too many characters

    }

    @Test
    public void constructor_noDescription_defaultDescription() {
        String title = "title";
        assertEquals(new Log(title, Log.DEFAULT_NO_DESCRIPTION), new Log(title, null));
    }

    @Test
    public void constructor_haveDescription_givenDescription() {
        String title = "title";
        String description = "description";
        Log testLog = new Log(title, description);
        assertEquals(testLog.getDescription(), description);
        assertEquals(testLog.getTitle(), title);
    }
}
