package com.thoughtworks.dev.model;

import com.thoughtworks.dev.enums.SessionType;
import com.thoughtworks.dev.util.Config;
import com.thoughtworks.dev.util.DateHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class Track {

    private final int id;
    private final Session[] sessions;
    private final ArrayList<Event> events = new ArrayList<>(20);
    private final Date lunchStartTime;

    private Date networkEventStartTime;

    /**
     *
     * @param id
     */
    public Track(int id) {
        this.id = id;
        this.sessions = new Session[]{ new Session(SessionType.MORNING), new Session(SessionType.AFTERNOON)};
        this.networkEventStartTime = DateHelper.createTime(Config.DEFAULT_NETWORK_START_HOUR);
        this.lunchStartTime = DateHelper.createTime(Config.DEFAULT_LUNCH_START_HOUR);
    }

    /**
     * @return
     */
    public boolean isFull()
    {
        return !Arrays.stream(this.sessions).filter(session -> !session.isFull()).findAny().isPresent();
    }

    public void updateNetworkEventStartTime(Date networkStartTime) {

        Date early = DateHelper.createTime(Config.EARLY_NETWORK_START_HOUR);

        if (networkStartTime.after(early)) {
            this.networkEventStartTime = networkStartTime;
        }else{
            this.networkEventStartTime = early;
        }

        events.stream().filter(t -> Objects.equals(t.getTitle(), "Networking Event"))
                .forEach(e -> e.setStartTime(this.networkEventStartTime));
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public Session[] getSessions() {
        return sessions;
    }

    public Date getNetworkEventStartTime() {
        return networkEventStartTime;
    }

    public Date getLunchStartTime() {
        return lunchStartTime;
    }

    @Override
    public String toString() {
        return "Track " + this.id;
    }

}
