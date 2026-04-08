import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {EnemyVoidGrubKillAnimation} from '../../../src/hud/animations/EnemyVoidGrubKillAnimation.js';

/**
 * Integration test for enemy void grub kill animation on actual hardware.
 */
describe('EnemyVoidGrubKillAnimation Integration', () => {
    test('playEnemyVoidGrubKillAnimation', async () => {
        await new AnimationTester().testAnimation(new EnemyVoidGrubKillAnimation());
    }, 30000);
});
