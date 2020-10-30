package com.bonepl.chromaleague.state;

public final class RunningState {
    private static boolean runningGameChanged = false;
    private static boolean riotApiUp = false;
    private static boolean runningGame = false;
    private static GameState gameState;

    private RunningState() {
    }

    public static boolean isRunningGameChanged() {
        return runningGameChanged;
    }

    public static boolean isRunningGame() {
        runningGameChanged = false;
        return runningGame;
    }

    public static void setRunningGame(boolean runningGame) {
        if (RunningState.runningGame != runningGame) {
            runningGameChanged = true;
            RunningState.runningGame = runningGame;
            if (runningGame) {
                gameState = new GameState();
            } else {
                gameState = null;
            }
        }
    }

    public static boolean isRiotApiUp() {
        return riotApiUp;
    }

    public static void setRiotApiUp(boolean riotApiUp) {
        if (RunningState.riotApiUp != riotApiUp) {
            RunningState.riotApiUp = riotApiUp;
        }
    }

    public static GameState getGameState() {
        return gameState;
    }
}
