package net.booone.chromaleague.tasks;

import net.booone.chromaleague.state.GameState;
import net.booone.chromaleague.state.RunningState;
import net.booone.razersdk.ChromaRestSDK;

import java.util.logging.Level;
import java.util.logging.Logger;

public record RefreshMainHudTask(ChromaRestSDK chromaRestSDK) implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(RefreshMainHudTask.class.getName());

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
