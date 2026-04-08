import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {AllyMountainDragonKillAnimation} from '../../../src/hud/animations/AllyMountainDragonKillAnimation.js';

/**
 * Integration test for ally mountain dragon kill animation on actual hardware.
 */
describe('AllyMountainDragonKillAnimation Integration', () => {
    test('playAllyMountainDragonKillAnimation', async () => {
        await new AnimationTester().testAnimation(new AllyMountainDragonKillAnimation());
    }, 30000);
});
