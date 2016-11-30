package com.thoughtworks.dev;

import com.thoughtworks.dev.model.Event;
import com.thoughtworks.dev.model.Track;
import com.thoughtworks.dev.service.PlanningService;
import com.thoughtworks.dev.util.DateHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static com.thoughtworks.dev.util.Config.COMPARATOR_START_TIME;

public class AppEntry
{
    private static PlanningService s_planningService = new PlanningService();

    /**
     * @param args supplied file path
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.err.println("No file path provided");
            System.exit(1);
        }

        BufferedReader reader;

        try{
            File inputFile = new File(args[0]);
            reader = new BufferedReader(new FileReader(inputFile));
        }catch (IOException e){
            e.printStackTrace();
            throw e;
        }

        for (String line; (line = reader.readLine()) != null; ) {
            line = line.trim();
            try {
                AppEntry.s_planningService.processInputLine(line);
            } catch (IllegalArgumentException e) {

                //TODO to be replaced with a proper logging function in real system
                System.err.println("IllegalArgumentException");
            }
        }

        AppEntry.s_planningService.scheduleConference();

        ArrayList<Track> tracks = AppEntry.s_planningService.getTracks();

        tracks.forEach(track -> {
            System.out.println(track.toString());
            printScheduledTalks(track.getEvents());
            System.out.println(System.getProperty("line.separator"));
        });

        System.exit(0);
    }

    /**
     * Method added in order to make code testable
     * @param planningService planning service
     */
    static void setPlanner(PlanningService planningService) {
        AppEntry.s_planningService = planningService;
    }

    /**
     * @param events Events to be printed
     */
    private static void printScheduledTalks(ArrayList<Event> events)
    {
        events.stream().sorted(COMPARATOR_START_TIME).forEach(talk -> System.out.println(DateHelper.DATE_FORMAT.format(talk.getStartTime()) + ' ' + talk.toString()));
    }
}
