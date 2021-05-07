package com.bluewingtitan.duckgames;

public class Step {
    public final int SecondsAfter;
    public final int WorldBorderSize;
    public final int TimeToShrink;

    public Step(int secondsAfter, int worldBorderSize, int timeToShrink) {
        SecondsAfter = secondsAfter;
        WorldBorderSize = worldBorderSize;
        TimeToShrink = timeToShrink;
    }
}
