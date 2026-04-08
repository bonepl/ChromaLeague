import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {LoseAnimation} from '../../../src/hud/animations/LoseAnimation.js';

/**
 * Integration test for defeat animation on actual hardware.
 */
describe('LoseAnimation Integration', () => {
    test('playLoseAnimation', async () => {
        await new AnimationTester().testAnimation(new LoseAnimation(), 100);
    }, 30000);
});
