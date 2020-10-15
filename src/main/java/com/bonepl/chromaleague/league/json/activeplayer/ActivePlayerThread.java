package com.bonepl.chromaleague.league.json.activeplayer;

import com.bonepl.chromaleague.league.GameState;
import com.bonepl.chromaleague.league.hud.GoldBar;
import com.bonepl.chromaleague.league.json.ApacheLeagueHttpClient;
import com.bonepl.chromaleague.league.json.GameDetectionThread;
import com.bonepl.chromaleague.league.json.activeplayer.model.ActivePlayer;
import com.bonepl.chromaleague.league.json.activeplayer.model.ChampionStats;
import com.jsoniter.JsonIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActivePlayerThread extends Thread {
    private final static Logger logger = LogManager.getLogger();

    boolean alive = true;

    public void run() {
        while (alive) {
            if (GameDetectionThread.isGameActive()) {
                while (GameDetectionThread.isGameActive()) {
                    fetchAndUpdateData();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void fetchAndUpdateData() {
        ApacheLeagueHttpClient.get("https://127.0.0.1:2999/liveclientdata/activeplayer")
                .map(activePlayer -> JsonIterator.deserialize(activePlayer, ActivePlayer.class))
                .ifPresentOrElse(GameState::setActivePlayer,
                        () -> GameState.setActivePlayer(null));
    }

    public int getHpPercentage() {
        if (GameState.isActivePlayerAvailable()) {
            final ChampionStats championStats = GameState.getActivePlayer().getChampionStats();
            if (championStats != null) {
                return (int) Math.floor((championStats.getCurrentHealth() / championStats.getMaxHealth()) * 100);
            }
        }
        return 0;
    }

    public int getResourcePercentage() {
        if (GameState.isActivePlayerAvailable()) {
            final ChampionStats championStats = GameState.getActivePlayer().getChampionStats();
            if (championStats != null) {
                return (int) Math.floor((championStats.getResourceValue() / championStats.getResourceMax()) * 100);
            }
        }
        return 0;
    }

    public int getGoldPercentage() {
        if (GameState.isActivePlayerAvailable()) {
            return (int) Math.floor((GameState.getActivePlayer().getCurrentGold() / GoldBar.GOLD_FULL) * 100);
        }
        return 0;
    }
}
