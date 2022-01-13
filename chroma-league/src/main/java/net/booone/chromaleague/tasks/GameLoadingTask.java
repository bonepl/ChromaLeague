package net.booone.chromaleague.tasks;

import net.booone.chromaleague.hud.animations.LoadingAnimation;
import net.booone.chromaleague.state.RunningState;
import net.booone.razersdk.ChromaRestSDK;

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
