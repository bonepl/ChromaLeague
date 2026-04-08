import {describe, expect, test} from 'vitest';
import type {GameStats} from '../../../../src/rest/gamestats/GameStats.js';

describe('GameStatsResponseHandler', () => {
    test('testGameStatsParsing', () => {
        //given
        const json = {
            gameMode: 'CLASSIC',
            gameTime: 15.123456789,
            mapName: 'Map11',
            mapNumber: 11,
            mapTerrain: 'Infernal',
        };

        //when
        const gameStats = json as GameStats;

        //then
        expect(gameStats).toBeDefined();
        verifyGameStats(gameStats);
    });
});

export function verifyGameStats(gameStats: GameStats): void {
    expect(gameStats).toBeDefined();
    expect(gameStats.gameMode).toBe('CLASSIC');
    expect(gameStats.gameTime).toBe(15.123456789);
    expect(gameStats.mapTerrain).toBe('Infernal');
}
