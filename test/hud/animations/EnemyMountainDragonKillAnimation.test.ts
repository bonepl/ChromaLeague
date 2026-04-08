import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {EnemyMountainDragonKillAnimation} from '../../../src/hud/animations/EnemyMountainDragonKillAnimation.js';

/**
 * Integration test for enemy mountain dragon kill animation on actual hardware.
 */
describe('EnemyMountainDragonKillAnimation Integration', () => {
    test('playEnemyMountainDragonKillAnimation', async () => {
        await new AnimationTester().testAnimation(new EnemyMountainDragonKillAnimation());
    }, 30000);
});
