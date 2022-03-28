package seedu.address.model.person.insights;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.Model;
import seedu.address.model.person.Person;


/**
 * This class encapsulates insights of a person.
 */
public class PersonInsight implements Comparable<PersonInsight> {

    // Data fields
    private final Person person;
    private final NumLogsInsight numberOfLogs;
    private final NumEventsInsight numberOfEvents;
    private final MostRecentEventInsight lastEvent;

    public PersonInsight(Person person, Model model) {
        requireAllNonNull(person, model);
        this.person = person;
        this.numberOfEvents = NumEventsInsight.of(person, model);
        this.numberOfLogs = NumLogsInsight.of(person, model);
        this.lastEvent = MostRecentEventInsight.of(person, model);

    }

    public String getNumLogsInsightAsString() {
        return this.numberOfLogs.getAsString();
    }

    public String getNumEventsInsightAsString() {
        return this.numberOfEvents.getAsString();
    }

    public String getLastEventInsightAsString() {
        return this.lastEvent.getAsString();
    }

    public Person getPerson() {
        return this.person;
    }

    @Override
    public int compareTo(PersonInsight other) {
        return this.numberOfEvents.compareTo(other.numberOfEvents)
                + this.numberOfLogs.compareTo(other.numberOfLogs)
                + this.lastEvent.compareTo(other.lastEvent);
    }

}
