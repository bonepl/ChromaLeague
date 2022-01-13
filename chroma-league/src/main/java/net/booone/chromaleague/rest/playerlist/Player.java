package net.booone.chromaleague.rest.playerlist;

import com.jsoniter.annotation.JsonCreator;

public record Player(String summonerName, Team team,
                     String championName, double respawnTimer, boolean isDead) {
    @JsonCreator
    public Player {
    }
}
