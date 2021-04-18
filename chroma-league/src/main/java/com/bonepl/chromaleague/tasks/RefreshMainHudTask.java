package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.state.GameState;
import com.bonepl.chromaleague.state.RunningState;
import com.bonepl.razersdk.ChromaRestSDK;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RefreshMainHudTask implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(RefreshMainHudTask.class.getName());
    private final ChromaRestSDK chromaRestSDK;

    public RefreshMainHudTask(ChromaRestSDK chromaRestSDK) {
        this.chromaRestSDK = chromaRestSDK;
    }

    @Override
    public void run() {
        try {
            final GameState gameState = RunningState.getGameState();
            if (gameState != null && gameState.getActivePlayer() != null && gameState.getPlayerList() != null) {
                chromaRestSDK.createKeyboardEffect(gameState.getMainHud());
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex, () -> "Error while refreshing main HUD");
        }
    }
}
