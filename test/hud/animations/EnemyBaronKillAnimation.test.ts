import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {EnemyBaronKillAnimation} from '../../../src/hud/animations/EnemyBaronKillAnimation.js';

/**
 * Integration test for enemy baron kill animation on actual hardware.
 */
describe('EnemyBaronKillAnimation Integration', () => {
    test('playEnemyBaronKillAnimation', async () => {
        await new AnimationTester().testAnimation(new EnemyBaronKillAnimation());
    }, 30000);
});
