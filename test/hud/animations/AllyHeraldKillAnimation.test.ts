import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {AllyHeraldKillAnimation} from '../../../src/hud/animations/AllyHeraldKillAnimation.js';

/**
 * Integration test for ally herald kill animation on actual hardware.
 */
describe('AllyHeraldKillAnimation Integration', () => {
    test('playAllyHeraldKillAnimation', async () => {
        await new AnimationTester().testAnimation(new AllyHeraldKillAnimation());
    }, 30000);
});
