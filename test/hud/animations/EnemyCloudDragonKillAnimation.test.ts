import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {EnemyCloudDragonKillAnimation} from '../../../src/hud/animations/EnemyCloudDragonKillAnimation.js';

/**
 * Integration test for enemy cloud dragon kill animation on actual hardware.
 */
describe('EnemyCloudDragonKillAnimation Integration', () => {
    test('playEnemyCloudDragonKillAnimation', async () => {
        await new AnimationTester().testAnimation(new EnemyCloudDragonKillAnimation());
    }, 30000);
});
