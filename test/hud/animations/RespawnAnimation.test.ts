import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {GameStateMocks} from '../../GameStateMocks.js';
import {RespawnAnimation} from '../../../src/hud/animations/RespawnAnimation.js';

/**
 * Integration test for respawn animation on actual hardware.
 */
describe('RespawnAnimation Integration', () => {
    test('playRespawnAnimation', async () => {
        // given
        const mocks = new GameStateMocks();

        const championStats = mocks.championStats();
        championStats.resourceMax = 100;
        championStats.resourceValue = 100;
        championStats.currentHealth = 100;
        championStats.maxHealth = 100;

        mocks.playerList();

        // then
        await new AnimationTester().testAnimation(new RespawnAnimation());
    }, 30000);
});
