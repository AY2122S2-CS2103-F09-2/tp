package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicateLogException;
import seedu.address.model.person.exceptions.LogNotFoundException;

public class UniqueLogListTest {


    @Test
    public void contains_null_throwsNullPointerException() {
        UniqueLogList uniqueLogList = new UniqueLogList();
        assertThrows(NullPointerException.class, () -> uniqueLogList.contains(null));
    }

    @Test
    public void contains_logNotInList_returnsFalse() {
        Log toFind = new Log("title", null);
        UniqueLogList uniqueLogList = new UniqueLogList(); // initially empty, test add separately
        assertFalse(uniqueLogList.contains(toFind));
    }

    @Test
    public void contains_logInList_returnsTrue() {
        Log toAdd = new Log("title", null);
        Log identical = new Log("title", "some description");
        UniqueLogList uniqueLogList = new UniqueLogList();
        uniqueLogList.addLog(toAdd);
        assertTrue(uniqueLogList.contains(identical));
    }

    @Test
    public void containsExactly_null_throwsNullPointerException() {
        UniqueLogList uniqueLogList = new UniqueLogList();
        assertThrows(NullPointerException.class, () -> uniqueLogList.containsExactly(null));
    }

    @Test
    public void containsExactly_logNotInList_returnsFalse() {
        Log toFind = new Log("title", null);
        UniqueLogList uniqueLogList = new UniqueLogList(); // initially empty, test add separately
        assertFalse(uniqueLogList.containsExactly(toFind));
    }

    @Test
    public void containsExactly_logInList_returnsTrue() {
        Log toAdd = new Log("title", "some description");
        Log identical = new Log("title", "some description");
        UniqueLogList uniqueLogList = new UniqueLogList();
        uniqueLogList.addLog(toAdd);
        assertTrue(uniqueLogList.containsExactly(identical));
    }

    @Test
    public void add_nullLog_throwsNullPointerException() {
        UniqueLogList uniqueLogList = new UniqueLogList();
        assertThrows(NullPointerException.class, () -> uniqueLogList.addLog(null));
    }


    @Test
    public void add_duplicateLog_throwsDuplicateLogException() {
        Log toAdd = new Log("title", null);
        Log similarLog = new Log("title", "some description"); // similar == same title
        Log anotherSimilarLog = new Log("title", "some other description");
        UniqueLogList uniqueLogList = new UniqueLogList();
        uniqueLogList.addLog(toAdd);
        assertThrows(DuplicateLogException.class, () -> uniqueLogList.addLog(similarLog));
        assertThrows(DuplicateLogException.class, () -> uniqueLogList.addLog(anotherSimilarLog));
    }

    @Test
    public void setLog_targetLogNotInside_throwsLogNotFoundException() {
        Log target = new Log("target", null);
        Log editedLog = new Log("target", "now added description");
        Log notTargetLog = new Log("not target", null);

        // target not in empty list
        UniqueLogList uniqueLogList = new UniqueLogList(); // initially empty
        assertThrows(LogNotFoundException.class, () -> uniqueLogList.setLog(target, editedLog));

        // target not in list
        uniqueLogList.addLog(notTargetLog); // contains non-target log
        assertThrows(LogNotFoundException.class, () -> uniqueLogList.setLog(target, editedLog));
    }


    @Test
    public void setLog_validEditContains_returnsTrue() {
        // target is inside, and edited log are identical
        Log target = new Log("target", "description");
        Log editedLog = new Log("target", "description");
        assertTrue(target.equals(editedLog)); // identical logs
        UniqueLogList uniqueLogList = new UniqueLogList(); // initially empty
        uniqueLogList.addLog(target);
        assertTrue(uniqueLogList.contains(target));
        uniqueLogList.setLog(target, editedLog);
        assertTrue(uniqueLogList.containsExactly(target)); // no change semantically, since setter sets identical thing

        // target is inside, and edited log same title but different description
        Log actuallyEditedLog = new Log("target", "some different description");
        Log actuallyEditedLogToFind = new Log("target", "some different description");
        uniqueLogList.setLog(target, actuallyEditedLog);
        assertTrue(uniqueLogList.contains(target)); // same title still inside
        assertFalse(uniqueLogList.containsExactly(target)); // semantically different
        assertTrue(uniqueLogList.containsExactly(actuallyEditedLogToFind));
    }

    @Test
    public void removeLog_null_throwsNullPointerException() {
        UniqueLogList uniqueLogList = new UniqueLogList(); // initially empty
        assertThrows(NullPointerException.class, () -> uniqueLogList.removeLog(null));
    }

    @Test
    public void removeLog_logNotInside_throwsLogNotFoundException() {
        // empty log
        Log target = new Log("target", "description");
        UniqueLogList uniqueLogList = new UniqueLogList(); // initially empty
        assertThrows(LogNotFoundException.class, () -> uniqueLogList.removeLog(target));

        // not inside
        Log nonTarget = new Log("not target", "description");
        uniqueLogList.addLog(nonTarget);
        assertTrue(uniqueLogList.contains(nonTarget)); // not empty
        assertThrows(LogNotFoundException.class, () -> uniqueLogList.removeLog(target));

    }

    @Test
    public void removeLog_validContains_returnsFalse() {

        Log target = new Log("not target", "description");
        UniqueLogList uniqueLogList = new UniqueLogList(); // initially empty
        uniqueLogList.addLog(target);

        Log toRemove = new Log("not target", "description"); // exact match to remove
        assertTrue(uniqueLogList.containsExactly(target)); // target is inside
        uniqueLogList.removeLog(toRemove);
        assertFalse(uniqueLogList.contains(target)); // removed successfully
    }
}
