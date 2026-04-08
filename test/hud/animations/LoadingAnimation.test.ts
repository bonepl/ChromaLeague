import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {LoadingAnimation} from '../../../src/hud/animations/LoadingAnimation.js';

/**
 * Integration test for loading animation on actual hardware.
 */
describe('LoadingAnimation Integration', () => {
    test('testLoadingAnimation', async () => {
        const loadingAnimation = new LoadingAnimation();
        await new AnimationTester().testAnimation(loadingAnimation, 100);
    }, 30000);
});
