import {DragonType} from '../rest/eventdata/DragonType.js';
import {RespawnIndicator} from './RespawnIndicator.js';
import {RunningState} from './RunningState.js';

/** Duration of the first Elder Dragon buff in seconds. Source: League of Legends wiki */
export const FIRST_ELDER_TIME = 150;
/** Duration of subsequent Elder Dragon buffs in seconds. Source: League of Legends wiki */
export const NEXT_ELDER_TIME = 300;
/** Duration of the Baron Nashor buff in seconds. Source: League of Legends wiki */
export const BARON_TIME = 180;
export const DEFAULT_MAP_TERRAIN = 'Default';

export function isActivePlayerAlive(): boolean {
    const { playerList, playerRiotId } = RunningState.getGameState();
    if (!playerList || !playerRiotId) return true;
    return !playerList.getActivePlayer(playerRiotId).isDead;
}

export function getHpPercentage(): number {
    const stats = RunningState.getGameState().activePlayer?.championStats;
    if (!stats) return 0;
    return getPercentage(stats.currentHealth, stats.maxHealth);
}

export function getResourcePercentage(): number {
    const stats = RunningState.getGameState().activePlayer?.championStats;
    if (!stats) return 0;
    return getPercentage(stats.resourceValue, stats.resourceMax);
}

export function getGold(): number {
    return RunningState.getGameState().activePlayer?.currentGold ?? 0;
}

export function getLevel(): number {
    return RunningState.getGameState().activePlayer?.level ?? 1;
}

export function getGoldPercentage(goldFull: number): number {
    return Math.max(getPercentage(RunningState.getGameState().activePlayer?.currentGold ?? 0, goldFull), 0);
}

export function getActivePlayerRange(): number {
    return RunningState.getGameState().activePlayer?.championStats.attackRange ?? 0;
}

function getPercentage(a: number, b: number): number {
    return Math.trunc(a * 100 / b);
}

export function startBaronBuff(eventTime: number, currentTimeForReconnection: number): void {
    if (playerAliveAtEventTime(eventTime, currentTimeForReconnection)) {
        if (hasExpired(eventTime, currentTimeForReconnection, BARON_TIME)) {
            RunningState.getGameState().eventData.baronBuffEnd = null;
        } else {
            RunningState.getGameState().eventData.baronBuffEnd = eventTime + BARON_TIME;
        }
    }
}

function hasExpired(eventTime: number, currentTimeForReconnection: number, expirationTime: number): boolean {
    if (currentTimeForReconnection === 0) {
        const gameStats = RunningState.getGameState().gameStats;
        if (!gameStats) return false;
        return gameStats.gameTime - eventTime > expirationTime;
    }
    return currentTimeForReconnection - eventTime > expirationTime;
}

export function hasBaronBuff(): boolean {
    const eventData = RunningState.getGameState().eventData;
    const gameStats = RunningState.getGameState().gameStats;
    if (gameStats && eventData.baronBuffEnd !== null && isActivePlayerAlive()) {
        return gameStats.gameTime < eventData.baronBuffEnd;
    }
    eventData.baronBuffEnd = null;
    return false;
}

export function startElderBuff(eventTime: number, currentTimeForReconnection: number): void {
    addKilledElder();
    if (playerAliveAtEventTime(eventTime, currentTimeForReconnection)) {
        const total = getTotalEldersKilled();
        if (total === 1) {
            computeElderBuffTimes(eventTime, currentTimeForReconnection, FIRST_ELDER_TIME);
        } else if (total > 1) {
            computeElderBuffTimes(eventTime, currentTimeForReconnection, NEXT_ELDER_TIME);
        }
    }
}

function computeElderBuffTimes(eventTime: number, currentTimeForReconnection: number, elderBuffTime: number): void {
    if (hasExpired(eventTime, currentTimeForReconnection, elderBuffTime)) {
        RunningState.getGameState().eventData.elderBuffEnd = null;
    } else {
        RunningState.getGameState().eventData.elderBuffEnd = eventTime + elderBuffTime;
    }
}

function playerAliveAtEventTime(eventTime: number, currentTimeOrReconnectionTime: number): boolean {
    const eventData = RunningState.getGameState().eventData;
    const gameStats = RunningState.getGameState().gameStats;
    if (!gameStats) return true;
    const gameTime = gameStats.gameTime;
    if (eventData.deathTime === null) return true;
    const eventLocalTime = gameTime - (currentTimeOrReconnectionTime - eventTime);
    return !(eventLocalTime > eventData.deathTime && eventLocalTime < eventData.respawnTime!);
}

export function hasElderBuff(): boolean {
    const eventData = RunningState.getGameState().eventData;
    const gameStats = RunningState.getGameState().gameStats;
    if (gameStats && eventData.elderBuffEnd !== null && isActivePlayerAlive()) {
        return gameStats.gameTime < eventData.elderBuffEnd;
    }
    eventData.elderBuffEnd = null;
    return false;
}

export function getKilledDragons(): readonly DragonType[] {
    return RunningState.getGameState().eventData.getKilledDragons();
}

export function getTotalEldersKilled(): number {
    return RunningState.getGameState().eventData.totalEldersKilled;
}

export function addKilledElder(): void {
    RunningState.getGameState().eventData.totalEldersKilled++;
}

export function getActivePlayerKillingSpree(): number {
    return RunningState.getGameState().eventData.activePlayerKillingSpree;
}

export function addPlayerKill(): void {
    RunningState.getGameState().eventData.activePlayerKillingSpree++;
}

export function getActivePlayerAssistSpree(): number {
    return RunningState.getGameState().eventData.activePlayerAssistSpree;
}

export function addPlayerAssist(): void {
    RunningState.getGameState().eventData.activePlayerAssistSpree++;
}

export function shouldPlayRespawnAnimation(): boolean {
    const eventData = RunningState.getGameState().eventData;
    const { playerList, playerRiotId } = RunningState.getGameState();
    if (!playerList || !playerRiotId) return false;
    const respawnTimer = playerList.getActivePlayer(playerRiotId).respawnTimer;
    if (respawnTimer > 0.0 && respawnTimer < 3.0) {
        if (eventData.respawnIndicator === RespawnIndicator.IDLE) {
            eventData.respawnIndicator = RespawnIndicator.CHARGING;
            return true;
        }
    } else if (eventData.respawnIndicator === RespawnIndicator.CHARGING) {
        eventData.respawnIndicator = RespawnIndicator.IDLE;
    }
    return false;
}

export function shouldPlayRiftAnimation(): boolean {
    const eventData = RunningState.getGameState().eventData;
    if (eventData.riftAnimationPlayed) {
        return false;
    }
    const gameStats = RunningState.getGameState().gameStats;
    if (!gameStats || gameStats.mapTerrain === DEFAULT_MAP_TERRAIN) {
        return false;
    }
    eventData.riftAnimationPlayed = true;
    return true;
}
