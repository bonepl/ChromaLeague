package com.bonepl.chromaleague.rest.gamestats;

import com.jsoniter.annotation.JsonCreator;

public class GameStats {
    private final String gameMode;
    private final double gameTime;

    @JsonCreator
    public GameStats(String gameMode, double gameTime) {
        this.gameMode = gameMode;
        this.gameTime = gameTime;
    }
}
