package com.bonepl.chromaleague.rest.playerlist;

import com.jsoniter.annotation.JsonCreator;

public class Player {
    private final String summonerName;
    private final Team team;
    private final boolean isDead;
    private final String championName;
    private double respawnTimer;

    @SuppressWarnings("BooleanParameter")
    @JsonCreator
    public Player(String summonerName, Team team, boolean isDead, String championName, double respawnTimer) {
        this.summonerName = summonerName;
        this.team = team;
        this.isDead = isDead;
        this.championName = championName;
        this.respawnTimer = respawnTimer;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public Team getTeam() {
        return team;
    }

    public boolean isDead() {
        return isDead;
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
