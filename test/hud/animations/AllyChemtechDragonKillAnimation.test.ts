import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {AllyChemtechDragonKillAnimation} from '../../../src/hud/animations/AllyChemtechDragonKillAnimation.js';

/**
 * Integration test for ally chemtech dragon kill animation on actual hardware.
 */
describe('AllyChemtechDragonKillAnimation Integration', () => {
    test('playAllyChemtechDragonKillAnimation', async () => {
        await new AnimationTester().testAnimation(new AllyChemtechDragonKillAnimation());
    }, 30000);
});
