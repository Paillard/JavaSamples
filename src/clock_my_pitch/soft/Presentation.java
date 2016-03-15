package clock_my_pitch.soft;

import java.time.Duration;

public class Presentation extends Speech {

    private static final Duration NORMAL_TIME = Duration.ofMinutes(5L);
    private static final Duration QUESTION_TIME = Duration.ofMinutes(5L);
    private static final Duration MAX_TIME = Duration.ofMinutes(10L);
    private static final Duration MAX_QUESTION_TIME = Duration.ofMinutes(10L);

    public Presentation() {
        this(NORMAL_TIME, QUESTION_TIME, MAX_TIME, MAX_QUESTION_TIME);
    }

    protected Presentation(Duration normalTime, Duration questionTime, Duration maxTime, Duration maxQuestionTime) {
        super(normalTime, questionTime, maxTime, maxQuestionTime);
    }
}
