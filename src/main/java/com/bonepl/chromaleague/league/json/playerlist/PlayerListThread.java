package com.bonepl.chromaleague.league.json.playerlist;

import com.bonepl.chromaleague.league.GameState;
import com.bonepl.chromaleague.league.json.ApacheLeagueHttpClient;
import com.bonepl.chromaleague.league.json.GameDetectionThread;
import com.bonepl.chromaleague.league.json.playerlist.model.Player;
import com.bonepl.chromaleague.league.json.playerlist.model.PlayerList;
import com.jsoniter.JsonIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerListThread extends Thread {
    private final static Logger logger = LogManager.getLogger();

    boolean alive = true;

    public void run() {
        while (alive) {
            if (GameDetectionThread.isGameActive()) {
                while (GameDetectionThread.isGameActive()) {
                    fetchAndUpdateData();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void fetchAndUpdateData() {
        ApacheLeagueHttpClient.get("https://127.0.0.1:2999/liveclientdata/playerlist")
                .map(playerList -> JsonIterator.deserialize(playerList, Player[].class))
                .map(PlayerList::new)
                .ifPresentOrElse(GameState::setPlayerList,
                        () -> GameState.setPlayerList(null));
    }

    public boolean isActivePlayerDead() {
        if (GameState.isPlayerListAvailable()) {
            final Player activePlayer = GameState.getPlayerList().getActivePlayer();
            if (activePlayer != null) {
                return activePlayer.isDead();
            }
        }
        return false;
    }

    public static PlayerList getPlayerList() {
        return GameState.getPlayerList();
    }

    // TEST ONLY
    public static void setPlayerList(PlayerList playerList) {
        GameState.setPlayerList(playerList);
    }
}
