package com.bonepl.chromaleague.rest.playerlist;

import com.jsoniter.annotation.JsonCreator;

public class Player {
    private final String summonerName;
    private final Team team;
    private final boolean isDead;

    @SuppressWarnings("BooleanParameter")
    @JsonCreator
    public Player(String summonerName, Team team, boolean isDead) {
        this.summonerName = summonerName;
        this.team = team;
        this.isDead = isDead;
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
}
