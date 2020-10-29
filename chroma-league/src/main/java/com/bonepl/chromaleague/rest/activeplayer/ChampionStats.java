package com.bonepl.chromaleague.rest.activeplayer;

import com.jsoniter.annotation.JsonCreator;

public class ChampionStats {
    private final double attackRange;
    private final double currentHealth;
    private final double maxHealth;
    private final double resourceMax;
    private final double resourceValue;
    private final String resourceType;

    @JsonCreator
    public ChampionStats(double attackRange, double currentHealth, double maxHealth,
                         double resourceMax, double resourceValue, String resourceType) {
        this.attackRange = attackRange;
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
        this.resourceMax = resourceMax;
        this.resourceValue = resourceValue;
        this.resourceType = resourceType;
    }

    public double getAttackRange() {
        return attackRange;
    }

    public double getCurrentHealth() {
        return currentHealth;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public double getResourceMax() {
        return resourceMax;
    }

    public double getResourceValue() {
        return resourceValue;
    }

    public String getResourceType() {
        return resourceType;
    }
}
