package com.bluewingtitan.duckgames;

public class DisconnectedPlayer {
    public final String playerName;
    public int secondsUntilDeath = 30;

    public DisconnectedPlayer(String playerName) {
        this.playerName = playerName;
    }
}
