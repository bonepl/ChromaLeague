import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {AllyHextechDragonKillAnimation} from '../../../src/hud/animations/AllyHextechDragonKillAnimation.js';

/**
 * Integration test for ally hextech dragon kill animation on actual hardware.
 */
describe('AllyHextechDragonKillAnimation Integration', () => {
    test('playAllyHextechDragonKillAnimation', async () => {
        await new AnimationTester().testAnimation(new AllyHextechDragonKillAnimation());
    }, 30000);
});
