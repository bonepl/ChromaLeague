import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {WinAnimation} from '../../../src/hud/animations/WinAnimation.js';

/**
 * Integration test for victory animation on actual hardware.
 */
describe('WinAnimation Integration', () => {
    test('playWinAnimation', async () => {
        await new AnimationTester().testAnimation(new WinAnimation(), 100);
    }, 30000);
});
