package seedu.address.storage;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.jobapplication.JobApplication;
import seedu.address.model.jobapplication.JobTitle;
import seedu.address.model.jobapplication.Label;
import seedu.address.model.jobapplication.Remark;
import seedu.address.model.jobapplication.Schedule;

/**
 * Jackson-friendly version of {@link JobApplication}.
 */
class JsonAdaptedJobApplication {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Job application's %s field is missing!";

    private final String jobTitle;
    private final String schedule;
    private final String remark;
    private final String label;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedJobApplication(@JsonProperty("jobTitle") String jobTitle,
                                     @JsonProperty("schedule") String schedule, @JsonProperty("label") String label,
                                     @JsonProperty("remark") String remark,
                                     @JsonProperty("tags") List<JsonAdaptedTag> tags) {

        this.label = label;
        this.schedule = schedule;
        this.jobTitle = jobTitle;
        this.remark = remark;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedJobApplication(JobApplication source) {
        schedule = source.getSchedule().value;
        jobTitle = source.getJobTitle().value;
        remark = source.getRemark().value;
        label = source.getLabel().value;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public JobApplication toModelType() throws IllegalValueException {
        if (label == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Label.class.getSimpleName()));
        }
        if (!Label.isValidLabel(label)) {
            throw new IllegalValueException(Label.MESSAGE_CONSTRAINTS);
        }
        Label modelLabel = new Label(label);

        if (jobTitle == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    JobTitle.class.getSimpleName()));
        }
        if (!JobTitle.isValidJobTitle(jobTitle)) {
            throw new IllegalValueException(JobTitle.MESSAGE_CONSTRAINTS);
        }
        final JobTitle modelJobTitle = new JobTitle(jobTitle);

        if (schedule == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Schedule.class.getSimpleName()));
        }
        final Schedule modelSchedule = new Schedule(schedule);

        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        final Remark modelRemark = new Remark(remark);

        return new JobApplication(modelJobTitle, modelSchedule, modelLabel, modelRemark);
    }

}
