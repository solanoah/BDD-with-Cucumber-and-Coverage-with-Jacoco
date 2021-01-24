package com.thoughtworks.dev;

import com.thoughtworks.dev.model.Event;
import com.thoughtworks.dev.model.Track;
import com.thoughtworks.dev.service.PlanningService;
import com.thoughtworks.dev.util.DateHelper;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static com.thoughtworks.dev.util.Config.COMPARATOR_START_TIME;

@Slf4j
public class AppEntry {
    private static PlanningService planningService = new PlanningService();

    /**
     * @param args supplied file path
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            log.error("No file path provided");
            System.exit(1);
        }

        BufferedReader reader;

        try {
            File inputFile = new File(args[0]);
            reader = new BufferedReader(new FileReader(inputFile));
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        for (String line; (line = reader.readLine()) != null; ) {
            try {
                AppEntry.planningService.processInputLine(line.trim());
            } catch (IllegalArgumentException e) {

                log.error("IllegalArgumentException");
            }
        }

        AppEntry.planningService.scheduleConference();

        List<Track> tracks = AppEntry.planningService.getTracks();

        tracks.forEach(track -> {
            log.info(track.toString());
            printScheduledTalks(track.getEvents());
            log.info(System.getProperty("line.separator"));
        });

        System.exit(0);
    }

    /**
     * Method added in order to make code testable
     *
     * @param planningService planning service
     */
    static void setPlanner(PlanningService planningService) {
        AppEntry.planningService = planningService;
    }

    /**
     * @param events Events to be printed
     */
    private static void printScheduledTalks(List<Event> events) {
        events.stream().sorted(COMPARATOR_START_TIME).forEach(talk -> log.info(DateHelper.DATE_FORMAT.format(talk.getStartTime()) + ' ' + talk.toString()));
    }
}
