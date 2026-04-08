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
        { riotId: 'Enemy Summoner#TEST', riotIdGameName: 'Enemy Summoner', team: Team.ORDER, championName: 'Test', respawnTimer: 0, isDead: false },
    ];
    RunningState.getGameState().playerList = new PlayerList(players);
}

function assertSecondsEqualsWithMargin(expectedSeconds: number, actualSeconds: number): void {
    expect(actualSeconds).toBeGreaterThanOrEqual(expectedSeconds - 2);
    expect(actualSeconds).toBeLessThanOrEqual(expectedSeconds + 2);
}

const elderBuffActiveEvents: Event[] = [
    { EventID: 0, EventName: 'GameStart', EventTime: 0.0563616007566452 },
    { EventID: 1, EventName: 'MinionsSpawning', EventTime: 65.06690979003906 },
    { EventID: 2, EventName: 'DragonKill', EventTime: 100.6826782226563, DragonType: 'Fire', KillerName: PLAYER_RIOT_ID_GAME_NAME, Assisters: [] },
    { EventID: 3, EventName: 'DragonKill', EventTime: 200.6826782226563, DragonType: 'Water', KillerName: PLAYER_RIOT_ID_GAME_NAME, Assisters: [] },
    { EventID: 4, EventName: 'DragonKill', EventTime: 250.6826782226563, DragonType: 'Air', KillerName: PLAYER_RIOT_ID_GAME_NAME, Assisters: [] },
    { EventID: 5, EventName: 'DragonKill', EventTime: 300.6826782226563, DragonType: 'Air', KillerName: PLAYER_RIOT_ID_GAME_NAME, Assisters: [] },
    { EventID: 6, EventName: 'DragonKill', EventTime: 350.6826782226563, DragonType: 'Elder', KillerName: PLAYER_RIOT_ID_GAME_NAME, Assisters: [] },
];

const secondElderBuffActiveEvents: Event[] = [
    ...elderBuffActiveEvents,
    { EventID: 7, EventName: 'DragonKill', EventTime: 700.6826782226563, DragonType: 'Elder', KillerName: PLAYER_RIOT_ID_GAME_NAME, Assisters: [] },
];

const elderBuffInactivePlayerDiedEvents: Event[] = [
    ...elderBuffActiveEvents,
    { EventID: 7, EventName: 'ChampionKill', EventTime: 400.1595916748047, KillerName: 'Enemy Player', VictimName: PLAYER_RIOT_ID_GAME_NAME, Assisters: [] },
];

const elderBuffInactivePlayerWasDeadEvents: Event[] = [
    { EventID: 0, EventName: 'GameStart', EventTime: 0.0563616007566452 },
    { EventID: 1, EventName: 'MinionsSpawning', EventTime: 65.06690979003906 },
    { EventID: 2, EventName: 'DragonKill', EventTime: 100.6826782226563, DragonType: 'Fire', KillerName: PLAYER_RIOT_ID_GAME_NAME, Assisters: [] },
    { EventID: 3, EventName: 'DragonKill', EventTime: 200.6826782226563, DragonType: 'Water', KillerName: PLAYER_RIOT_ID_GAME_NAME, Assisters: [] },
    { EventID: 4, EventName: 'DragonKill', EventTime: 250.6826782226563, DragonType: 'Air', KillerName: PLAYER_RIOT_ID_GAME_NAME, Assisters: [] },
    { EventID: 5, EventName: 'DragonKill', EventTime: 300.6826782226563, DragonType: 'Air', KillerName: PLAYER_RIOT_ID_GAME_NAME, Assisters: [] },
    { EventID: 7, EventName: 'ChampionKill', EventTime: 347.1595916748047, KillerName: 'Enemy Player', VictimName: PLAYER_RIOT_ID_GAME_NAME, Assisters: [] },
    { EventID: 6, EventName: 'DragonKill', EventTime: 350.6826782226563, DragonType: 'Elder', KillerName: 'Ally', Assisters: [] },
];

const enemyElderBuffEvents: Event[] = [
    { EventID: 0, EventName: 'GameStart', EventTime: 0.0563616007566452 },
    { EventID: 1, EventName: 'MinionsSpawning', EventTime: 65.06690979003906 },
    { EventID: 2, EventName: 'DragonKill', EventTime: 100.6826782226563, DragonType: 'Fire', KillerName: 'Enemy Summoner', Assisters: [] },
    { EventID: 3, EventName: 'DragonKill', EventTime: 200.6826782226563, DragonType: 'Water', KillerName: 'Enemy Summoner', Assisters: [] },
    { EventID: 4, EventName: 'DragonKill', EventTime: 250.6826782226563, DragonType: 'Air', KillerName: 'Enemy Summoner', Assisters: [] },
    { EventID: 5, EventName: 'DragonKill', EventTime: 300.6826782226563, DragonType: 'Air', KillerName: 'Enemy Summoner', Assisters: [] },
    { EventID: 6, EventName: 'DragonKill', EventTime: 350.6826782226563, DragonType: 'Elder', KillerName: 'Enemy Summoner', Assisters: [] },
];

describe('EventDataProcessorElderBuff', () => {
    beforeEach(() => {
        setupMocks();
    });

    test('testElderBuffActive', () => {
        //given
        RunningState.getGameState().gameStats = { gameMode: 'CLASSIC', gameTime: 400.0, mapTerrain: 'Default' };

        //when
        collectUnprocessedEvents(elderBuffActiveEvents);

        //then
        const eventData = RunningState.getGameState().eventData;
        const gameTime = RunningState.getGameState().gameStats!.gameTime;
        assertSecondsEqualsWithMargin(
            GameStateHelper.FIRST_ELDER_TIME,
            Math.trunc(50 + eventData.elderBuffEnd! - gameTime),
        );
        expect(eventData.totalEldersKilled).toBe(1);
        expect(GameStateHelper.hasElderBuff()).toBe(true);
        expect(eventData.getKilledDragons()).toHaveLength(4);
    });

    test('testSecondElderBuffActive', () => {
        //given
        RunningState.getGameState().gameStats = { gameMode: 'CLASSIC', gameTime: 750.0, mapTerrain: 'Default' };

        //when
        collectUnprocessedEvents(secondElderBuffActiveEvents);

        //then
        const eventData = RunningState.getGameState().eventData;
        const gameTime = RunningState.getGameState().gameStats!.gameTime;
        assertSecondsEqualsWithMargin(
            GameStateHelper.NEXT_ELDER_TIME,
            Math.trunc(50 + eventData.elderBuffEnd! - gameTime),
        );
        expect(eventData.totalEldersKilled).toBe(2);
        expect(GameStateHelper.hasElderBuff()).toBe(true);
        expect(eventData.getKilledDragons()).toHaveLength(4);
    });

    test('testElderBuffInactiveCauseExpired', () => {
        //given
        RunningState.getGameState().gameStats = { gameMode: 'CLASSIC', gameTime: 600.0, mapTerrain: 'Default' };

        //when
        collectUnprocessedEvents(elderBuffActiveEvents);

        //then
        const eventData = RunningState.getGameState().eventData;
        expect(eventData.elderBuffEnd).toBeNull();
        expect(eventData.totalEldersKilled).toBe(1);
        expect(GameStateHelper.hasElderBuff()).toBe(false);
        expect(eventData.getKilledDragons()).toHaveLength(4);
    });

    test('testElderBuffInactiveIfPlayerWasDead', () => {
        //given
        RunningState.getGameState().gameStats = { gameMode: 'CLASSIC', gameTime: 400.0, mapTerrain: 'Default' };
        RunningState.getGameState().activePlayer!.level = 6;

        //when
        collectUnprocessedEvents(elderBuffInactivePlayerWasDeadEvents);

        //then
        const eventData = RunningState.getGameState().eventData;
        expect(eventData.elderBuffEnd).toBeNull();
        expect(eventData.totalEldersKilled).toBe(1);
        expect(GameStateHelper.hasElderBuff()).toBe(false);
        expect(eventData.getKilledDragons()).toHaveLength(4);
    });

    test('testElderBuffInactiveIfPlayerDied', () => {
        //given
        RunningState.getGameState().gameStats = { gameMode: 'CLASSIC', gameTime: 450.0, mapTerrain: 'Default' };
        RunningState.getGameState().activePlayer!.level = 6;

        //when
        collectUnprocessedEvents(elderBuffInactivePlayerDiedEvents);

        //then
        const eventData = RunningState.getGameState().eventData;
        expect(eventData.elderBuffEnd).toBeNull();
        expect(eventData.totalEldersKilled).toBe(1);
        expect(GameStateHelper.hasElderBuff()).toBe(false);
        expect(eventData.getKilledDragons()).toHaveLength(4);
    });

    test('testElderBuffInactiveCauseEnemyKilled', () => {
        //given
        RunningState.getGameState().gameStats = { gameMode: 'CLASSIC', gameTime: 400.0, mapTerrain: 'Default' };

        //when
        collectUnprocessedEvents(enemyElderBuffEvents);

        //then
        const eventData = RunningState.getGameState().eventData;
        expect(eventData.elderBuffEnd).toBeNull();
        expect(eventData.totalEldersKilled).toBe(1);
        expect(GameStateHelper.hasElderBuff()).toBe(false);
        expect(eventData.getKilledDragons()).toHaveLength(0);
    });
});
