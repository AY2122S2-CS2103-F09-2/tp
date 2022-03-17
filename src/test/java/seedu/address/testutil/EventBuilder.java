package seedu.address.testutil;

import static seedu.address.testutil.TypicalEvents.ALEX_NAME;
import static seedu.address.testutil.TypicalEvents.AMY_NAME;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.common.Description;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.person.FriendName;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = "Default Event";
    public static final String DEFAULT_DATE_TIME = "12-5-2022 1500";
    public static final String DEFAULT_DESCRIPTION = "Default Description";
    public static final List<FriendName> DEFAULT_FRIENDLIST =
            List.of(new FriendName(AMY_NAME), new FriendName(ALEX_NAME));

    private EventName name;
    private DateTime dateTime;
    private Description description;
    private Set<FriendName> friendNames = new HashSet<>();

    /**
     * Creates a {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        name = new EventName(DEFAULT_NAME);
        dateTime = new DateTime(DEFAULT_DATE_TIME);
        description = new Description(DEFAULT_DESCRIPTION);
        friendNames.addAll(DEFAULT_FRIENDLIST);
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        dateTime = eventToCopy.getDateTime();
        description = eventToCopy.getDescription();
        friendNames.addAll(eventToCopy.getFriendNames());
    }

    /**
     * Sets the {@code Name} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = new EventName(name);
        return this;
    }

    /**
     * Parses the {@code names} into a {@code Set<Name>} and set it to the {@code Event} that we are building.
     */
    public EventBuilder withNames(String ... names) {
        this.friendNames = SampleDataUtil.getNameSet(names);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public EventBuilder withDateTime(String dateTime) {
        this.dateTime = new DateTime(dateTime);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Person} that we are building.
     */
    public EventBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    public Event build() {
        return new Event(name, dateTime, description, friendNames);
    }
}
