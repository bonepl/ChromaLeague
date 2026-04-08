import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {AllyCloudDragonKillAnimation} from '../../../src/hud/animations/AllyCloudDragonKillAnimation.js';

/**
 * Integration test for ally cloud dragon kill animation on actual hardware.
 */
describe('AllyCloudDragonKillAnimation Integration', () => {
    test('playAllyCloudDragonKillAnimation', async () => {
        await new AnimationTester().testAnimation(new AllyCloudDragonKillAnimation());
    }, 30000);
});
