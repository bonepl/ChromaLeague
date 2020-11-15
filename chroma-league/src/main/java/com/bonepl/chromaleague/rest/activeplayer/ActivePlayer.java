package com.bonepl.chromaleague.rest.activeplayer;

import com.jsoniter.annotation.JsonCreator;

public class ActivePlayer {
    private final double currentGold;
    private final int level;
    private final ChampionStats championStats;

    @JsonCreator
    public ActivePlayer(double currentGold, int level, ChampionStats championStats) {
        this.currentGold = currentGold;
        this.level = level;
        this.championStats = championStats;
    }

    public double getCurrentGold() {
        return currentGold;
    }

    public int getLevel() {
        return level;
    }

    public ChampionStats getChampionStats() {
        return championStats;
    }
}
