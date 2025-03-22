package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.jobapplication.JobTitle;
import seedu.address.model.jobapplication.Label;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.jobapplication.Remark;
import seedu.address.model.jobapplication.Schedule;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_JOBTITLE = "Front End Developer";
    public static final String DEFAULT_SCHEDULE = "10/02/2025 10:00";
    public static final String DEFAULT_REMARK = "Likes to solve leetcode problems.";
    public static final String DEFAULT_LABEL = "Unreviewed";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Schedule schedule;
    private JobTitle jobTitle;
    private Set<Tag> tags;
    private Remark remark;

    private Label label;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        schedule = new Schedule(DEFAULT_SCHEDULE);
        jobTitle = new JobTitle(DEFAULT_JOBTITLE);
        tags = new HashSet<>();
        label = new Label(DEFAULT_LABEL);
        remark = new Remark(DEFAULT_REMARK);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        schedule = personToCopy.getSchedule();
        jobTitle = personToCopy.getJobTitle();
        tags = new HashSet<>(personToCopy.getTags());
        label = personToCopy.getLabel();
        remark = personToCopy.getRemark();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Label} of the {@code Person} that we are building.
     */
    public PersonBuilder withLabel(String label) {
        this.label = new Label(label);
        return this;
    }


    /**
     * Sets the {@code Schedule} of the {@code Schedule} that we are building.
     */
    public PersonBuilder withSchedule(String schedule) {
        this.schedule = new Schedule(schedule);
        return this;
    }

    /**
     * Sets the {@code JobTitle} of the {@code Person} that we are building.
     */
    public PersonBuilder withJobTitle(String jobTitle) {
        this.jobTitle = new JobTitle(jobTitle);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Builds the {@code Person} and returns it
     */
    public Person build() {
        return new Person(name, phone, email, address, jobTitle, schedule, label, remark, tags);
    }

}
