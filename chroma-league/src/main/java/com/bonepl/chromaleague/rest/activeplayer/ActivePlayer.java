package com.bonepl.chromaleague.rest.activeplayer;

import com.jsoniter.annotation.JsonCreator;

public class ActivePlayer {
    private final double currentGold;
    private final int level;
    private final String summonerName;
    private final ChampionStats championStats;

    @JsonCreator
    public ActivePlayer(double currentGold, int level, String summonerName, ChampionStats championStats) {
        this.currentGold = currentGold;
        this.level = level;
        this.summonerName = summonerName;
        this.championStats = championStats;
    }

    public double getCurrentGold() {
        return currentGold;
    }

    public int getLevel() {
        return level;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public ChampionStats getChampionStats() {
        return championStats;
    }
}
