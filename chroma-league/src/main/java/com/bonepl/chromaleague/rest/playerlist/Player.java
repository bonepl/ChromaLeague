package com.bonepl.chromaleague.rest.playerlist;

import com.jsoniter.annotation.JsonCreator;

public class Player {
    private final String summonerName;
    private final Team team;
    private final String championName;
    private final boolean isDead;

    @JsonCreator
    public Player(String summonerName, Team team, String championName, boolean isDead) {
        this.summonerName = summonerName;
        this.team = team;
        this.championName = championName;
        this.isDead = isDead;
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

    public boolean isDead() {
        return isDead;
    }
}
