package com.bonepl.chromaleague.state;

import com.bonepl.chromaleague.tasks.ChangeAwareBoolean;

public final class RunningState {
    private static final ChangeAwareBoolean RIOT_API = new ChangeAwareBoolean();
    private static final ChangeAwareBoolean RUNNING_GAME = new ChangeAwareBoolean();
    private static GameState gameState = null;

    private RunningState() {
    }

    public static ChangeAwareBoolean getRiotApi() {
        return RIOT_API;
    }

    public static ChangeAwareBoolean getRunningGame() {
        return RUNNING_GAME;
    }

    public static void setRunningGame(boolean newValue) {
        if (RUNNING_GAME.different(newValue)) {
            if (newValue) {
                gameState = new GameState();
            } else {
                gameState = null;
            }
        }
        RUNNING_GAME.setValue(newValue);
    }

    public static void setRiotApi(boolean newValue) {
        RIOT_API.setValue(newValue);
    }

    public static GameState getGameState() {
        return gameState;
    }
}
