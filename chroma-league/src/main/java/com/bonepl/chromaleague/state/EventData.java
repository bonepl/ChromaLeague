package com.bonepl.chromaleague.state;

import com.bonepl.chromaleague.rest.eventdata.DragonType;
import com.bonepl.chromaleague.rest.eventdata.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class EventData {
    private final List<DragonType> killedDragons = new ArrayList<>();
    private final List<Event> processedEvents = new LinkedList<>();
    private Double baronBuffEnd;
    private Double elderBuffEnd;
    private int totalEldersKilled;
    private int activePlayerKillingSpree;
    private int activePlayerAssistSpree;
    private boolean riftAnimationPlayed = false;
    private RespawnIndicator respawnIndicator = RespawnIndicator.IDLE;
    private Double deathTime;
    private Double respawnTime;

    public Double getBaronBuffEnd() {
        return baronBuffEnd;
    }

    public void setBaronBuffEnd(Double baronBuffEnd) {
        this.baronBuffEnd = baronBuffEnd;
    }

    public List<DragonType> getKilledDragons() {
        return Collections.unmodifiableList(killedDragons);
    }

    public void addKilledDragon(DragonType dragonType) {
        killedDragons.add(dragonType);
    }

    public Double getElderBuffEnd() {
        return elderBuffEnd;
    }

    public void setElderBuffEnd(Double elderBuffEnd) {
        this.elderBuffEnd = elderBuffEnd;
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

    public boolean didRiftAnimationPlay() {
        return riftAnimationPlayed;
    }

    public void setRiftAnimationPlayed(boolean riftAnimationPlayed) {
        this.riftAnimationPlayed = riftAnimationPlayed;
    }

    public void addProcessedEvents(List<Event> events) {
        processedEvents.addAll(events);
    }

    public List<Event> getUnprocessedEvents(List<Event> fetchedEvents) {
        return fetchedEvents.stream()
                .filter(fetchedEvent -> !processedEvents.contains(fetchedEvent)).toList();
    }

    public RespawnIndicator getRespawnIndicator() {
        return respawnIndicator;
    }

    public void setRespawnIndicator(RespawnIndicator respawnIndicator) {
        this.respawnIndicator = respawnIndicator;
    }

    public Double getDeathTime() {
        return deathTime;
    }

    public void setDeathTime(Double deathTime) {
        this.deathTime = deathTime;
    }

    public Double getRespawnTime() {
        return respawnTime;
    }

    public void setRespawnTime(Double respawnTime) {
        this.respawnTime = respawnTime;
    }

    //TEST ONLY
    public List<Event> getProcessedEvents() {
        return Collections.unmodifiableList(processedEvents);
    }
}
