import {describe, expect, test} from 'vitest';
import {PlayerList} from '../../../../src/rest/playerlist/PlayerList.js';
import type {Player} from '../../../../src/rest/playerlist/Player.js';
import {Team} from '../../../../src/rest/playerlist/Team.js';

const PLAYER_RIOT_ID = 'BonE#EUPL';

const testPlayers: Player[] = [
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

describe('PlayerListResponseHandler', () => {
    test('testPlayerListParsing', () => {
        //when
        const playerList = new PlayerList(testPlayers);

        //then
        verifyPlayerList(playerList);
    });
});

export function verifyPlayerList(playerList: PlayerList): void {
    expect(playerList).toBeDefined();
    expect(playerList.getAllies(PLAYER_RIOT_ID)).toHaveLength(5);
    expect(playerList.getEnemies(PLAYER_RIOT_ID)).toHaveLength(5);

    const activePlayer = playerList.getActivePlayer(PLAYER_RIOT_ID);
    expect(activePlayer.riotId).toBe('BonE#EUPL');
    expect(activePlayer.riotIdGameName).toBe('BonE');
    expect(activePlayer.team).toBe(Team.CHAOS);
    expect(activePlayer.respawnTimer).toBe(4.5);
    expect(playerList.isAlly('Test summoner 5', PLAYER_RIOT_ID)).toBe(true);
    expect(playerList.isAlly('Test summoner 9', PLAYER_RIOT_ID)).toBe(false);
    expect(playerList.getEnemies(PLAYER_RIOT_ID)).toContain('Łążćkiewicz');

    expect(activePlayer.championName).toBe("Cho'Gath");
    expect(activePlayer.riotId).toBe('BonE#EUPL');
    expect(activePlayer.riotIdGameName).toBe('BonE');
    expect(activePlayer.isDead).toBe(true);
    expect(activePlayer.team).toBe(Team.CHAOS);
}
