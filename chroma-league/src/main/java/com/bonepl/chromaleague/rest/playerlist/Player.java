package com.bonepl.chromaleague.rest.playerlist;

import com.jsoniter.annotation.JsonCreator;

public record Player(String summonerName, Team team,
                     String championName, boolean isDead) {
    @JsonCreator
    public Player {
    }
}
