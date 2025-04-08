package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.JobRole;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniqueJobRoleList;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueJobRoleList jobRoles;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        jobRoles = new UniqueJobRoleList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the person list with {@code jobRoles}.
     * {@code jobRoles} must not contain duplicate jobRoles.
     */
    public void setJobRoles(List<JobRole> jobRoles) {
        this.jobRoles.setJobRoles(jobRoles);
    }
    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setJobRoles(newData.getJobRoleList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    // JobRole level operations

    /**
     * Returns true if a jobRole with the same value as {@code jobRole} exists in the address book.
     */
    public boolean hasJobRole(JobRole jobRole) {
        requireNonNull(jobRole);
        return jobRoles.contains(jobRole);
    }

    /**
     * Returns true if a jobRole with the same value as all jobRole in {@code jobRoles} exists in the address book.
     */
    public boolean hasJobRoles(Set<JobRole> jobRoles) {
        requireNonNull(jobRoles);
        return this.jobRoles.containsAll(jobRoles);
    }

    /**
     * Adds a jobRole to the address book.
     * The jobRole must not already exist in the address book.
     */
    public void addJobRole(JobRole j) {
        jobRoles.add(j);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeJobRole(JobRole key) {
        jobRoles.remove(key);
    }

    /**
     * Returns a mapping of each job role to applicants.
     */
    public Map<JobRole, Long> getJobApplicantStatistics() {
        return getPersonList().stream()
                .flatMap(person -> person.getJobRoles().stream())
                .collect(Collectors.groupingBy(jobRole -> jobRole, Collectors.counting()));
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<JobRole> getJobRoleList() {
        return jobRoles.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons)
                && jobRoles.equals(otherAddressBook.jobRoles);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
