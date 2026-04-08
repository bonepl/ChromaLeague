import {beforeEach, describe, expect, test} from 'vitest';
import {RunningState} from '../../src/state/RunningState.js';
import {collectUnprocessedEvents} from '../../src/tasks/FetchNewEventsTask.js';
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
        level: 5,
        championStats: { attackRange: 125, currentHealth: 100, maxHealth: 100, resourceMax: 100, resourceValue: 100, resourceType: 'MANA' },
    };
    RunningState.getGameState().gameStats = { gameMode: 'CLASSIC', gameTime: 320.0, mapTerrain: 'Default' };

    const players: Player[] = [
        { riotId: PLAYER_RIOT_ID, riotIdGameName: PLAYER_RIOT_ID_GAME_NAME, team: Team.CHAOS, championName: "Cho'Gath", respawnTimer: 0, isDead: false },
        { riotId: 'Ally#TEST', riotIdGameName: 'Ally', team: Team.CHAOS, championName: 'Test', respawnTimer: 0, isDead: false },
        { riotId: 'Enemy#TEST', riotIdGameName: 'Enemy', team: Team.ORDER, championName: 'Test', respawnTimer: 0, isDead: false },
    ];
    RunningState.getGameState().playerList = new PlayerList(players);
}

const killsAssistsEvents: Event[] = [
    { EventID: 0, EventName: 'GameStart', EventTime: 0.0563616007566452 },
    { EventID: 1, EventName: 'MinionsSpawning', EventTime: 65.06690979003906 },
    { EventID: 2, EventName: 'ChampionKill', EventTime: 100.8250732421876, KillerName: PLAYER_RIOT_ID_GAME_NAME, VictimName: 'Enemy', Assisters: [] },
    { EventID: 3, EventName: 'ChampionKill', EventTime: 120.8250732421876, KillerName: 'Ally', VictimName: 'Enemy', Assisters: [PLAYER_RIOT_ID_GAME_NAME] },
    { EventID: 4, EventName: 'ChampionKill', EventTime: 150.8250732421876, KillerName: 'Ally', VictimName: 'Enemy', Assisters: [PLAYER_RIOT_ID_GAME_NAME] },
    { EventID: 5, EventName: 'ChampionKill', EventTime: 200.8250732421876, KillerName: 'Enemy', VictimName: PLAYER_RIOT_ID_GAME_NAME, Assisters: [] },
    { EventID: 6, EventName: 'ChampionKill', EventTime: 220.8250732421876, KillerName: PLAYER_RIOT_ID_GAME_NAME, VictimName: 'Enemy', Assisters: [] },
    { EventID: 7, EventName: 'ChampionKill', EventTime: 250.8250732421876, KillerName: 'Ally', VictimName: 'Enemy', Assisters: [PLAYER_RIOT_ID_GAME_NAME] },
    { EventID: 8, EventName: 'ChampionKill', EventTime: 300.8250732421876, KillerName: 'Ally', VictimName: 'Enemy', Assisters: [PLAYER_RIOT_ID_GAME_NAME] },
];

const enemyKillsAssistsEvents: Event[] = [
    { EventID: 0, EventName: 'GameStart', EventTime: 0.0563616007566452 },
    { EventID: 1, EventName: 'MinionsSpawning', EventTime: 65.06690979003906 },
    { EventID: 2, EventName: 'ChampionKill', EventTime: 100.8250732421876, KillerName: 'Enemy', VictimName: 'Ally', Assisters: [] },
    { EventID: 3, EventName: 'ChampionKill', EventTime: 120.8250732421876, KillerName: 'Enemy', VictimName: 'Ally', Assisters: ['Enemy'] },
];

describe('EventDataProcessorKillsAssists', () => {
    beforeEach(() => {
        setupMocks();
    });

    test('testKillsAndAssists', () => {
        //when
        collectUnprocessedEvents(killsAssistsEvents);

        //then
        const eventData = RunningState.getGameState().eventData;
        expect(eventData.activePlayerAssistSpree).toBe(2);
        expect(eventData.activePlayerKillingSpree).toBe(1);
    });

    test('testEnemyKillsAndAssists', () => {
        //when
        collectUnprocessedEvents(enemyKillsAssistsEvents);

        //then
        const eventData = RunningState.getGameState().eventData;
        expect(eventData.activePlayerAssistSpree).toBe(0);
        expect(eventData.activePlayerKillingSpree).toBe(0);
    });
});
