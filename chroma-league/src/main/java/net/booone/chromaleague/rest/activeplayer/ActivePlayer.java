package net.booone.chromaleague.rest.activeplayer;

import com.jsoniter.annotation.JsonCreator;

public record ActivePlayer(String riotId, String riotIdGameName,
                           double currentGold, int level,
                           ChampionStats championStats) {
    @JsonCreator
    public ActivePlayer {
    }
}
