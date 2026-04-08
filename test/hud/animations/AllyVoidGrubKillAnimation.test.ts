import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {AllyVoidGrubKillAnimation} from '../../../src/hud/animations/AllyVoidGrubKillAnimation.js';

/**
 * Integration test for ally void grub kill animation on actual hardware.
 */
describe('AllyVoidGrubKillAnimation Integration', () => {
    test('playAllyVoidGrubKillAnimation', async () => {
        await new AnimationTester().testAnimation(new AllyVoidGrubKillAnimation());
    }, 30000);
});
