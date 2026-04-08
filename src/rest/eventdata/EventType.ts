import {DragonType, dragonTypeFromApiType} from './DragonType.js';
import {Event} from './Event.js';
import {RunningState} from '../../state/RunningState.js';

export enum EventType {
    UNSUPPORTED = 'UNSUPPORTED',
    ALLY_BARON_KILL = 'ALLY_BARON_KILL',
    ALLY_HERALD_KILL = 'ALLY_HERALD_KILL',
    ALLY_VOID_GRUB_KILL = 'ALLY_VOID_GRUB_KILL',
    ALLY_CHEMTECH_DRAGON_KILL = 'ALLY_CHEMTECH_DRAGON_KILL',
    ALLY_CLOUD_DRAGON_KILL = 'ALLY_CLOUD_DRAGON_KILL',
    ALLY_ELDER_DRAGON_KILL = 'ALLY_ELDER_DRAGON_KILL',
    ALLY_HEXTECH_DRAGON_KILL = 'ALLY_HEXTECH_DRAGON_KILL',
    ALLY_INFERNAL_DRAGON_KILL = 'ALLY_INFERNAL_DRAGON_KILL',
    ALLY_MOUNTAIN_DRAGON_KILL = 'ALLY_MOUNTAIN_DRAGON_KILL',
    ALLY_OCEAN_DRAGON_KILL = 'ALLY_OCEAN_DRAGON_KILL',
    ENEMY_BARON_KILL = 'ENEMY_BARON_KILL',
    ENEMY_HERALD_KILL = 'ENEMY_HERALD_KILL',
    ENEMY_VOID_GRUB_KILL = 'ENEMY_VOID_GRUB_KILL',
    ENEMY_CHEMTECH_DRAGON_KILL = 'ENEMY_CHEMTECH_DRAGON_KILL',
    ENEMY_CLOUD_DRAGON_KILL = 'ENEMY_CLOUD_DRAGON_KILL',
    ENEMY_ELDER_DRAGON_KILL = 'ENEMY_ELDER_DRAGON_KILL',
    ENEMY_HEXTECH_DRAGON_KILL = 'ENEMY_HEXTECH_DRAGON_KILL',
    ENEMY_INFERNAL_DRAGON_KILL = 'ENEMY_INFERNAL_DRAGON_KILL',
    ENEMY_MOUNTAIN_DRAGON_KILL = 'ENEMY_MOUNTAIN_DRAGON_KILL',
    ENEMY_OCEAN_DRAGON_KILL = 'ENEMY_OCEAN_DRAGON_KILL',
    GAME_START = 'GAME_START',
    GAME_END_VICTORY = 'GAME_END_VICTORY',
    GAME_END_DEFEAT = 'GAME_END_DEFEAT',
    ACTIVE_PLAYER_DIED = 'ACTIVE_PLAYER_DIED',
    ACTIVE_PLAYER_KILL = 'ACTIVE_PLAYER_KILL',
    ACTIVE_PLAYER_ASSIST = 'ACTIVE_PLAYER_ASSIST',
}

const DRAGON_ALLY_MAP: Record<DragonType, EventType> = {
    [DragonType.CHEMTECH]: EventType.ALLY_CHEMTECH_DRAGON_KILL,
    [DragonType.CLOUD]: EventType.ALLY_CLOUD_DRAGON_KILL,
    [DragonType.ELDER]: EventType.ALLY_ELDER_DRAGON_KILL,
    [DragonType.HEXTECH]: EventType.ALLY_HEXTECH_DRAGON_KILL,
    [DragonType.INFERNAL]: EventType.ALLY_INFERNAL_DRAGON_KILL,
    [DragonType.MOUNTAIN]: EventType.ALLY_MOUNTAIN_DRAGON_KILL,
    [DragonType.OCEAN]: EventType.ALLY_OCEAN_DRAGON_KILL,
};

const DRAGON_ENEMY_MAP: Record<DragonType, EventType> = {
    [DragonType.CHEMTECH]: EventType.ENEMY_CHEMTECH_DRAGON_KILL,
    [DragonType.CLOUD]: EventType.ENEMY_CLOUD_DRAGON_KILL,
    [DragonType.ELDER]: EventType.ENEMY_ELDER_DRAGON_KILL,
    [DragonType.HEXTECH]: EventType.ENEMY_HEXTECH_DRAGON_KILL,
    [DragonType.INFERNAL]: EventType.ENEMY_INFERNAL_DRAGON_KILL,
    [DragonType.MOUNTAIN]: EventType.ENEMY_MOUNTAIN_DRAGON_KILL,
    [DragonType.OCEAN]: EventType.ENEMY_OCEAN_DRAGON_KILL,
};

export function eventTypeFromEvent(event: Event | null): EventType {
    if (!event) return EventType.UNSUPPORTED;

    const gameState = RunningState.getGameState();
    const playerRiotId = gameState.playerRiotId;

    if (event.EventName === 'GameStart') return EventType.GAME_START;

    if (event.EventName === 'ChampionKill') {
        if (!gameState.activePlayer) return EventType.UNSUPPORTED;
        const riotIdGameName = gameState.activePlayer.riotIdGameName;
        if (riotIdGameName === event.VictimName) return EventType.ACTIVE_PLAYER_DIED;
        if (riotIdGameName === event.KillerName) return EventType.ACTIVE_PLAYER_KILL;
        if (event.Assisters?.includes(riotIdGameName)) return EventType.ACTIVE_PLAYER_ASSIST;
    }

    if (event.EventName === 'DragonKill' && event.DragonType) {
        if (!gameState.playerList || !playerRiotId || !event.KillerName) return EventType.UNSUPPORTED;
        const dragon = dragonTypeFromApiType(event.DragonType);
        const isAlly = gameState.playerList.isAlly(event.KillerName, playerRiotId);
        return isAlly ? DRAGON_ALLY_MAP[dragon] : DRAGON_ENEMY_MAP[dragon];
    }

    if (event.EventName === 'BaronKill') {
        if (!gameState.playerList || !playerRiotId || !event.KillerName) return EventType.UNSUPPORTED;
        return gameState.playerList.isAlly(event.KillerName, playerRiotId)
            ? EventType.ALLY_BARON_KILL
            : EventType.ENEMY_BARON_KILL;
    }

    if (event.EventName === 'HeraldKill') {
        if (!gameState.playerList || !playerRiotId || !event.KillerName) return EventType.UNSUPPORTED;
        return gameState.playerList.isAlly(event.KillerName, playerRiotId)
            ? EventType.ALLY_HERALD_KILL
            : EventType.ENEMY_HERALD_KILL;
    }

    if (event.EventName === 'HordeKill') {
        if (!gameState.playerList || !playerRiotId || !event.KillerName) return EventType.UNSUPPORTED;
        return gameState.playerList.isAlly(event.KillerName, playerRiotId)
            ? EventType.ALLY_VOID_GRUB_KILL
            : EventType.ENEMY_VOID_GRUB_KILL;
    }

    if (event.EventName === 'GameEnd') {
        return event.Result === 'Win' ? EventType.GAME_END_VICTORY : EventType.GAME_END_DEFEAT;
    }

    return EventType.UNSUPPORTED;
}
