package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.hud.MainHud;
import com.bonepl.chromaleague.state.RunningState;
import com.bonepl.razersdk.ChromaRestSDK;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RefreshMainHudTask implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ChromaRestSDK chromaRestSDK;

    public RefreshMainHudTask(ChromaRestSDK chromaRestSDK) {
        this.chromaRestSDK = chromaRestSDK;
    }

    @Override
    public void run() {
        try {
            if (RunningState.getGameState().isActivePlayerAvailable() && RunningState.getGameState().isPlayerListAvailable()) {
                chromaRestSDK.createKeyboardEffect(new MainHud());
            }
        } catch (Exception ex) {
            LOGGER.error("Error while refreshing main HUD", ex);
        }
    }
}
