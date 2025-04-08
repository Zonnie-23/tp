package seedu.address.commons.util;

import java.time.Duration;
import java.time.LocalTime;

/**
 * ScheduleUtil contains methods for manipulating Schedules.
 */
public class ScheduleUtil {
    private static final long MIN_DURATION_MINUTES = 15;
    private static final long MAX_DURATION_MINUTES = 4 * 60;

    /**
     * Checks that {@code startTime} provided is earlier than {@code endTime} provided
     * @return true if startTime is before endTime
     */
    public static boolean checkStartEndDateTime(LocalTime startTime, LocalTime endTime) {
        return startTime.isBefore(endTime);
    }

    /**
     * Validates that the duration between them is at least 15 minutes, and the duration does not exceed 4 hours.
     *
     * @param startTime the start time
     * @param endTime the end time
     * @return true if all constraints are satisfied, false otherwise
     */
    public static boolean isValidDuration(LocalTime startTime, LocalTime endTime) {
        long durationMinutes = Duration.between(startTime, endTime).toMinutes();
        return durationMinutes >= MIN_DURATION_MINUTES
                && durationMinutes <= MAX_DURATION_MINUTES;
    }
}
