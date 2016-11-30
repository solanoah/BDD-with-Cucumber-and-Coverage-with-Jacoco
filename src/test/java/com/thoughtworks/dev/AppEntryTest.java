package com.thoughtworks.dev;

import com.thoughtworks.dev.service.PlanningService;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeast;

@SuppressWarnings("All")
public class AppEntryTest {

    private final ClassLoader classLoader = getClass().getClassLoader();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @InjectMocks
    PlanningService planningService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }

    @Test
    public void testAppEntryMain() throws IOException {
        exit.expectSystemExit();

        PlanningService planningMock = Mockito.mock(PlanningService.class);

        AppEntry.setPlanner(planningMock);
        File file = new File(classLoader.getResource("TestSample_lightning.txt").getFile());
        AppEntry.main(new String[] { file.getAbsolutePath()});

        Mockito.verify(planningMock, atLeast(19)).processInputLine(any());
        Mockito.verify(planningMock).scheduleConference();
        Mockito.verify(planningMock).getTracks();
    }

    @Test
    public void testAppEntryMain_Actual() throws IOException {

        exit.expectSystemExit();
        AppEntry.setPlanner(planningService);
        File file = new File(classLoader.getResource("TestSample_lightning.txt").getFile());
        AppEntry.main(new String[] { file.getAbsolutePath()});

        assertEquals("Track 1\n" +
                "09:00AM Writing Fast Tests Against Enterprise Rails 60l\n" +
                "10:00AM Communicating Over Distance 60l\n" +
                "11:00AM Rails Magic 60l\n" +
                "12:00PM Lunch\n" +
                "01:00PM Ruby on Rails: Why We Should Move On 60l\n" +
                "02:00PM Ruby on Rails Legacy App Maintenance 60l\n" +
                "03:00PM Overdoing it in Python 45l\n" +
                "03:45PM Ruby Errors from Mismatched Gem Versions 45l\n" +
                "04:30PM Lua for the Masses 30l\n" +
                "05:00PM Networking Event\n" +
                '\n' +
                '\n' +
                "Track 2\n" +
                "09:00AM Common Ruby Errors 45l\n" +
                "09:45AM Accounting-Driven Development 45l\n" +
                "10:30AM Pair Programming vs Noise 45l\n" +
                "11:15AM Clojure Ate Scala (on my project) 45l\n" +
                "12:00PM Lunch\n" +
                "01:00PM Woah 30l\n" +
                "01:30PM Sit Down and Write 30l\n" +
                "02:00PM Programming in the Boondocks of Seattle 30l\n" +
                "02:30PM Ruby vs. Clojure for Back-End Development 30l\n" +
                "03:00PM A World Without HackerNews 30l\n" +
                "03:30PM User Interface CSS in Rails Apps 30l\n" +
                "04:00PM Rails for Python Developers lightning\n" +
                "05:00PM Networking Event\n" +
                '\n' +
                '\n', outContent.toString());
    }

    @Test
    public void testAppEntryMain_NoArg() throws IOException {
        exit.expectSystemExit();
        AppEntry.main(new String[] { });
    }

    @Test(expected = IOException.class)
    public void testAppEntryMain_InvalidFilePath() throws IOException {
        AppEntry.setPlanner(planningService);
        AppEntry.main(new String[] { "Invalid.txt" });
    }

    @Test
    public void testAppEntryMain_IllegalArgument() throws IOException {
        exit.expectSystemExit();
        AppEntry.setPlanner(planningService);
        File file = new File(classLoader.getResource("TestSample_exception.txt").getFile());
        AppEntry.main(new String[] { file.getAbsolutePath()});
        assertEquals("IllegalArgumentException\n" + "IllegalArgumentException\n", errContent.toString());
    }
}
