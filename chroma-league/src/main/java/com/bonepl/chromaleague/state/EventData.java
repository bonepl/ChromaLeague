package com.bonepl.chromaleague.state;

import com.bonepl.chromaleague.rest.eventdata.DragonType;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventData {
    private volatile LocalTime baronBuffEnd;
    private volatile LocalTime elderBuffEnd;
    private final List<DragonType> killedDragons = new ArrayList<>();
    private volatile int totalEldersKilled;
    private volatile int activePlayerKillingSpree;
    private volatile int activePlayerAssistSpree;

    public void setBaronBuffEnd(LocalTime baronBuffEnd) {
        this.baronBuffEnd = baronBuffEnd;
    }

    public LocalTime getBaronBuffEnd() {
        return baronBuffEnd;
    }

    public List<DragonType> getKilledDragons() {
        return Collections.unmodifiableList(killedDragons);
    }

    public void addKilledDragon(DragonType dragonType) {
        killedDragons.add(dragonType);
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

    public int getActivePlayerKillingSpree() {
        return activePlayerKillingSpree;
    }

    public void setActivePlayerKillingSpree(int activePlayerKillingSpree) {
        this.activePlayerKillingSpree = activePlayerKillingSpree;
    }

    public int getActivePlayerAssistSpree() {
        return activePlayerAssistSpree;
    }

    public void setActivePlayerAssistSpree(int activePlayerAssistSpree) {
        this.activePlayerAssistSpree = activePlayerAssistSpree;
    }

    public void resetCounters() {
        killedDragons.clear();
        totalEldersKilled = 0;
        activePlayerKillingSpree = 0;
        activePlayerAssistSpree = 0;
    }
}
