package com.bonepl.chromaleague.league.rest.activeplayer.model;

public class ActivePlayer {
    double currentGold;
    int level;
    ChampionStats championStats;

    public ChampionStats getChampionStats() {
        return championStats;
    }

    public double getCurrentGold() {
        return currentGold;
    }

    public int getLevel() {
        return level;
    }
}
