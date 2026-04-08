import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {EnemyHeraldKillAnimation} from '../../../src/hud/animations/EnemyHeraldKillAnimation.js';

/**
 * Integration test for enemy herald kill animation on actual hardware.
 */
describe('EnemyHeraldKillAnimation Integration', () => {
    test('playEnemyHeraldKillAnimation', async () => {
        await new AnimationTester().testAnimation(new EnemyHeraldKillAnimation());
    }, 30000);
});
