package com.bonepl.chromaleague.razer.league.json.activeplayer;

import com.bonepl.chromaleague.razer.league.json.GameDetectionThread;
import com.bonepl.chromaleague.razer.league.json.LeagueHttpClient;
import com.bonepl.chromaleague.razer.league.json.activeplayer.model.ActivePlayer;
import com.bonepl.chromaleague.razer.league.json.activeplayer.model.ChampionStats;
import com.jsoniter.JsonIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActivePlayerThread extends Thread {
    private final static Logger logger = LogManager.getLogger();

    private final LeagueHttpClient leagueHttpClient;
    boolean alive = true;
    private ActivePlayer activePlayer;

    public ActivePlayerThread(LeagueHttpClient leagueHttpClient) {
        this.leagueHttpClient = leagueHttpClient;
    }

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
        String json = leagueHttpClient.fetchJson("https://127.0.0.1:2999/liveclientdata/activeplayer");
        if (json != null) {
            activePlayer = JsonIterator.deserialize(json, ActivePlayer.class);
            logger.debug("New hp value: " + activePlayer.getChampionStats().getCurrentHealth());
            logger.debug("New resource value: " + activePlayer.getChampionStats().getResourceValue());
        }
    }

    public int getHpPercentage() {
        if (activePlayer != null) {
            final ChampionStats championStats = activePlayer.getChampionStats();
            if (championStats != null) {
                return (int) Math.floor((championStats.getCurrentHealth() / championStats.getMaxHealth()) * 100);
            }
        }
        return 0;
    }

    public int getResourcePercentage() {
        if (activePlayer != null) {
            final ChampionStats championStats = activePlayer.getChampionStats();
            if (championStats != null) {
                return (int) Math.floor((championStats.getResourceValue() / championStats.getResourceMax()) * 100);
            }
        }
        return 0;
    }
}
