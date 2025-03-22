package seedu.address.model.jobapplication;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code JobApplication}'s details (i.e., any of its attributes) matches any of the keywords given.
 */
public class JobApplicationDetailsContainKeywordCheck {
    /**
     * Meant for the purpose of checking for keyword match of each job application
     * @param jobApplication    The job application to perform keyword match
     * @param keyword           The keyword to check
     * @return
     */
    public static boolean jobApplicationDetailsContainKeyword(JobApplication jobApplication, String keyword) {
        return StringUtil.containsWordIgnoreCase(jobApplication.getJobTitle().value, keyword)
                || StringUtil.containsWordIgnoreCase(jobApplication.getSchedule().value, keyword)
                || StringUtil.containsWordIgnoreCase(jobApplication.getLabel().value, keyword)
                || StringUtil.containsWordIgnoreCase(jobApplication.getRemark().value, keyword);
    }
}
