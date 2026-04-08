import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {EnemyChemtechDragonKillAnimation} from '../../../src/hud/animations/EnemyChemtechDragonKillAnimation.js';

/**
 * Integration test for enemy chemtech dragon kill animation on actual hardware.
 */
describe('EnemyChemtechDragonKillAnimation Integration', () => {
    test('playEnemyChemtechDragonKillAnimation', async () => {
        await new AnimationTester().testAnimation(new EnemyChemtechDragonKillAnimation());
    }, 30000);
});
