package net.booone.chromaleague.rest.gamestats;

import com.jsoniter.annotation.JsonCreator;

public record GameStats(String gameMode, double gameTime, String mapTerrain) {
    @JsonCreator
    public GameStats {
    }
}
