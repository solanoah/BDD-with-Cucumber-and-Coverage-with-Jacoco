package com.thoughtworks.dev.model;

import com.thoughtworks.dev.enums.DurationUnit;
import com.thoughtworks.dev.enums.SessionType;
import com.thoughtworks.dev.enums.TalkType;

public class Talk extends Event {

    private final TalkType talkType;
    private boolean assigned;
    private SessionType sessionType;

    /**
     * @param title
     * @param duration
     * @param durationUnit
     */
    public Talk(String title, int duration, DurationUnit durationUnit) {
        super(title, duration, durationUnit);
        this.talkType = TalkType.TIMED;
    }

    /**
     * @param title
     */
    public Talk(String title) {
        super(title);
        this.talkType = TalkType.NOT_TIMED;
    }

    /**
     * @return Timed or NotTimed
     */
    public TalkType getTalkType() {
        return talkType;
    }

    /**
     *
     */
    public void setAssigned() {
        this.assigned = true;
    }

    /**
     * @return
     */
    public boolean isAssigned() {
        return !assigned;
    }

    /**
     * @return
     */
    @Override
    public String toString() {

        if (this.talkType == TalkType.TIMED) {
            return this.getTitle() + ' ' + this.getDuration() + this.getDurationUnit().toString();
        } else {
            return this.getTitle();
        }
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }
}
