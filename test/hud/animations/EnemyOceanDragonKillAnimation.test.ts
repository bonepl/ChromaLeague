import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {EnemyOceanDragonKillAnimation} from '../../../src/hud/animations/EnemyOceanDragonKillAnimation.js';

/**
 * Integration test for enemy ocean dragon kill animation on actual hardware.
 */
describe('EnemyOceanDragonKillAnimation Integration', () => {
    test('playEnemyOceanDragonKillAnimation', async () => {
        await new AnimationTester().testAnimation(new EnemyOceanDragonKillAnimation());
    }, 30000);
});
