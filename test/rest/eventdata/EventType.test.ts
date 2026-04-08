import {beforeEach, describe, expect, test} from 'vitest';
import {EventType, eventTypeFromEvent} from '../../../src/rest/eventdata/EventType.js';
import {DragonType} from '../../../src/rest/eventdata/DragonType.js';
import type {Event} from '../../../src/rest/eventdata/Event.js';
import {RunningState} from '../../../src/state/RunningState.js';
import {PlayerList} from '../../../src/rest/playerlist/PlayerList.js';
import type {Player} from '../../../src/rest/playerlist/Player.js';
import {Team} from '../../../src/rest/playerlist/Team.js';

const PLAYER_RIOT_ID = 'BonE#EUPL';
const PLAYER_RIOT_ID_GAME_NAME = 'BonE';

function setupGameState(isAlly: boolean): void {
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

    const allyPlayers: Player[] = [
        { riotId: PLAYER_RIOT_ID, riotIdGameName: PLAYER_RIOT_ID_GAME_NAME, team: Team.CHAOS, championName: "Cho'Gath", respawnTimer: 0, isDead: false },
        { riotId: 'BooonE#TEST', riotIdGameName: 'BooonE', team: isAlly ? Team.CHAOS : Team.ORDER, championName: 'Test', respawnTimer: 0, isDead: false },
    ];
    RunningState.getGameState().playerList = new PlayerList(allyPlayers);
}

function dragonEvent(dragonApiType: string): Event {
    return { EventID: 7, EventName: 'DragonKill', EventTime: 933, DragonType: dragonApiType, KillerName: 'BooonE' };
}

describe('EventTypeTest', () => {
    beforeEach(() => {
        RunningState.setRunningGame(false);
        RunningState.setRunningGame(true);
    });

    const dragonToExpectedTypeArguments: [DragonType, string, EventType, boolean][] = [
        [DragonType.OCEAN, 'Water', EventType.ALLY_OCEAN_DRAGON_KILL, true],
        [DragonType.MOUNTAIN, 'Earth', EventType.ALLY_MOUNTAIN_DRAGON_KILL, true],
        [DragonType.INFERNAL, 'Fire', EventType.ALLY_INFERNAL_DRAGON_KILL, true],
        [DragonType.CLOUD, 'Air', EventType.ALLY_CLOUD_DRAGON_KILL, true],
        [DragonType.CHEMTECH, 'Chemtech', EventType.ALLY_CHEMTECH_DRAGON_KILL, true],
        [DragonType.HEXTECH, 'Hextech', EventType.ALLY_HEXTECH_DRAGON_KILL, true],
        [DragonType.ELDER, 'Elder', EventType.ALLY_ELDER_DRAGON_KILL, true],
        [DragonType.OCEAN, 'Water', EventType.ENEMY_OCEAN_DRAGON_KILL, false],
        [DragonType.MOUNTAIN, 'Earth', EventType.ENEMY_MOUNTAIN_DRAGON_KILL, false],
        [DragonType.INFERNAL, 'Fire', EventType.ENEMY_INFERNAL_DRAGON_KILL, false],
        [DragonType.CLOUD, 'Air', EventType.ENEMY_CLOUD_DRAGON_KILL, false],
        [DragonType.CHEMTECH, 'Chemtech', EventType.ENEMY_CHEMTECH_DRAGON_KILL, false],
        [DragonType.HEXTECH, 'Hextech', EventType.ENEMY_HEXTECH_DRAGON_KILL, false],
        [DragonType.ELDER, 'Elder', EventType.ENEMY_ELDER_DRAGON_KILL, false],
    ];

    test.each(dragonToExpectedTypeArguments)(
        '%s -> %s (allied=%s)',
        (_dragonType, apiType, expectedEventType, alliedKill) => {
            //given
            setupGameState(alliedKill);
            const event = dragonEvent(apiType);

            //when
            const eventType = eventTypeFromEvent(event);

            //then
            expect(eventType).toBe(expectedEventType);
        },
    );

    test('testActivePlayerKill', () => {
        //given
        setupGameState(true);
        const event: Event = { EventID: 14, EventName: 'ChampionKill', EventTime: 1118, KillerName: PLAYER_RIOT_ID_GAME_NAME, VictimName: 'Warwick Bot', Assisters: [] };

        //when
        const eventType = eventTypeFromEvent(event);

        //then
        expect(eventType).toBe(EventType.ACTIVE_PLAYER_KILL);
    });

    test('testActivePlayerAssist', () => {
        //given
        setupGameState(true);
        const event: Event = { EventID: 14, EventName: 'ChampionKill', EventTime: 1118, KillerName: 'Some Champ', VictimName: 'Warwick Bot', Assisters: [PLAYER_RIOT_ID_GAME_NAME] };

        //when
        const eventType = eventTypeFromEvent(event);

        //then
        expect(eventType).toBe(EventType.ACTIVE_PLAYER_ASSIST);
    });

    test('testAllyHeraldKillEvent', () => {
        //given
        setupGameState(true);
        const event: Event = { EventID: 7, EventName: 'HeraldKill', EventTime: 933, KillerName: 'BooonE' };

        //when
        const eventType = eventTypeFromEvent(event);

        //then
        expect(eventType).toBe(EventType.ALLY_HERALD_KILL);
    });

    test('testEnemyHeraldKillEvent', () => {
        //given
        setupGameState(false);
        const event: Event = { EventID: 7, EventName: 'HeraldKill', EventTime: 933, KillerName: 'BooonE' };

        //when
        const eventType = eventTypeFromEvent(event);

        //then
        expect(eventType).toBe(EventType.ENEMY_HERALD_KILL);
    });

    test('testAllyBaronKillEvent', () => {
        //given
        setupGameState(true);
        const event: Event = { EventID: 7, EventName: 'BaronKill', EventTime: 933, KillerName: 'BooonE' };

        //when
        const eventType = eventTypeFromEvent(event);

        //then
        expect(eventType).toBe(EventType.ALLY_BARON_KILL);
    });

    test('testEnemyBaronKillEvent', () => {
        //given
        setupGameState(false);
        const event: Event = { EventID: 7, EventName: 'BaronKill', EventTime: 933, KillerName: 'BooonE' };

        //when
        const eventType = eventTypeFromEvent(event);

        //then
        expect(eventType).toBe(EventType.ENEMY_BARON_KILL);
    });

    test('testGameEndWinEvent', () => {
        //given
        setupGameState(true);
        const event: Event = { EventID: 26, EventName: 'GameEnd', EventTime: 1375, Result: 'Win' };

        //when
        const eventType = eventTypeFromEvent(event);

        //then
        expect(eventType).toBe(EventType.GAME_END_VICTORY);
    });

    test('testGameEndLoseEvent', () => {
        //given
        setupGameState(true);
        const event: Event = { EventID: 26, EventName: 'GameEnd', EventTime: 1375, Result: 'Lose' };

        //when
        const eventType = eventTypeFromEvent(event);

        //then
        expect(eventType).toBe(EventType.GAME_END_DEFEAT);
    });
});
