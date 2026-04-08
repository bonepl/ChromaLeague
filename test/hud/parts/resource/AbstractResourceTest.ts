import {beforeEach} from 'vitest';
import {GameStateMocks} from '../../../GameStateMocks.js';
import {ChampionStats} from '../../../../src/rest/activeplayer/ChampionStats.js';

export let gameStateMocks: GameStateMocks;

export function setupResourceTest(): void {
    beforeEach(() => {
        gameStateMocks = new GameStateMocks();
    });
}

export function mockResource(resource: number, maxResource: number): void {
    const stats: ChampionStats = gameStateMocks.championStats();
    stats.resourceValue = resource;
    stats.resourceMax = maxResource;
}

export function mockRange(range: number): void {
    const stats: ChampionStats = gameStateMocks.championStats();
    stats.attackRange = range;
}
