package com.bonepl.chromaleague.tasks;

import com.bonepl.chromaleague.hud.animations.LoadingAnimation;
import com.bonepl.chromaleague.state.RunningState;
import com.bonepl.razersdk.ChromaRestSDK;

import java.util.logging.Level;
import java.util.logging.Logger;

public record GameLoadingTask(ChromaRestSDK chromaRestSDK,
                              LoadingAnimation loadingAnimation) implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(GameLoadingTask.class.getName());

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
