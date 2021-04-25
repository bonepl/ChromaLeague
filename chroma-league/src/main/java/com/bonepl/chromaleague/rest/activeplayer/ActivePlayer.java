package com.bonepl.chromaleague.rest.activeplayer;

import com.jsoniter.annotation.JsonCreator;

public record ActivePlayer(double currentGold, int level,
                           ChampionStats championStats) {
    @JsonCreator
    public ActivePlayer {
    }
}
