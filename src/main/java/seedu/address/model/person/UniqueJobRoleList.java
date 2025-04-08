package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicateJobRoleException;
import seedu.address.model.person.exceptions.JobRoleNotFoundException;

/**
 * A list of job roles that enforces uniqueness between its elements and does not allow nulls.
 * A job role is considered unique by comparing using {@code JobRole#isSame(JobRole)}.
 * Supports a minimal set of list operations.
 */
public class UniqueJobRoleList implements Iterable<JobRole> {

    private final ObservableList<JobRole> internalList = FXCollections.observableArrayList();
    private final ObservableList<JobRole> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Default constructor for this list. The following jobRoles are by default added into this list.
     */
    public UniqueJobRoleList() {
        internalList.add(new JobRole("Software Engineer"));
        internalList.add(new JobRole("Data Scientist"));
        internalList.add(new JobRole("Front End Developer"));
        internalList.add(new JobRole("Back End Developer"));
        internalList.add(new JobRole("IT Administrator"));
        internalList.add(new JobRole("UI Designer"));
        internalList.add(new JobRole("Product Manager"));
        internalList.add(new JobRole("DevOps Engineer"));
        internalList.add(new JobRole("QA Engineer"));
    }

    /**
     * Returns true if the list contains an equivalent job role as the given argument.
     */
    public boolean contains(JobRole toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Returns true if the list contains an equivalent job role for all JobRoles in given argument.
     */
    public boolean containsAll(Set<JobRole> toCheck) {
        requireNonNull(toCheck);
        return toCheck.stream().allMatch(this::contains);
    }

    /**
     * Adds a job role to the list.
     * The job role must not already exist in the list.
     */
    public void add(JobRole toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateJobRoleException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent job role from the list.
     * The job role must exist in the list.
     */
    public void remove(JobRole toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new JobRoleNotFoundException();
        }
    }


    public void setJobRoles(UniqueJobRoleList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code jobRoles}.
     * {@code jobRoles} must not contain duplicate jobRoles.
     */
    public void setJobRoles(List<JobRole> jobRoles) {
        requireAllNonNull(jobRoles);
        if (!jobRolesAreUnique(jobRoles)) {
            throw new DuplicateJobRoleException();
        }

        internalList.setAll(jobRoles);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<JobRole> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<JobRole> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UniqueJobRoleList)) {
            return false;
        }
        UniqueJobRoleList otherList = (UniqueJobRoleList) other;
        return internalList.equals(otherList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code jobRoles} contains only unique job roles.
     */
    private boolean jobRolesAreUnique(List<JobRole> jobRoles) {
        for (int i = 0; i < jobRoles.size() - 1; i++) {
            for (int j = i + 1; j < jobRoles.size(); j++) {
                if (jobRoles.get(i).equals(jobRoles.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
