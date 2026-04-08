import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {AllyBaronKillAnimation} from '../../../src/hud/animations/AllyBaronKillAnimation.js';

/**
 * Integration test for ally Baron kill animation on actual hardware.
 */
describe('AllyBaronKillAnimation Integration', () => {
    test('playAllyBaronKillAnimation', async () => {
        await new AnimationTester().testAnimation(new AllyBaronKillAnimation());
    }, 30000);
});
