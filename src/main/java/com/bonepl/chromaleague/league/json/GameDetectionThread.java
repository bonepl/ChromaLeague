package com.bonepl.chromaleague.league.json;

import com.bonepl.chromaleague.league.GameState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameDetectionThread extends Thread {
    private final static Logger logger = LogManager.getLogger();
    private static GameDetectionThread instance;

    boolean alive = true;

    public static void runThread() {
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
        ApacheLeagueHttpClient.get("https://127.0.0.1:2999/liveclientdata/activeplayername")
                .filter(name -> !name.contains("RESOURCE_NOT_FOUND"))
                .map(name -> name.substring(1, name.length() - 1))
                .ifPresentOrElse(GameState::setActivePlayerName,
                        () -> GameState.setActivePlayerName(null));
    }

    public static boolean isGameActive() {
        return GameState.isActivePlayerNameAvailable();
    }

    public static String getActivePlayerName() {
        return GameState.getActivePlayerName();
    }
}
