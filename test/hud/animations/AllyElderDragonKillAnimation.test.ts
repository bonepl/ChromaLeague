import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {AllyElderDragonKillAnimation} from '../../../src/hud/animations/AllyElderDragonKillAnimation.js';

/**
 * Integration test for ally elder dragon kill animation on actual hardware.
 */
describe('AllyElderDragonKillAnimation Integration', () => {
    test('playAllyElderDragonKillAnimation', async () => {
        await new AnimationTester().testAnimation(new AllyElderDragonKillAnimation());
    }, 30000);
});
