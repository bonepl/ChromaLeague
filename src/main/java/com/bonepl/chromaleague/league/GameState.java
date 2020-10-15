package com.bonepl.chromaleague.league;

import com.bonepl.chromaleague.league.json.activeplayer.model.ActivePlayer;
import com.bonepl.chromaleague.league.json.eventdata.model.Event;
import com.bonepl.chromaleague.league.json.playerlist.model.PlayerList;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameState {
    private volatile static String activePlayerName;
    private volatile static ActivePlayer activePlayer;
    private volatile static PlayerList playerList;
    private static final Queue<Event> unprocessedEvents = new ConcurrentLinkedQueue<>();

    private GameState() {
    }

    public static void setActivePlayerName(String activePlayerName) {
        GameState.activePlayerName = activePlayerName;
    }

    public static String getActivePlayerName() {
        return activePlayerName;
    }

    public static boolean isActivePlayerNameAvailable() {
        return activePlayerName != null;
    }

    public static void setActivePlayer(ActivePlayer activePlayer) {
        GameState.activePlayer = activePlayer;
    }

    public static ActivePlayer getActivePlayer() {
        return activePlayer;
    }

    public static boolean isActivePlayerAvailable() {
        return activePlayer != null;
    }

    public static void setPlayerList(PlayerList playerList) {
        GameState.playerList = playerList;
    }

    public static PlayerList getPlayerList() {
        return playerList;
    }

    public static boolean isPlayerListAvailable() {
        return playerList != null;
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
}
