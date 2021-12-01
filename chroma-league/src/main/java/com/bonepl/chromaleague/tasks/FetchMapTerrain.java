package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.state.RunningState;

import java.util.logging.Logger;

public class FetchMapTerrain extends Thread {
    public static final String DEFAULT_MAP_TERRAIN = "Default";
    private static final Logger LOGGER = Logger.getLogger(FetchMapTerrain.class.getName());

    @Override
    public void run() {
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String mapTerrain = FetchGameStats.fetchGameStats().mapTerrain();
        while (DEFAULT_MAP_TERRAIN.equals(mapTerrain)) {
            mapTerrain = FetchGameStats.fetchGameStats().mapTerrain();
            LOGGER.info("Fetched mapTerrain " + mapTerrain);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        RunningState.getGameState().getEventData().setMapTerrain(mapTerrain);
    }
}
