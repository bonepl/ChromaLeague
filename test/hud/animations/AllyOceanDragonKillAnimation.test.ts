import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {AllyOceanDragonKillAnimation} from '../../../src/hud/animations/AllyOceanDragonKillAnimation.js';

/**
 * Integration test for ally ocean dragon kill animation on actual hardware.
 */
describe('AllyOceanDragonKillAnimation Integration', () => {
    test('playAllyOceanDragonKillAnimation', async () => {
        await new AnimationTester().testAnimation(new AllyOceanDragonKillAnimation());
    }, 30000);
});
