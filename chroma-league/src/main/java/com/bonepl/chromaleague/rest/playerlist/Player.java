package com.bonepl.chromaleague.rest.playerlist;

import com.jsoniter.annotation.JsonCreator;

public class Player {
    private final String summonerName;
    private final Team team;
    private final boolean isDead;
    private final String championName;

    @SuppressWarnings("BooleanParameter")
    @JsonCreator
    public Player(String summonerName, Team team, boolean isDead, String championName) {
        this.summonerName = summonerName;
        this.team = team;
        this.isDead = isDead;
        this.championName = championName;
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
}
