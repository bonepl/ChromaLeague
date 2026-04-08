import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {EnemyElderDragonKillAnimation} from '../../../src/hud/animations/EnemyElderDragonKillAnimation.js';

/**
 * Integration test for enemy elder dragon kill animation on actual hardware.
 */
describe('EnemyElderDragonKillAnimation Integration', () => {
    test('playEnemyElderDragonKillAnimation', async () => {
        await new AnimationTester().testAnimation(new EnemyElderDragonKillAnimation());
    }, 30000);
});
