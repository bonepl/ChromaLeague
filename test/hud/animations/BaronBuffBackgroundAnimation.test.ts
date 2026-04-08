import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {BaronBuffBackgroundAnimation} from '../../../src/hud/animations/BaronBuffBackgroundAnimation.js';

/**
 * Integration test for baron buff background animation on actual hardware.
 */
describe('BaronBuffBackgroundAnimation Integration', () => {
    test('playBaronBuffBackgroundAnimation', async () => {
        await new AnimationTester().testAnimation(new BaronBuffBackgroundAnimation(), 100);
    }, 30000);
});
