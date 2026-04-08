import {afterEach, beforeEach, describe, expect, test, vi} from 'vitest';
import {RunningState} from '../../src/state/RunningState.js';
import type {Player} from '../../src/rest/playerlist/Player.js';
import {Team} from '../../src/rest/playerlist/Team.js';

const PLAYER_RIOT_ID = 'BonE#EUPL';

const playerListJson: Player[] = [
    { riotId: 'Łążćkiewicz#TAG1', riotIdGameName: 'Łążćkiewicz', team: Team.ORDER, championName: 'Ryze', respawnTimer: 0, isDead: true },
    { riotId: 'Test summoner 2#TEST', riotIdGameName: 'Test summoner 2', team: Team.ORDER, championName: 'Hecarim', respawnTimer: 0, isDead: false },
    { riotId: 'Test summoner 7#TEST', riotIdGameName: 'Test summoner 7', team: Team.ORDER, championName: 'Yasuo', respawnTimer: 0, isDead: false },
    { riotId: 'Test summoner 8#TEST', riotIdGameName: 'Test summoner 8', team: Team.ORDER, championName: 'Lucian', respawnTimer: 0, isDead: false },
    { riotId: 'Test summoner 9#TEST', riotIdGameName: 'Test summoner 9', team: Team.ORDER, championName: 'Malphite', respawnTimer: 0, isDead: false },
    { riotId: PLAYER_RIOT_ID, riotIdGameName: 'BonE', team: Team.CHAOS, championName: "Cho'Gath", respawnTimer: 4.5, isDead: true },
    { riotId: 'Test summoner 3#TEST', riotIdGameName: 'Test summoner 3', team: Team.CHAOS, championName: 'Amumu', respawnTimer: 0, isDead: false },
    { riotId: 'Test summoner 4#TEST', riotIdGameName: 'Test summoner 4', team: Team.CHAOS, championName: 'Zed', respawnTimer: 0, isDead: false },
    { riotId: 'Test summoner 5#TEST', riotIdGameName: 'Test summoner 5', team: Team.CHAOS, championName: 'Jhin', respawnTimer: 0, isDead: false },
    { riotId: 'Test summoner 6#TEST', riotIdGameName: 'Test summoner 6', team: Team.CHAOS, championName: 'Thresh', respawnTimer: 0, isDead: false },
];

vi.mock('../../src/rest/http/LeagueHttpClients.js', () => ({
    getNonBlockingResponse: vi.fn().mockResolvedValue(playerListJson),
    getRetryingResponse: vi.fn().mockResolvedValue(playerListJson),
    setNonBlockingLeagueHttpClient: vi.fn(),
    setRetryingLeagueHttpClient: vi.fn(),
}));

describe('FetchPlayerListTask', () => {
    beforeEach(() => {
        RunningState.setRunningGame(false);
        RunningState.setRunningGame(true);
        RunningState.getGameState().playerRiotId = PLAYER_RIOT_ID;
    });

    afterEach(() => {
        RunningState.setRunningGame(false);
    });

    test('testPlayerListParsing', async () => {
        //given & when
        const { fetchPlayerListTask } = await import('../../src/tasks/FetchPlayerListTask.js');
        await fetchPlayerListTask();

        //then
        const playerList = RunningState.getGameState().playerList!;
        expect(playerList).toBeDefined();
        expect(playerList.getAllies(PLAYER_RIOT_ID)).toHaveLength(5);
        expect(playerList.getEnemies(PLAYER_RIOT_ID)).toHaveLength(5);
        expect(playerList.getActivePlayer(PLAYER_RIOT_ID).riotId).toBe('BonE#EUPL');
        expect(playerList.getActivePlayer(PLAYER_RIOT_ID).riotIdGameName).toBe('BonE');
        expect(playerList.getActivePlayer(PLAYER_RIOT_ID).team).toBe(Team.CHAOS);
        expect(playerList.getActivePlayer(PLAYER_RIOT_ID).respawnTimer).toBe(4.5);
        expect(playerList.isAlly('Test summoner 5', PLAYER_RIOT_ID)).toBe(true);
        expect(playerList.isAlly('Test summoner 9', PLAYER_RIOT_ID)).toBe(false);
        expect(playerList.getEnemies(PLAYER_RIOT_ID)).toContain('Łążćkiewicz');

        const activePlayer = playerList.getActivePlayer(PLAYER_RIOT_ID);
        expect(activePlayer.championName).toBe("Cho'Gath");
        expect(activePlayer.isDead).toBe(true);
        expect(activePlayer.team).toBe(Team.CHAOS);
    });
});
