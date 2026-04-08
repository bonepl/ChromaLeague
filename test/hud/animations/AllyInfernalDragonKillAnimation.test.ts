import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {AllyInfernalDragonKillAnimation} from '../../../src/hud/animations/AllyInfernalDragonKillAnimation.js';

/**
 * Integration test for ally infernal dragon kill animation on actual hardware.
 */
describe('AllyInfernalDragonKillAnimation Integration', () => {
    test('playAllyInfernalDragonKillAnimation', async () => {
        await new AnimationTester().testAnimation(new AllyInfernalDragonKillAnimation());
    }, 30000);
});
