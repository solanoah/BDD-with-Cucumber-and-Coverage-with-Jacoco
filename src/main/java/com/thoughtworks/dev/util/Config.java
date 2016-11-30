package com.thoughtworks.dev.util;

import com.thoughtworks.dev.model.Event;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Pattern;

public final class Config {

    public static final float MORNING_SESSION_DURATION = 3 * 60;
    public static final float AFTERNOON_SESSION_DURATION = 4 * 60;

    public static final Comparator<Event> COMPARATOR_START_TIME = (e1, e2) -> e1.getStartTime().compareTo(e2.getStartTime());
    public static final Comparator<Event> COMPARATOR_DURATION = (e1, e2) -> Integer.compare(e2.getDuration(), e1.getDuration());

    public static final int MORNING_SESSION_START_HOUR = 9;
    public static final int AFTERNOON_SESSION_START_HOUR = 13;
    public static final int EARLY_NETWORK_START_HOUR = 16;
    public static final int DEFAULT_NETWORK_START_HOUR = 17;
    public static final int DEFAULT_LUNCH_START_HOUR = 12;

    public static final float MAX_TRACK_DURATION = MORNING_SESSION_DURATION + AFTERNOON_SESSION_DURATION;
    public static final float MAX_SESSION_DURATION = Collections.max(Arrays.asList(MORNING_SESSION_DURATION, AFTERNOON_SESSION_DURATION));

    public static final Pattern LINE_PATTERN = Pattern.compile("^(.+)\\s(-?\\d+)?\\s?((min)|(l))$");

}
