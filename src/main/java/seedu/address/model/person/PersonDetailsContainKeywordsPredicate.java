package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.jobapplication.JobApplicationDetailsContainKeywordCheck;

/**
 * Tests that a {@code Person}'s details (i.e., any of its attributes) matches any of the keywords given.
 */
public class PersonDetailsContainKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PersonDetailsContainKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> personDetailsContainKeyword(person, keyword));
    }

    // Note: currently I implement it to check if the any of the job application attach to this person has a match
    private boolean personDetailsContainKeyword(Person person, String keyword) {

        return StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword)
                || StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword)
                || StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword)
                || StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword)
                || person.getJobApplcations().stream()
                    .anyMatch(ja -> JobApplicationDetailsContainKeywordCheck
                                        .jobApplicationDetailsContainKeyword(ja, keyword))
                || person.getTags().stream()
                    .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonDetailsContainKeywordsPredicate)) {
            return false;
        }

        PersonDetailsContainKeywordsPredicate otherPersonDetailsContainKeywordsPredicate =
                (PersonDetailsContainKeywordsPredicate) other;
        return keywords.equals(otherPersonDetailsContainKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
