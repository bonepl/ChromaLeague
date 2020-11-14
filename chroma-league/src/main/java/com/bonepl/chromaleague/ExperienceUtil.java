package com.bonepl.chromaleague;

import com.bonepl.chromaleague.state.RunningState;

import java.util.HashMap;
import java.util.Map;

public final class ExperienceUtil {
    private static final Map<Integer, Integer> LEVEL_EXPERIENCE_MAP = new HashMap<>();

    static {
        LEVEL_EXPERIENCE_MAP.put(1, 0);
        LEVEL_EXPERIENCE_MAP.put(2, 280);
        LEVEL_EXPERIENCE_MAP.put(3, 660);
        LEVEL_EXPERIENCE_MAP.put(4, 1140);
        LEVEL_EXPERIENCE_MAP.put(5, 1720);
        LEVEL_EXPERIENCE_MAP.put(6, 2400);
        LEVEL_EXPERIENCE_MAP.put(7, 3180);
        LEVEL_EXPERIENCE_MAP.put(8, 4060);
        LEVEL_EXPERIENCE_MAP.put(9, 5040);
        LEVEL_EXPERIENCE_MAP.put(10, 6120);
        LEVEL_EXPERIENCE_MAP.put(11, 7300);
        LEVEL_EXPERIENCE_MAP.put(12, 8580);
        LEVEL_EXPERIENCE_MAP.put(13, 9960);
        LEVEL_EXPERIENCE_MAP.put(14, 11440);
        LEVEL_EXPERIENCE_MAP.put(15, 13020);
        LEVEL_EXPERIENCE_MAP.put(16, 14700);
        LEVEL_EXPERIENCE_MAP.put(17, 16480);
        LEVEL_EXPERIENCE_MAP.put(18, 18360);
    }

    private ExperienceUtil() {
    }

    public static double getApproxDeathTimeForLevel(final int level) {
        return getApproxDeathTimerForLevel(level);
    }

    public static double getApproxDeathTimeForEventTime(final double eventTime, final double currentGameTime) {
        final int approxLevelOnDeath = getApproxLevelOnDeath(eventTime, currentGameTime);
        return getApproxDeathTimerForLevel(approxLevelOnDeath);
    }

    private static double getApproxDeathTimerForLevel(final int level) {
        if (level < 7) {
            return (level << 1) + 4;
        }
        if (level == 7) {
            return 21;
        }
        return level * 2.5 + 7.5;
    }

    private static int getApproxLevelOnDeath(final double eventTime, final double currentGameTime) {
        final int currentLevel = RunningState.getGameState().getActivePlayer().getLevel();
        final int approxCurrentExp = getApproxCurrentExp(currentLevel);
        final double timePercent = eventTime / currentGameTime;
        return getLevelFromExp(approxCurrentExp * timePercent);
    }

    private static int getApproxCurrentExp(final int currentLevel) {
        if (currentLevel == 18) {
            return 19000;
        }
        return (LEVEL_EXPERIENCE_MAP.get(currentLevel) + LEVEL_EXPERIENCE_MAP.get(currentLevel + 1)) / 2;
    }

    private static int getLevelFromExp(final double experience) {
        return LEVEL_EXPERIENCE_MAP.entrySet().stream().filter(entry -> entry.getValue() >= experience).findFirst().map(Map.Entry::getKey).orElseThrow();
    }
}
