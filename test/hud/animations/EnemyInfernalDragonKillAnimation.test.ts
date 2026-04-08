import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {EnemyInfernalDragonKillAnimation} from '../../../src/hud/animations/EnemyInfernalDragonKillAnimation.js';

/**
 * Integration test for enemy infernal dragon kill animation on actual hardware.
 */
describe('EnemyInfernalDragonKillAnimation Integration', () => {
    test('playEnemyInfernalDragonKillAnimation', async () => {
        await new AnimationTester().testAnimation(new EnemyInfernalDragonKillAnimation());
    }, 30000);
});
