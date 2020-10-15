package com.bonepl.chromaleague.league.rest.playerlist.model;

public class Player {
    String summonerName;
    Team team;
    boolean isDead;

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
