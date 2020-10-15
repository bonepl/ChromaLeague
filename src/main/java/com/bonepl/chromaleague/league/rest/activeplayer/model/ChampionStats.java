package com.bonepl.chromaleague.league.rest.activeplayer.model;

public class ChampionStats {
    double currentHealth;
    double maxHealth;
    double resourceMax;
    double resourceValue;
    String resourceType;

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
