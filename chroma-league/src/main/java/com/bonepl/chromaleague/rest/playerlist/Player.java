package com.bonepl.chromaleague.rest.playerlist;

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
