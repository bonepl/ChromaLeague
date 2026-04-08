import {describe, expect, test} from 'vitest';
import {MapTerrain, mapTerrainFromApiType} from '../../../src/rest/gamestats/MapTerrain.js';

describe('MapTerrainTest', () => {
    const gameStatsToMapTerrains: [string, MapTerrain][] = [
        ['Chemtech', MapTerrain.CHEMTECH],
        ['Cloud', MapTerrain.CLOUD],
        ['Hextech', MapTerrain.HEXTECH],
        ['Infernal', MapTerrain.INFERNAL],
        ['Mountain', MapTerrain.MOUNTAIN],
        ['Ocean', MapTerrain.OCEAN],
    ];

    test.each(gameStatsToMapTerrains)(
        'shouldParseMapTerrain %s -> %s',
        (apiType, expectedMapTerrain) => {
            //when
            const result = mapTerrainFromApiType(apiType);

            //then
            expect(result).toBe(expectedMapTerrain);
        },
    );
});
