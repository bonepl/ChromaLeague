package com.bonepl.chromaleague.rest.activeplayer.model;

public class ActivePlayer {
    double currentGold;
    int level;
    String summonerName;
    ChampionStats championStats;

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
