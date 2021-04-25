package com.bonepl.chromaleague.rest.gamestats;

import com.jsoniter.annotation.JsonCreator;

public record GameStats(String gameMode, double gameTime) {
    @JsonCreator
    public GameStats {
    }
}
