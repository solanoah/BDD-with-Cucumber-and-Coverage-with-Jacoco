package com.thoughtworks.dev.model;

import com.thoughtworks.dev.enums.SessionType;
import com.thoughtworks.dev.util.Config;
import com.thoughtworks.dev.util.DateHelper;

import java.util.Date;

public class Session {

    private final int startHour;
    private final SessionType sessionType;
    private int totalScheduledTime;

    /**
     * Default constructor
     *
     * @param sessionType
     */
    Session(SessionType sessionType) {

        this.sessionType = sessionType;

        if (sessionType == SessionType.MORNING)
            this.startHour = Config.MORNING_SESSION_START_HOUR;
        else
            this.startHour = Config.AFTERNOON_SESSION_START_HOUR;
    }

    /**
     * @return The start time
     */
    public Date getStartTime() {
        return DateHelper.createTime(this.startHour);
    }

    /**
     * Determine if the allowable time has been used up
     *
     * @return
     */
    public boolean isFull() {
        if (sessionType == SessionType.MORNING) {
            return totalScheduledTime == Config.MORNING_SESSION_DURATION;
        } else {
            return totalScheduledTime == Config.AFTERNOON_SESSION_DURATION;
        }
    }

    /**
     * @return The total duration in minutes
     */
    public int getTotalScheduledTime() {
        return totalScheduledTime;
    }

    /**
     * Update Total scheduled time
     *
     * @param totalScheduledTime
     */
    public void setTotalScheduledTime(int totalScheduledTime) {
        this.totalScheduledTime = totalScheduledTime;
    }

    /**
     * @return Maximum allowable session duration
     */
    public float getMaxDuration() {

        if (sessionType == SessionType.MORNING)
            return Config.MORNING_SESSION_DURATION;
        else
            return Config.AFTERNOON_SESSION_DURATION;
    }

    /**
     * @return The finish/end time of this session
     */
    public Date getEndTime() {

        Date endTime = new Date(getStartTime().getTime());
        endTime.setTime(endTime.getTime() + (totalScheduledTime * 60 * 1000));
        return endTime;
    }

    public SessionType getSessionType() {
        return sessionType;
    }
}

