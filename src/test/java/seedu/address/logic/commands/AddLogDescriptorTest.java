package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class AddLogDescriptorTest {

    @Test
    public void equals() {
        AddLogCommand.AddLogDescriptor descriptor;
        AddLogCommand.AddLogDescriptor other;
        String title = "some title";
        String differentTitle = "other title";
        String description = "some description";
        String differentDescription = "some other description";

        // same object -> returns true
        descriptor = new AddLogCommand.AddLogDescriptor();
        assertEquals(descriptor, descriptor);

        // same title -> returns true
        descriptor = new AddLogCommand.AddLogDescriptor();
        other = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(title);
        other.setNewTitle(title);
        assertEquals(descriptor, other);

        // same title and description -> returns true
        descriptor = new AddLogCommand.AddLogDescriptor();
        other = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(title);
        descriptor.setNewDescription(description);
        other.setNewTitle(title);
        other.setNewDescription(description);
        assertEquals(descriptor, other);

        // null -> returns false
        assertNotEquals(descriptor, null);

        // different types -> returns false
        assertNotEquals(descriptor, "string object");

        // different title same desc -> returns true
        descriptor = new AddLogCommand.AddLogDescriptor();
        other = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(title);
        descriptor.setNewDescription(description);
        other.setNewTitle(differentTitle);
        other.setNewDescription(description);
        assertNotEquals(descriptor, other);

        // same title different desc -> returns false
        descriptor = new AddLogCommand.AddLogDescriptor();
        other = new AddLogCommand.AddLogDescriptor();
        descriptor.setNewTitle(title);
        descriptor.setNewDescription(description);
        other.setNewTitle(title);
        other.setNewDescription(differentDescription);
        assertNotEquals(descriptor, other);
    }
}
