package com.thoughtworks.dev.service;

import com.thoughtworks.dev.enums.DurationUnit;
import com.thoughtworks.dev.enums.TalkType;
import com.thoughtworks.dev.model.Event;
import com.thoughtworks.dev.model.Talk;
import com.thoughtworks.dev.model.Track;
import com.thoughtworks.dev.util.Config;

import java.util.*;
import java.util.regex.Matcher;

import static com.thoughtworks.dev.util.Config.COMPARATOR_DURATION;

public class PlanningService {

    private final List<Talk> talks = new LinkedList<>();
    private final List<Track> tracks = new ArrayList<>(5);

    /**
     * Create event
     *
     * @param title     Event title
     * @param startTime Start time
     * @return
     */
    private static Event createEvent(String title, Date startTime) {
        Event event = new Event(title);
        event.setStartTime(startTime);
        return event;
    }

    /**
     * @return All tracks that has been generated
     */
    public List<Track> getTracks() {
        return this.tracks;
    }

    /**
     * @return All proposed talks
     */
    public List<Talk> getTalks() {
        return this.talks;
    }

    /**
     * Add proposed talk and validate that it's not negative and duration can not exceed highest session
     *
     * @param talk
     */
    public void addTalk(Talk talk) throws IllegalArgumentException {

        if (talk.getDuration() > Config.MAX_SESSION_DURATION || Math.signum(talk.getDuration()) == -1) {
            throw new IllegalArgumentException();
        }

        this.talks.add(talk);
    }

    /**
     * Create required tracks and lunch/Netwroking events
     */
    private void createRequiredTrack() {

        // Find the total estimated track.
        boolean hasNotTimedTalks = this.talks.stream().anyMatch(t -> t.getTalkType() == TalkType.NOT_TIMED);
        float totalProposedTime = this.talks.stream().map(Talk::getDuration).reduce(hasNotTimedTalks ? 1 : 0, Integer::sum);

        int requiredTrack = (int) Math.ceil(totalProposedTime / Config.MAX_TRACK_DURATION);

        for (int trackNumber = 0; trackNumber < requiredTrack; trackNumber++) {

            Track track = new Track(trackNumber + 1);
            track.getEvents().add(createEvent("Lunch", track.getLunchStartTime()));
            track.getEvents().add(createEvent("Networking Event", track.getNetworkEventStartTime()));
            tracks.add(track);
        }
    }

    /**
     * Schedule conference, create tracks and allocate talks
     */
    public void scheduleConference() {

        createRequiredTrack();

        scheduleTalkWithDuration();

        scheduleTalkWithNoDuration();
    }

    /**
     * Schedule all talks with no duration - talk type (NotTimed)
     * Also updates the Networking event start time
     */
    private void scheduleTalkWithNoDuration() {

        // check if there is any "not time" talks
        boolean anyWithoutDuration = this.talks.stream().anyMatch(t -> t.isAssigned() && t.getTalkType() == TalkType.NOT_TIMED);

        // Look for tracks that are not yet full
        this.tracks.stream().filter((Track track) -> !track.isFull()).forEach(currentTrack -> {

            Date startTime;

            // get start time depending on session
            if (currentTrack.getSessions()[0].isFull()) {
                startTime = currentTrack.getSessions()[1].getEndTime();
            } else {
                startTime = currentTrack.getSessions()[0].getEndTime();
            }

            // if there is any not-timed talk, assign those and the networking event start time remains as default of 5pm
            if (anyWithoutDuration) {
                this.talks.stream().filter(t -> t.isAssigned() && t.getTalkType() == TalkType.NOT_TIMED).forEach(currentTalk -> {
                    currentTalk.setStartTime(startTime);
                    currentTalk.setAssigned();
                    currentTrack.getEvents().add(currentTalk);
                });
            } else {
                // Update the networking event start time if no not timed talk
                currentTrack.updateNetworkEventStartTime(startTime);
            }
        });
    }

    /**
     * Schedule all talks with duration - talk type (Timed)
     */
    private void scheduleTalkWithDuration() {

        this.tracks.stream().filter((Track track) -> !track.isFull()).forEach(currentTrack -> {

            // add talks into sessions - morning/afternoon
            Arrays.stream(currentTrack.getSessions()).forEach(session -> {

                // Assign all timed talks
                talks.stream().filter(t -> t.isAssigned() && t.getTalkType() == TalkType.TIMED).sorted(COMPARATOR_DURATION).forEach(currentTalk -> {

                    int talkTime = currentTalk.getDuration();
                    int pos = (int) (session.getMaxDuration() - talkTime - session.getTotalScheduledTime());

                    Talk lowest = talks.parallelStream().filter(t -> t.isAssigned() && t.getTalkType() == TalkType.TIMED)
                            .min((t1, t2) -> Integer.compare(t1.getDuration(), t2.getDuration())).get();

                    long remainingTalks = talks.parallelStream().filter(t -> t.isAssigned() && t.getTalkType() == TalkType.TIMED).count();

                    // ensure that any time left in a session can accommodate talk with smallest duration
                    if (pos >= lowest.getDuration() || pos == 0 || (remainingTalks == 1 && pos < lowest.getDuration())) {

                        Date talkStartTime = new Date(session.getStartTime().getTime());
                        talkStartTime.setTime(talkStartTime.getTime() + (session.getTotalScheduledTime() * 60 * 1000));

                        // update talk details and signify if it's been assigned
                        currentTalk.setStartTime(talkStartTime);
                        currentTalk.setAssigned();
                        currentTalk.setSessionType(session.getSessionType());

                        // update total scheduled time in a given session
                        session.setTotalScheduledTime(session.getTotalScheduledTime() + talkTime);
                        currentTrack.getEvents().add(currentTalk);
                    }
                });
            });
        });
    }

    /**
     * @param line propose talk detail
     */
    public void processInputLine(String line){

        if (line.length() == 0) {
            return;
        }

        Matcher match = Config.LINE_PATTERN.matcher(line);

        if (match.find()) {
            String title = match.group(1);
            String durationInString = match.group(2);
            DurationUnit durationUnit = match.group(3).equalsIgnoreCase("min") ? DurationUnit.MINUTES : DurationUnit.LIGHTNING;
            int duration = Integer.parseInt(durationInString);
            addTalk(new Talk(title, duration, durationUnit));
        } else {
            addTalk(new Talk(line));
        }
    }
}
