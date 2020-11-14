package com.bonepl.chromaleague.rest.playerlist;

import com.jsoniter.annotation.JsonCreator;

public class Player {
    private final String summonerName;
    private final Team team;
    private final String championName;
    private double respawnTimer;

    @JsonCreator
    public Player(String summonerName, Team team, String championName, double respawnTimer) {
        this.summonerName = summonerName;
        this.team = team;
        this.championName = championName;
        this.respawnTimer = respawnTimer;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public Team getTeam() {
        return team;
    }

    public String getChampionName() {
        return championName;
    }

    public double getRespawnTimer() {
        return respawnTimer;
    }

    public void overwriteRespawnTimer(double timer) {
        respawnTimer = timer;
    }
}
