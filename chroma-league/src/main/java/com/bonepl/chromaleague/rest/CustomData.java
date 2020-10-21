package com.bonepl.chromaleague.rest;

import com.bonepl.chromaleague.hud.DragonType;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CustomData {
    private int baronKillGameTime;
    private LocalTime baronBuffEnd;
    private int elderKillGameTime;
    private LocalTime elderBuffEnd;
    private int lastPlayerDeathTime;
    private final List<DragonType> killedDragons = new ArrayList<>();
    private int totalEldersKilled = 0;

    public void setBaronBuffEnd(LocalTime baronBuffEnd) {
        this.baronBuffEnd = baronBuffEnd;
    }

    public LocalTime getBaronBuffEnd() {
        return baronBuffEnd;
    }

    public List<DragonType> getKilledDragons() {
        return killedDragons;
    }

    public void setElderBuffEnd(LocalTime elderBuffEnd) {
        this.elderBuffEnd = elderBuffEnd;
    }

    public LocalTime getElderBuffEnd() {
        return elderBuffEnd;
    }

    public int getTotalEldersKilled() {
        return totalEldersKilled;
    }

    public void setTotalEldersKilled(int totalEldersKilled) {
        this.totalEldersKilled = totalEldersKilled;
    }

    public void reset() {
        getKilledDragons().clear();
        totalEldersKilled = 0;
    }

    public int getBaronKillGameTime() {
        return baronKillGameTime;
    }

    public void setBaronKillGameTime(int baronKillGameTime) {
        this.baronKillGameTime = baronKillGameTime;
    }

    public int getElderKillGameTime() {
        return elderKillGameTime;
    }

    public void setElderKillGameTime(int elderKillGameTime) {
        this.elderKillGameTime = elderKillGameTime;
    }

    public int getLastPlayerDeathTime() {
        return lastPlayerDeathTime;
    }

    public void setLastPlayerDeathTime(int lastPlayerDeathTime) {
        this.lastPlayerDeathTime = lastPlayerDeathTime;
    }
}
