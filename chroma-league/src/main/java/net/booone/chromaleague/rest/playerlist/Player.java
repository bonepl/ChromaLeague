package net.booone.chromaleague.rest.playerlist;

import com.jsoniter.annotation.JsonCreator;

public record Player(String riotId, String riotIdGameName, Team team,
                     String championName, double respawnTimer, boolean isDead) {
    @JsonCreator
    public Player {
    }

}
