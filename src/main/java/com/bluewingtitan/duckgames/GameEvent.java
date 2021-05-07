package com.bluewingtitan.duckgames;

public class GameEvent {
    public final EventType eventType;
    public final int SecondsAfter;

    public GameEvent(EventType eventType, int secondsAfter) {
        this.eventType = eventType;
        SecondsAfter = secondsAfter;
    }
}