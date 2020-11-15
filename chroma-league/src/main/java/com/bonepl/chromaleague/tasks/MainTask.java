package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.state.RunningState;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainTask implements Runnable {
    public static final int ACTIVE_PLAYER_FETCH_DELAY = 100;
    public static final int EVENTS_FETCH_DELAY = 300;
    public static final int MAIN_HUD_REFRESH_DELAY = 50;

    private static final Logger LOGGER = Logger.getLogger(MainTask.class.getName());

    private MainThreads mainThreads;

    @Override
    public void run() {
        try {
            if (RunningState.isRiotApiUp()) {
                if (mainThreads == null || !mainThreads.isAlive()) {
                    mainThreads = new MainThreads();
                }
                if (RunningState.isRunningGameChanged() && RunningState.isRunningGame()) {
                    mainThreads.initializeGameThreads();
                }
            } else {
                if (mainThreads != null && mainThreads.isAlive()) {
                    mainThreads.close();
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex, () -> "Exception in MainTask");
        }
    }
}
