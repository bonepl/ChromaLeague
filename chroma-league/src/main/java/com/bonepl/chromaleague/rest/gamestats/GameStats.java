package com.bonepl.chromaleague.rest.gamestats;

import com.jsoniter.annotation.JsonCreator;

public class GameStats {
    public final String gameMode;
    public final double gameTime;

    @JsonCreator
    public GameStats(String gameMode, double gameTime) {
        this.gameMode = gameMode;
        this.gameTime = gameTime;
    }
}
