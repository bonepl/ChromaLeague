package com.bonepl.chromaleague.state;

import com.bonepl.chromaleague.rest.eventdata.DragonType;
import com.bonepl.chromaleague.rest.eventdata.Event;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class EventData {
    private final List<DragonType> killedDragons = new ArrayList<>();
    private final List<Event> processedEvents = new LinkedList<>();
    private LocalTime baronBuffEnd;
    private LocalTime elderBuffEnd;
    private int totalEldersKilled;
    private int activePlayerKillingSpree;
    private int activePlayerAssistSpree;
    private double lastDeathTime;
    private double approxLastDeathTimer;
    private RespawnIndicator respawnIndicator = RespawnIndicator.IDLE;

    public void setLastDeathTime(double lastDeathTime) {
        this.lastDeathTime = lastDeathTime;
    }

    public double getLastDeathTime() {
        return lastDeathTime;
    }

    public double getApproxLastDeathTimer() {
        return approxLastDeathTimer;
    }

    public void setApproxLastDeathTimer(double approxLastDeathTimer) {
        this.approxLastDeathTimer = approxLastDeathTimer;
    }

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

    public void addProcessedEvents(List<Event> events){
        processedEvents.addAll(events);
    }

    public List<Event> getUnprocessedEvents(List<Event> fetchedEvents){
        return fetchedEvents.stream()
                .filter(fetchedEvent -> !processedEvents.contains(fetchedEvent))
                .collect(Collectors.toUnmodifiableList());
    }

    public RespawnIndicator getRespawnIndicator() {
        return respawnIndicator;
    }

    public void setRespawnIndicator(RespawnIndicator respawnIndicator) {
        this.respawnIndicator = respawnIndicator;
    }

    //TEST ONLY
    public List<Event> getProcessedEvents() {
        return Collections.unmodifiableList(processedEvents);
    }
}
