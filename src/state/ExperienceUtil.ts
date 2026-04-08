import {RunningState} from './RunningState.js';

const LEVEL_EXPERIENCE_MAP: Record<number, number> = {
    1: 0,
    2: 280,
    3: 660,
    4: 1140,
    5: 1720,
    6: 2400,
    7: 3180,
    8: 4060,
    9: 5040,
    10: 6120,
    11: 7300,
    12: 8580,
    13: 9960,
    14: 11440,
    15: 13020,
    16: 14700,
    17: 16480,
    18: 18360,
};

export function getApproxDeathTimeForEventTime(eventTime: number, currentGameTime: number): number {
    const approxLevel = getApproxLevelOnDeath(eventTime, currentGameTime);
    return getApproxDeathTimerForLevel(approxLevel);
}

function getApproxDeathTimerForLevel(level: number): number {
    if (level < 7) return (level << 1) + 4;
    if (level === 7) return 21;
    return level * 2.5 + 7.5;
}

function getApproxLevelOnDeath(eventTime: number, currentGameTime: number): number {
    const currentLevel = RunningState.getGameState().activePlayer!.level;
    const approxCurrentExp = getApproxCurrentExp(currentLevel);
    const timePercent = eventTime / currentGameTime;
    return getLevelFromExp(approxCurrentExp * timePercent);
}

function getApproxCurrentExp(currentLevel: number): number {
    if (currentLevel === 18) return 19000;
    return (LEVEL_EXPERIENCE_MAP[currentLevel] + LEVEL_EXPERIENCE_MAP[currentLevel + 1]) / 2;
}

function getLevelFromExp(experience: number): number {
    const entry = Object.entries(LEVEL_EXPERIENCE_MAP)
        .map(([k, v]) => [Number(k), v] as const)
        .find(([, v]) => v >= experience);
    if (!entry) throw new Error('Could not determine level from experience');
    return entry[0];
}
