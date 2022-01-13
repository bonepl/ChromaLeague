package net.booone.chromaleague.tasks;

import net.booone.chromaleague.state.RunningState;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainTask implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(MainTask.class.getName());

    private MainThreads mainThreads;

    @Override
    public void run() {
        try {
            final boolean riotApiUp = RunningState.getRiotApi().waitForChange();
            if (riotApiUp) {
                if (mainThreads == null || !mainThreads.isAlive()) {
                    mainThreads = new MainThreads();
                }
                final boolean runningGame = RunningState.getRunningGame().waitForChange();
                if (runningGame) {
                    mainThreads.initializeGameThreads();
                }
            } else {
                if (mainThreads != null && mainThreads.isAlive()) {
                    mainThreads.close();
                }
                RunningState.getRunningGame().reset();
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex, () -> "Exception in MainTask");
        }
    }
}
