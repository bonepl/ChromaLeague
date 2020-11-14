package com.bonepl.chromaleague.rest.playerlist;

import com.jsoniter.annotation.JsonCreator;

public class Player {
    private final String summonerName;
    private final Team team;
    private final String championName;

    @JsonCreator
    public Player(String summonerName, Team team, String championName) {
        this.summonerName = summonerName;
        this.team = team;
        this.championName = championName;
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
}
