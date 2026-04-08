import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {GameStateMocks} from '../../GameStateMocks.js';
import {RiftAnimation} from '../../../src/hud/animations/RiftAnimation.js';

const MAP_TERRAINS = ['Chemtech', 'Cloud', 'Hextech', 'Infernal', 'Mountain', 'Ocean'] as const;

/**
 * Integration test for rift terrain animations on actual hardware.
 */
describe('RiftAnimation Integration', () => {
    test.each(MAP_TERRAINS)('playRiftAnimation - %s', async (mapTerrain) => {
        // given
        const gameStats = new GameStateMocks().gameStats();
        gameStats.mapTerrain = mapTerrain;

        // then
        await new AnimationTester().testAnimation(new RiftAnimation());
    }, 30000);
});
