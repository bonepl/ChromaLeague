import {beforeEach, describe, expect, test} from 'vitest';
import {RunningState} from '../../src/state/RunningState.js';
import {collectUnprocessedEvents} from '../../src/tasks/FetchNewEventsTask.js';
import * as GameStateHelper from '../../src/state/GameStateHelper.js';
import type {Event} from '../../src/rest/eventdata/Event.js';
import {PlayerList} from '../../src/rest/playerlist/PlayerList.js';
import type {Player} from '../../src/rest/playerlist/Player.js';
import {Team} from '../../src/rest/playerlist/Team.js';

const PLAYER_RIOT_ID = 'BonE#EUPL';
const PLAYER_RIOT_ID_GAME_NAME = 'BonE';

function setupMocks(): void {
    RunningState.setRunningGame(false);
    RunningState.setRunningGame(true);
    RunningState.getGameState().playerRiotId = PLAYER_RIOT_ID;
    RunningState.getGameState().activePlayer = {
        riotId: PLAYER_RIOT_ID,
        riotIdGameName: PLAYER_RIOT_ID_GAME_NAME,
        currentGold: 0,
        level: 1,
        championStats: { attackRange: 125, currentHealth: 100, maxHealth: 100, resourceMax: 100, resourceValue: 100, resourceType: 'MANA' },
    };

    const players: Player[] = [
        { riotId: PLAYER_RIOT_ID, riotIdGameName: PLAYER_RIOT_ID_GAME_NAME, team: Team.CHAOS, championName: "Cho'Gath", respawnTimer: 0, isDead: false },
        { riotId: 'Ally#TEST', riotIdGameName: 'Ally', team: Team.CHAOS, championName: 'Test', respawnTimer: 0, isDead: false },
        { riotId: 'Enemy Player#TEST', riotIdGameName: 'Enemy Player', team: Team.ORDER, championName: 'Test', respawnTimer: 0, isDead: false },
        { riotId: 'Enemy Champion#TEST', riotIdGameName: 'Enemy Champion', team: Team.ORDER, championName: 'Test', respawnTimer: 0, isDead: false },
    ];
    RunningState.getGameState().playerList = new PlayerList(players);
}

function assertSecondsEqualsWithMargin(expectedSeconds: number, actualSeconds: number): void {
    expect(actualSeconds).toBeGreaterThanOrEqual(expectedSeconds - 2);
    expect(actualSeconds).toBeLessThanOrEqual(expectedSeconds + 2);
}

const baronBuffActiveEvents: Event[] = [
    { EventID: 0, EventName: 'GameStart', EventTime: 0.0563616007566452 },
    { EventID: 1, EventName: 'MinionsSpawning', EventTime: 65.06690979003906 },
    { EventID: 2, EventName: 'BaronKill', EventTime: 200.689453125, KillerName: PLAYER_RIOT_ID_GAME_NAME, Assisters: [] },
];

const baronBuffInactivePlayerDiedEvents: Event[] = [
    { EventID: 0, EventName: 'GameStart', EventTime: 0.0563616007566452 },
    { EventID: 1, EventName: 'MinionsSpawning', EventTime: 65.06690979003906 },
    { EventID: 2, EventName: 'BaronKill', EventTime: 200.689453125, KillerName: PLAYER_RIOT_ID_GAME_NAME, Assisters: [] },
    { EventID: 3, EventName: 'ChampionKill', EventTime: 250.1595916748047, KillerName: 'Enemy Player', VictimName: PLAYER_RIOT_ID_GAME_NAME, Assisters: [] },
];

const baronBuffInactivePlayerWasDeadEvents: Event[] = [
    { EventID: 0, EventName: 'GameStart', EventTime: 0.0563616007566452 },
    { EventID: 1, EventName: 'MinionsSpawning', EventTime: 65.06690979003906 },
    { EventID: 2, EventName: 'ChampionKill', EventTime: 247.1595916748047, KillerName: 'Enemy Player', VictimName: PLAYER_RIOT_ID_GAME_NAME, Assisters: [] },
    { EventID: 3, EventName: 'BaronKill', EventTime: 250.689453125, KillerName: 'Ally', Assisters: [] },
];

const enemyBaronBuffEvents: Event[] = [
    { EventID: 0, EventName: 'GameStart', EventTime: 0.0563616007566452 },
    { EventID: 1, EventName: 'MinionsSpawning', EventTime: 65.06690979003906 },
    { EventID: 2, EventName: 'BaronKill', EventTime: 200.689453125, KillerName: 'Enemy Champion', Assisters: [] },
];

describe('EventDataProcessorBaronBuff', () => {
    beforeEach(() => {
        setupMocks();
    });

    test('testBaronBuffActive', () => {
        //given
        RunningState.getGameState().gameStats = { gameMode: 'CLASSIC', gameTime: 250.0, mapTerrain: 'Default' };

        //when
        collectUnprocessedEvents(baronBuffActiveEvents);

        //then
        const eventData = RunningState.getGameState().eventData;
        const gameTime = RunningState.getGameState().gameStats!.gameTime;
        assertSecondsEqualsWithMargin(
            GameStateHelper.BARON_TIME,
            Math.trunc(50 + eventData.baronBuffEnd! - gameTime),
        );
        expect(GameStateHelper.hasBaronBuff()).toBe(true);
    });

    test('testBaronBuffInactiveCauseExpired', () => {
        //given
        RunningState.getGameState().gameStats = { gameMode: 'CLASSIC', gameTime: 400.0, mapTerrain: 'Default' };

        //when
        collectUnprocessedEvents(baronBuffActiveEvents);

        //then
        const eventData = RunningState.getGameState().eventData;
        expect(eventData.baronBuffEnd).toBeNull();
        expect(GameStateHelper.hasBaronBuff()).toBe(false);
    });

    test('testBaronBuffInactiveIfPlayerWasDead', () => {
        //given
        RunningState.getGameState().gameStats = { gameMode: 'CLASSIC', gameTime: 300.0, mapTerrain: 'Default' };
        RunningState.getGameState().activePlayer!.level = 6;

        //when
        collectUnprocessedEvents(baronBuffInactivePlayerWasDeadEvents);

        //then
        const eventData = RunningState.getGameState().eventData;
        expect(eventData.baronBuffEnd).toBeNull();
        expect(GameStateHelper.hasBaronBuff()).toBe(false);
    });

    test('testBaronBuffInactiveIfPlayerDied', () => {
        //given
        RunningState.getGameState().gameStats = { gameMode: 'CLASSIC', gameTime: 300.0, mapTerrain: 'Default' };
        RunningState.getGameState().activePlayer!.level = 6;

        //when
        collectUnprocessedEvents(baronBuffInactivePlayerDiedEvents);

        //then
        const eventData = RunningState.getGameState().eventData;
        expect(eventData.baronBuffEnd).toBeNull();
        expect(GameStateHelper.hasBaronBuff()).toBe(false);
    });

    test('testBaronBuffInactiveCauseEnemyKilled', () => {
        //given
        RunningState.getGameState().gameStats = { gameMode: 'CLASSIC', gameTime: 250.0, mapTerrain: 'Default' };

        //when
        collectUnprocessedEvents(enemyBaronBuffEvents);

        //then
        const eventData = RunningState.getGameState().eventData;
        expect(eventData.baronBuffEnd).toBeNull();
        expect(GameStateHelper.hasBaronBuff()).toBe(false);
    });
});
