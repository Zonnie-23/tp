package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.JobRole;
import seedu.address.model.person.Person;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_JOBROLE = "List of job roles contains duplicate job role(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedJobRole> jobRoles = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("jobRoles") List<JsonAdaptedJobRole> jobRoles) {
        this.persons.addAll(persons);
        this.jobRoles.addAll(jobRoles);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        jobRoles.addAll(source.getJobRoleList().stream().map(JsonAdaptedJobRole::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        addressBook.setJobRoles(new ArrayList<>());
        for (JsonAdaptedJobRole jsonAdaptedJobRoles : jobRoles) {
            JobRole jobRole = jsonAdaptedJobRoles.toModelType();
            if (addressBook.hasJobRole(jobRole)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_JOBROLE);
            }
            addressBook.addJobRole(jobRole);
        }
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            if (!addressBook.hasJobRoles(person.getJobRoles())) {
                person.resetJobRoles();
            }
            addressBook.addPerson(person);
        }
        return addressBook;
    }

}
