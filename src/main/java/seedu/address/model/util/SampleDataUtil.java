package seedu.address.model.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.JobRole;
import seedu.address.model.person.Label;
import seedu.address.model.person.Mode;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.schedule.ReadOnlyScheduleBoard;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.ScheduleBoard;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final Remark EMPTY_REMARK = new Remark("");

    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Label("Unreviewed"),
                EMPTY_REMARK, getJobRoleSet("Data Scientist"), getTagSet("Java")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Label("Unreviewed"),
                EMPTY_REMARK, getJobRoleSet("Front End Developer"), getTagSet("Java", "Python")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Label("Unreviewed"),
                EMPTY_REMARK, getJobRoleSet("Back End Developer"), getTagSet("Rust", "Zig")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Label("Unreviewed"),
                EMPTY_REMARK, getJobRoleSet("DevOps Engineer"),
                getTagSet("React", "MongoDB", "Nodejs", "Expressjs")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Label("Unreviewed"),
                EMPTY_REMARK, getJobRoleSet("UI Designer"), getTagSet("CUDA")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Label("Unreviewed"),
                EMPTY_REMARK, getJobRoleSet("QA Engineer"), getTagSet("C", "Rust", "Go"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
            .map(Tag::new)
            .collect(Collectors.toSet());
    }

    /**
     * Returns a jobRole set containing the list of strings given.
     */
    public static Set<JobRole> getJobRoleSet(String... strings) {
        return Arrays.stream(strings)
            .map(JobRole::new)
            .collect(Collectors.toSet());
    }

    public static Schedule[] getSampleSchedules() {
        return new Schedule[]{
            new Schedule(LocalDate.parse("2025-05-13", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalTime.parse("12:00"), LocalTime.parse("15:00"),
                Mode.ONLINE, new Name("Alex Yeoh"), new Email("alexyeoh@example.com")),
            new Schedule(LocalDate.parse("2025-05-14", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalTime.parse("14:00"), LocalTime.parse("16:00"),
                Mode.ONLINE, new Name("Bernice Yu"), new Email("berniceyu@example.com")),
            new Schedule(LocalDate.parse("2025-05-16", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalTime.parse("14:00"), LocalTime.parse("16:00"),
                Mode.ONLINE, new Name("Charlotte Oliveiro"), new Email("charlotte@example.com")),
            new Schedule(LocalDate.parse("2025-05-17", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalTime.parse("14:00"), LocalTime.parse("16:00"),
                Mode.ONLINE, new Name("David Li"), new Email("lidavid@example.com")),
            new Schedule(LocalDate.parse("2025-05-20", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalTime.parse("14:00"), LocalTime.parse("16:00"),
                Mode.ONLINE, new Name("Roy Balakrishnan"), new Email("royb@example.com")),
        };
    }

    public static ReadOnlyScheduleBoard getSampleScheduleBoard() {
        ScheduleBoard sampleSb = new ScheduleBoard();
        for (Schedule sampleSchedule : getSampleSchedules()) {
            sampleSb.addSchedule(sampleSchedule);
        }
        return sampleSb;
    }
}
