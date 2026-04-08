import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {EnemyHextechDragonKillAnimation} from '../../../src/hud/animations/EnemyHextechDragonKillAnimation.js';

/**
 * Integration test for enemy hextech dragon kill animation on actual hardware.
 */
describe('EnemyHextechDragonKillAnimation Integration', () => {
    test('playEnemyHextechDragonKillAnimation', async () => {
        await new AnimationTester().testAnimation(new EnemyHextechDragonKillAnimation());
    }, 30000);
});
