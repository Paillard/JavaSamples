package clock_my_pitch.soft;

import java.time.Duration;
import java.time.LocalTime;

/**
 * Created by me on 3/11/16.
 */
public abstract class Speech {
    // The time at which the speech began
    private LocalTime startTime;
    // The time given to make the speech
    private Duration normalTime;
    // The maximum time the speech can overlaps
    private Duration maxTime;
    // The time given for the questions
    private Duration questionTime;
    // The maximum time the questions can overlaps
    private Duration maxQuestionTime;

    protected Speech(Duration normalTime, Duration questionTime, Duration maxTime, Duration maxQuestionTime) {

    }
    // startTime
    // getElapsedTime
}
