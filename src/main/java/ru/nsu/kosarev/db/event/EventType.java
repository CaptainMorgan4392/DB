package ru.nsu.kosarev.db.event;

import java.util.HashMap;
import java.util.Map;

public enum EventType {

    CONCERT,
    PERFORMANCE,
    COMPETITION,
    FILM;

    public static final Map<Integer, EventType> MAPPINGS = new HashMap<>();

    static {
        MAPPINGS.put(1, CONCERT);
        MAPPINGS.put(2, PERFORMANCE);
        MAPPINGS.put(3, COMPETITION);
        MAPPINGS.put(4, FILM);
    }

}
