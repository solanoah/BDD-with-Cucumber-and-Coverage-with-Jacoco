package com.thoughtworks.dev.model;

import com.thoughtworks.dev.enums.DurationUnit;

import java.util.Date;

public class Event {

    private final String title;

    private Date startTime;
    private int duration = 0;
    private DurationUnit durationUnit;

    /**
     * @param title
     */
    public Event(String title) {
        this.title = title;
    }

    /**
     * @param title Title
     * @param duration Duration
     * @param durationUnit Minute/Lightning
     */
    Event(String title, int duration, DurationUnit durationUnit) {
        this.title = title;
        this.duration = duration;
        this.durationUnit = durationUnit;
    }

    /**
     * @return Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return The start time
     */
    public Date getStartTime(){
        return this.startTime;
    }

    /**
     * @param startTime sets the start time
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return Duration in minute
     */
    public int getDuration() {
        return this.durationUnit == DurationUnit.MINUTES ? this.duration : 5 *  this.duration;
    }

    /**
     * @return Minute/Lightning
     */
    DurationUnit getDurationUnit() {
        return durationUnit;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return title;
    }
}
