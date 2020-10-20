package com.bonepl.chromaleague.rest;

import com.bonepl.chromaleague.hud.DragonType;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CustomData {
    private LocalTime baronBuffEnd;
    private final List<DragonType> killedDragons = new ArrayList<>();

    public void setBaronBuffEnd(LocalTime baronBuffEnd) {
        this.baronBuffEnd = baronBuffEnd;
    }

    public LocalTime getBaronBuffEnd() {
        return baronBuffEnd;
    }

    public List<DragonType> getKilledDragons() {
        return killedDragons;
    }
}
