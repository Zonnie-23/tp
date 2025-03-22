package seedu.address.model.jobapplication;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Represents a Job Application associated to a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class JobApplication {
    private final Person person;

    private final JobTitle jobTitle;
    private final Schedule schedule;
    private final Label label;
    private final Remark remark;

    /**
     * Every field must be present and not null.
     */
    public JobApplication(JobTitle jobTitle, Schedule schedule, Label label, Remark remark) {
        requireAllNonNull(jobTitle, schedule, label, remark);
        this.person = null;
        this.jobTitle = jobTitle;
        this.schedule = schedule;
        this.label = label;
        this.remark = remark;
    }

    /**
     * Every field must be present and not null.
     */
    public JobApplication(Person person, JobTitle jobTitle, Schedule schedule, Label label, Remark remark) {
        requireAllNonNull(person, jobTitle, schedule, label, remark);
        this.person = person;
        this.jobTitle = jobTitle;
        this.schedule = schedule;
        this.label = label;
        this.remark = remark;

        person.addJobApplication(this);
    }

    public Person getPerson() {
        return person;
    }

    public Label getLabel() {
        return label;
    }

    public JobTitle getJobTitle() {
        return jobTitle;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameJobApplication(JobApplication otherJobApplication) {
        if (otherJobApplication == this) {
            return true;
        }

        // Remark is omitted due to arbitrary nature of address input
        return otherJobApplication != null
                && otherJobApplication.getPerson().isSamePerson(getPerson())
                && otherJobApplication.getLabel().equals(getLabel())
                && otherJobApplication.getJobTitle().equals(getJobTitle())
                && otherJobApplication.getSchedule().equals(getSchedule());
    }

    /**
     * Returns true if both applications are for the same person and have same data fields.
     * This defines a stronger notion of equality between two applications.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof JobApplication)) {
            return false;
        }

        JobApplication otherApplication = (JobApplication) other;
        return person.equals(otherApplication.person)
                && label.equals(otherApplication.label)
                && jobTitle.equals(otherApplication.jobTitle)
                && schedule.equals(otherApplication.schedule)
                && remark.equals(otherApplication.remark);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(jobTitle, schedule, label, remark);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("applied job title", jobTitle)
                .add("interview date", schedule)
                .add("label", label)
                .add("remark", remark)
                .toString();
    }
}
