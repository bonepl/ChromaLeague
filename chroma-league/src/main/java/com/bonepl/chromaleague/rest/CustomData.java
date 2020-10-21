package com.bonepl.chromaleague.rest;

import com.bonepl.chromaleague.hud.DragonType;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CustomData {
    private LocalTime baronBuffEnd;
    private LocalTime elderBuffEnd;
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
}
