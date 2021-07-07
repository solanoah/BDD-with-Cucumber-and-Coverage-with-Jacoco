package com.thoughtworks.dev;

import com.thoughtworks.dev.enums.DurationUnit;
import com.thoughtworks.dev.enums.TalkType;
import com.thoughtworks.dev.model.Event;
import com.thoughtworks.dev.model.Track;
import com.thoughtworks.dev.service.PlanningService;
import com.thoughtworks.dev.util.DateHelper;
import cucumber.api.Format;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class PlannerTestSteps {

    private PlanningService planningService;
    private int exceptionCount = 0;

    @Before
    public void setup() {
        planningService = new PlanningService();
    }

    /**
     * This method will process all the input, create talk object and validate the provided duration
     *
     * @param title    Talk tile
     * @param duration Duration
     * @param unit     Unit in minute/lightning
     * @throws IllegalArgumentException Will be raised if duration is negative or more than 240
     */
    @Given("^'(.+)' (\\d+)(.+)$")
    public void talk_Title_With_Duration(String title, int duration, String unit) throws IllegalArgumentException {

        try {
            planningService.processInputLine(title + ' ' + duration + unit);
        } catch (IllegalArgumentException e) {
            exceptionCount++;
        }
    }

    /**
     * This method will process all the input, create talk object (Talk with no duration)
     *
     * @param line detail of the talk
     * @throws Throwable
     */
    @Given("^'(.+)'$")
    public void talk_Title_With_NoDuration(String line) throws Throwable {

        planningService.processInputLine(line);
    }

    /**
     * This method will process all the input and raise an exception for Talk with negative duration)
     *
     * @param title    Talk tile
     * @param duration Duration
     */
    @Given("^'(.+)' -(\\d+)min$")
    public void talk_Title_With_Negative_Duration(String title, int duration) {
        try {
            planningService.processInputLine(title + ' ' + -duration + DurationUnit.MINUTES);
        } catch (IllegalArgumentException e) {
            exceptionCount++;
        }
    }

    /**
     * THis method does the scheduling and assert that expected tracks, talk and duration have been done correctly
     *
     * @param expectedTracks Expected number of tracks
     * @param talkCount      Total talks created
     * @param timedTalk      Total number of timed talks
     * @param notTimedTalk   Total number of NotTimed talks
     * @throws Throwable
     */
    @When("^Schedule (\\d+) Track for proposed total (\\d+) talks, (\\d+) timed talks, (\\d+) untimed talk$")
    public void schedule_Track_for_proposed_total_talks(int expectedTracks, long talkCount, long timedTalk, long notTimedTalk) throws Throwable {

        planningService.scheduleConference();

        assertThat("The talks created should be " + talkCount, planningService.getTalks().stream().count(), is(equalTo(talkCount)));
        assertThat("The timed talks created should be " + timedTalk, planningService.getTalks().stream().filter(talk -> talk.getTalkType() == TalkType.TIMED).count(), is(equalTo(timedTalk)));
        assertThat("The not timed talks created should be " + notTimedTalk, planningService.getTalks().stream().filter(talk -> talk.getTalkType() == TalkType.NOT_TIMED).count(), is(equalTo(notTimedTalk)));
        assertThat("The tracks created should be " + expectedTracks, planningService.getTracks().size(), is(equalTo(expectedTracks)));
    }

    /**
     * This method assert that the total scheduled talk time for a given track does not exceed morning limit
     *
     * @param trackNumber  The track number
     * @param expectedTime The expected Allocated Time
     * @throws Throwable
     */
    @Then("^Track (\\d+) should have (\\d+)mins for morning session$")
    public void track_should_have_mins_for_morning_session(int trackNumber, int expectedTime) throws Throwable {
        Track track = planningService.getTracks().get(trackNumber - 1);
        assertThat("Tha track Total Scheduled morning Time for track should be " + expectedTime, track.getSessions()[0].getTotalScheduledTime(), is(equalTo(expectedTime)));
        assertThat("The track name should be equal", track.toString(), is(equalTo("Track " + trackNumber)));
    }

    /**
     * This method assert that the total scheduled talk time for a given track does not exceed afternoon limit
     *
     * @param trackNumber  The track number
     * @param expectedTime The expected Allocated Time
     * @throws Throwable
     */
    @Then("^Track (\\d+) should have (\\d+)mins for afternoon session$")
    public void track_should_have_mins_for_afternoon_session(int trackNumber, int expectedTime) throws Throwable {
        Track track = planningService.getTracks().get(trackNumber - 1);
        assertThat("Tha track Total Scheduled afternoon Time for track should be " + expectedTime, track.getSessions()[1].getTotalScheduledTime(), is(equalTo(expectedTime)));
        assertThat(track.toString(), is(equalTo("Track " + trackNumber)));
    }

    /**
     * This method assert that the total scheduled talk time for a given track should be less than afternoon limit
     *
     * @param trackNumber  The track number
     * @param expectedTime The expected Allocated Time
     * @throws Throwable
     */
    @Then("^Track (\\d+) should less than (\\d+)mins for afternoon session$")
    public void track_should_less_than_mins_for_afternoon_session(int trackNumber, int expectedTime) throws Throwable {
        Track track = planningService.getTracks().get(trackNumber - 1);
        assertThat("Tha track Total Scheduled afternoon Time for track should be less than " + expectedTime, track.getSessions()[1].getTotalScheduledTime(), lessThan(expectedTime));
    }

    /**
     * Method to check that not-time talks should be added to a specific track
     * e.g. Rails_for_Python_Developers_lightning
     *
     * @param trackNumber Track number
     * @param talkName    talk title
     * @throws Throwable
     */
    @Then("^Track (\\d+) should have '(.+)' talk$")
    public void track_should_have_not_timed_talk(int trackNumber, String talkName) throws Throwable {
        Track track = planningService.getTracks().get(trackNumber - 1);
        Optional<Event> talk = track.getEvents().stream().filter(t -> Objects.equals(t.getTitle(), talkName)).findAny();
        assertThat(talkName + " should be part of track " + trackNumber, talk.isPresent(), is(equalTo(true)));
    }

    /**
     * Method to test that events (lunch/Network) are created and start at the right time
     *
     * @param trackNumber Track number
     * @param eventName   Event name - Lunch/Network
     * @param date        Event start time
     * @throws Throwable
     */
    @Then("^Track (\\d+) (.+) should start at (.*)$")
    public void track_should_have_NetworkingAndLunch(int trackNumber, String eventName, @Format("h:mma") Date date) throws Throwable {

        Track track = planningService.getTracks().get(trackNumber - 1);
        Optional<Event> talk = track.getEvents().stream().filter(t -> Objects.equals(t.getTitle(), eventName)).findAny();

        assertThat(eventName + " should be part of track " + trackNumber, talk.isPresent(), is(equalTo(true)));
        assertThat(eventName + " should start at " + DateHelper.DATE_FORMAT.format(date), DateHelper.DATE_FORMAT.format(talk.get().getStartTime()), is(equalTo(DateHelper.DATE_FORMAT.format(date))));
    }

    /**
     * Method to test that timed talks are created and start at the right time
     *
     * @param trackNumber Track number
     * @param date        talk start time
     * @param talkTitle   talk title
     * @param minute      Duration
     * @throws Throwable
     */
    @And("^Track (\\d+) : (.*) '(.+)' (\\d+)min$")
    public void track_Scheduled_Start_Timed(int trackNumber, @Format("h:mma") Date date, String talkTitle, int minute) throws Throwable {

        Track track = planningService.getTracks().get(trackNumber - 1);
        Optional<Event> talk = track.getEvents().stream().filter(t -> Objects.equals(t.getTitle(), talkTitle)).findAny();

        assertThat(talkTitle + " should be part of track " + trackNumber, talk.isPresent(), is(equalTo(true)));
        assertThat(talkTitle + " should start at " + DateHelper.DATE_FORMAT.format(date), DateHelper.DATE_FORMAT.format(talk.get().getStartTime()), is(equalTo(DateHelper.DATE_FORMAT.format(date))));
        assertThat(talkTitle + " duration should be " + minute, talk.get().getDuration(), is(equalTo(minute)));
    }

    /**
     * Method to test that not timed talks are created and start at the right time
     *
     * @param trackNumber Track number
     * @param date        talk start time
     * @param talkTitle   talk title
     * @throws Throwable
     */
    @And("^Track (\\d+) : (.*) '(.+)'$")
    public void track_Scheduled_Start_NotTimed(int trackNumber, @Format("h:mma") Date date, String talkTitle) throws Throwable {

        Track track = planningService.getTracks().get(trackNumber - 1);
        Optional<Event> talk = track.getEvents().stream().filter(t -> Objects.equals(t.getTitle(), talkTitle)).findAny();

        assertThat(talkTitle + " should be part of track " + trackNumber, talk.isPresent(), is(equalTo(true)));
        assertThat(talkTitle + " should start at " + DateHelper.DATE_FORMAT.format(date), DateHelper.DATE_FORMAT.format(talk.get().getStartTime()), is(equalTo(DateHelper.DATE_FORMAT.format(date))));
    }

    /**
     * Ascertain that the no tracks has been created when exception is raised
     *
     * @param numberOfCreatedTrack Number of created tracks
     */
    @Then("^(\\d+) Track is created$")
    public void raiseException1(Integer numberOfCreatedTrack) {
        assertThat("The total number of tracks in exception should be " + numberOfCreatedTrack, planningService.getTracks().size(), is(equalTo(numberOfCreatedTrack)));
    }

    /**
     * Check the total number of exception created
     *
     * @param numberOfException Number of exceptions
     */
    @Then("^(\\d+) Illegal Argument Exception is raised$")
    public void raiseException2(Integer numberOfException) {
        assertThat("The total number of exceptions should be " + numberOfException, exceptionCount, is(equalTo(numberOfException)));
    }
}
