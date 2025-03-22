package seedu.address.model.jobapplication;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.jobapplication.exceptions.DuplicateJobApplicationException;
import seedu.address.model.jobapplication.exceptions.JobApplicationNotFoundException;

/**
 * A list of job applications that enforces uniqueness between its elements and does not allow nulls.
 * An application is considered unique by comparing using {@code JobApplication#isSameJobApplication(JobApplication)}.
 * As such, adding and updating of job applications uses JobApplication#isSameJobApplication(JobApplication) for
 * equality so as to ensure that the application being added or updated is unique in terms of identity in the
 * UniqueJobApplicationList. However, the removal of a job application uses JobApplication#equals(Object) so
 * as to ensure that the application with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see JobApplication#isSameJobApplication(JobApplication)
 */
public class UniqueJobApplicationList implements Iterable<JobApplication> {

    private final ObservableList<JobApplication> internalList = FXCollections.observableArrayList();
    private final ObservableList<JobApplication> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent job applications as the given argument.
     */
    public boolean contains(JobApplication toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameJobApplication);
    }

    /**
     * Adds a job applications to the list.
     * The job applications must not already exist in the list.
     */
    public void add(JobApplication toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateJobApplicationException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the job application {@code target} in the list with {@code editedJobApplication}.
     * {@code target} must exist in the list.
     * The application identity of {@code editedJobApplication} must not be the same as
     * another existing job application in the list.
     */
    public void setJobApplication(JobApplication target, JobApplication editedJobApplication) {
        requireAllNonNull(target, editedJobApplication);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new JobApplicationNotFoundException();
        }

        if (!target.isSameJobApplication(editedJobApplication) && contains(editedJobApplication)) {
            throw new DuplicateJobApplicationException();
        }

        internalList.set(index, editedJobApplication);
    }

    /**
     * Removes the equivalent job application from the list.
     * The job application must exist in the list.
     */
    public void remove(JobApplication toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new JobApplicationNotFoundException();
        }
    }

    public void setJobApplications(UniqueJobApplicationList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code jobApplications}.
     * {@code jobApplications} must not contain duplicate jobApplications.
     */
    public void setJobApplications(List<JobApplication> jobApplications) {
        requireAllNonNull(jobApplications);
        if (!jobApplicationsAreUnique(jobApplications)) {
            throw new DuplicateJobApplicationException();
        }

        internalList.setAll(jobApplications);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<JobApplication> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<JobApplication> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueJobApplicationList)) {
            return false;
        }

        UniqueJobApplicationList otherUniqueJobApplicationList = (UniqueJobApplicationList) other;
        return internalList.equals(otherUniqueJobApplicationList.internalList);
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
     * Returns true if {@code jobApplications} contains only unique persons.
     */
    private boolean jobApplicationsAreUnique(List<JobApplication> jobApplications) {
        for (int i = 0; i < jobApplications.size() - 1; i++) {
            for (int j = i + 1; j < jobApplications.size(); j++) {
                if (jobApplications.get(i).isSameJobApplication(jobApplications.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
