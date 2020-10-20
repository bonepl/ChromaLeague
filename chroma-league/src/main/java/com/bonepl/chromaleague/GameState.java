package com.bonepl.chromaleague;

import com.bonepl.chromaleague.rest.CustomData;
import com.bonepl.chromaleague.rest.activeplayer.model.ActivePlayer;
import com.bonepl.chromaleague.rest.eventdata.model.Event;
import com.bonepl.chromaleague.rest.playerlist.model.PlayerList;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class GameState {
    private volatile static String activePlayerName;
    private volatile static ActivePlayer activePlayer;
    private volatile static PlayerList playerList;
    private static final CustomData customData = new CustomData();
    private static final Queue<Event> unprocessedEvents = new ConcurrentLinkedQueue<>();

    private GameState() {
    }

    public static void setActivePlayerName(String activePlayerName) {
        GameState.activePlayerName = activePlayerName;
    }

    public static String getActivePlayerName() {
        return activePlayerName;
    }

    public static boolean isGameActive() {
        return activePlayerName != null;
    }

    public static void setActivePlayer(ActivePlayer activePlayer) {
        GameState.activePlayer = activePlayer;
    }

    public static ActivePlayer getActivePlayer() {
        return activePlayer;
    }

    public static boolean isActivePlayerAvailable() {
        return activePlayer != null && activePlayer.getChampionStats() != null;
    }

    public static void setPlayerList(PlayerList playerList) {
        GameState.playerList = playerList;
    }

    public static PlayerList getPlayerList() {
        return playerList;
    }

    public static boolean isPlayerListAvailable() {
        return playerList != null && playerList.getActivePlayer() != null;
    }

    public static void addUnprocessedEvents(Collection<Event> events) {
        unprocessedEvents.addAll(events);
    }

    public static Event pollNextUnprocessedEvent() {
        return unprocessedEvents.poll();
    }

    public static boolean hasUnprocessedEvents() {
        return !unprocessedEvents.isEmpty();
    }

    public static CustomData getCustomData() {
        return customData;
    }
}
