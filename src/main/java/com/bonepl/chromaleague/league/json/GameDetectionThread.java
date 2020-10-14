package com.bonepl.chromaleague.league.json;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameDetectionThread extends Thread {
    private final static Logger logger = LogManager.getLogger();
    private static GameDetectionThread instance;
    private static LeagueHttpClient leagueHttpClient;

    boolean alive = true;

    volatile static boolean gameActive;
    static String activePlayerName;

    public static void runThread(LeagueHttpClient leagueHttpClient) {
        GameDetectionThread.leagueHttpClient = leagueHttpClient;
        instance = new GameDetectionThread();
        instance.start();
    }

    public void run() {
        while (alive) {
            fetchAndUpdateData();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void fetchAndUpdateData() {
        String json = leagueHttpClient.fetchData("https://127.0.0.1:2999/liveclientdata/activeplayername");
        if (json != null && !json.contains("RESOURCE_NOT_FOUND")) {
            if (!gameActive) {
                logger.debug(json + " has joined the game");
                GameDetectionThread.gameActive = true;
                GameDetectionThread.activePlayerName = json.replaceAll("\"", "");
            }
        } else {
            if (gameActive) {
                logger.debug("Game ended");
                GameDetectionThread.gameActive = false;
                GameDetectionThread.activePlayerName = null;
            }
        }
    }

    public static boolean isGameActive() {
        return gameActive;
    }

    public static String getActivePlayerName() {
        return activePlayerName;
    }

    // TEST ONLY
    public static void setInstance(GameDetectionThread instance) {
        GameDetectionThread.instance = instance;
    }
}
