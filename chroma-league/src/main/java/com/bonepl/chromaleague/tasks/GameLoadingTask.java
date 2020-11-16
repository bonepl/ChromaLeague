package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.hud.animations.LoadingAnimation;
import com.bonepl.chromaleague.state.RunningState;
import com.bonepl.razersdk.ChromaRestSDK;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameLoadingTask implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(GameLoadingTask.class.getName());

    private final ChromaRestSDK chromaRestSDK;
    private final LoadingAnimation loadingAnimation;

    public GameLoadingTask(final ChromaRestSDK chromaRestSDK) {
        this.chromaRestSDK = chromaRestSDK;
        loadingAnimation = new LoadingAnimation();
    }

    @Override
    public void run() {
        if (!RunningState.getRunningGame().getValue()) {
            try {
                chromaRestSDK.createKeyboardEffect(loadingAnimation);
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex, () -> "Error while refreshing loading animation");
            }
        }
    }
}
