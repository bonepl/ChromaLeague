import {afterEach, beforeEach, describe, expect, test} from 'vitest';
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
        level: 6,
        championStats: { attackRange: 125, currentHealth: 100, maxHealth: 100, resourceMax: 100, resourceValue: 100, resourceType: 'MANA' },
    };
    RunningState.getGameState().gameStats = { gameMode: 'CLASSIC', gameTime: 1400.0, mapTerrain: 'Default' };

    const players: Player[] = [
        { riotId: PLAYER_RIOT_ID, riotIdGameName: PLAYER_RIOT_ID_GAME_NAME, team: Team.CHAOS, championName: "Cho'Gath", respawnTimer: 0, isDead: false },
        { riotId: 'BooonE#TEST', riotIdGameName: 'BooonE', team: Team.CHAOS, championName: 'Test', respawnTimer: 0, isDead: false },
        { riotId: 'Warwick Bot#TEST', riotIdGameName: 'Warwick Bot', team: Team.ORDER, championName: 'Warwick', respawnTimer: 0, isDead: false },
    ];
    RunningState.getGameState().playerList = new PlayerList(players);
}

const standardEvents: Event[] = [
    { EventID: 0, EventName: 'GameStart', EventTime: 0.0563616007566452 },
    { EventID: 1, EventName: 'MinionsSpawning', EventTime: 5.06690979003906 },
    { EventID: 2, EventName: 'ChampionKill', EventTime: 7.1595916748047, KillerName: 'BooonE', VictimName: 'Warwick Bot', Assisters: [] },
];

const gameStartEvent: Event[] = [
    { EventID: 0, EventName: 'GameStart', EventTime: 0.0563616007566452 },
];

describe('FetchNewEventsTask', () => {
    beforeEach(() => {
        setupMocks();
    });

    afterEach(() => {
        RunningState.setRunningGame(false);
    });

    test('testEventParsing', () => {
        //when
        collectUnprocessedEvents(standardEvents);

        //then
        const events = RunningState.getGameState().eventData.getProcessedEvents();
        expect(events).toHaveLength(3);

        expect(events[0].EventID).toBe(0);
        expect(events[0].EventName).toBe('GameStart');
        expect(events[0].EventTime).toBe(0.0563616007566452);

        expect(events[1].EventID).toBe(1);
        expect(events[1].EventName).toBe('MinionsSpawning');
        expect(events[1].EventTime).toBe(5.06690979003906);

        expect(events[2].EventID).toBe(2);
        expect(events[2].EventName).toBe('ChampionKill');
        expect(events[2].EventTime).toBe(7.1595916748047);
    });

    test('testEventParsingAfterReconnect', () => {
        //given - full event set from eventdata.json
        const events: Event[] = [
            { EventID: 0, EventName: 'GameStart', EventTime: 0.0563616007566452 },
            { EventID: 1, EventName: 'MinionsSpawning', EventTime: 65.06690979003906 },
            { EventID: 2, EventName: 'ChampionKill', EventTime: 230.1595916748047, KillerName: 'BooonE', VictimName: 'Warwick Bot', Assisters: [] },
            { EventID: 3, EventName: 'FirstBlood', EventTime: 230.1595916748047 },
            { EventID: 4, EventName: 'Ace', EventTime: 230.1595916748047 },
            { EventID: 5, EventName: 'TurretKilled', EventTime: 907.0410766601563, KillerName: 'Warwick Bot', Assisters: [] },
            { EventID: 6, EventName: 'FirstBrick', EventTime: 907.0410766601563 },
            { EventID: 7, EventName: 'DragonKill', EventTime: 933.6826782226563, DragonType: 'Fire', KillerName: 'BooonE', Assisters: [] },
            { EventID: 8, EventName: 'HeraldKill', EventTime: 982.7305908203125, KillerName: 'BooonE', Assisters: [] },
            { EventID: 9, EventName: 'TurretKilled', EventTime: 1027.162841796875, KillerName: 'BooonE', Assisters: [] },
            { EventID: 10, EventName: 'TurretKilled', EventTime: 1048.6292724609376, KillerName: 'Minion', Assisters: [] },
            { EventID: 11, EventName: 'TurretKilled', EventTime: 1053.9127197265626, KillerName: 'BooonE', Assisters: [] },
            { EventID: 12, EventName: 'TurretKilled', EventTime: 1087.5941162109376, KillerName: 'BooonE', Assisters: [] },
            { EventID: 13, EventName: 'InhibKilled', EventTime: 1105.1768798828126 },
            { EventID: 14, EventName: 'ChampionKill', EventTime: 1118.4588623046876, KillerName: 'BooonE', VictimName: 'Warwick Bot', Assisters: [] },
            { EventID: 15, EventName: 'Ace', EventTime: 1118.4588623046876 },
            { EventID: 16, EventName: 'TurretKilled', EventTime: 1144.8411865234376, KillerName: 'BooonE', Assisters: [] },
            { EventID: 17, EventName: 'TurretKilled', EventTime: 1160.109130859375, KillerName: 'BooonE', Assisters: [] },
            { EventID: 18, EventName: 'ChampionKill', EventTime: 1172.0419921875, KillerName: 'Warwick Bot', VictimName: 'BooonE', Assisters: [] },
            { EventID: 19, EventName: 'Ace', EventTime: 1172.0419921875 },
            { EventID: 20, EventName: 'TurretKilled', EventTime: 1244.9444580078126, KillerName: 'BooonE', Assisters: [] },
            { EventID: 21, EventName: 'ChampionKill', EventTime: 1253.8250732421876, KillerName: 'Turret', VictimName: 'BooonE', Assisters: [] },
            { EventID: 22, EventName: 'TurretKilled', EventTime: 1259.8662109375, KillerName: 'Minion', Assisters: [] },
            { EventID: 23, EventName: 'TurretKilled', EventTime: 1319.2762451171876, KillerName: 'Minion', Assisters: [] },
            { EventID: 24, EventName: 'InhibKilled', EventTime: 1349.1202392578126 },
            { EventID: 25, EventName: 'BaronKill', EventTime: 1357.689453125, KillerName: 'BooonE', Assisters: [] },
            { EventID: 26, EventName: 'GameEnd', EventTime: 1375.343994140625, Result: 'Win' },
        ];

        //when
        collectUnprocessedEvents(events);
        collectUnprocessedEvents(events);

        //then
        const processedEvents = [...RunningState.getGameState().eventData.getProcessedEvents()];
        expect(processedEvents).toHaveLength(27);
        expect(processedEvents[0].EventID).toBe(0);
        expect(processedEvents[0].EventName).toBe('GameStart');
        expect(processedEvents[0].EventTime).toBe(0.0563616007566452);
    });

    test('testFirstEventParsing', () => {
        //when
        collectUnprocessedEvents(gameStartEvent);

        //then
        const events = [...RunningState.getGameState().eventData.getProcessedEvents()];
        expect(events).toHaveLength(1);
        expect(events[0].EventID).toBe(0);
        expect(events[0].EventName).toBe('GameStart');
        expect(events[0].EventTime).toBe(0.0563616007566452);
    });
});
