package net.booone.chromaleague.rest.activeplayer;

import com.jsoniter.annotation.JsonCreator;

public record ChampionStats(double attackRange, double currentHealth, double maxHealth, double resourceMax,
                            double resourceValue, String resourceType) {
    @JsonCreator
    public ChampionStats {
    }
}
