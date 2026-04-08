import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {GameStateMocks} from '../../GameStateMocks.js';
import {ActivePlayerKillAnimation} from '../../../src/hud/animations/ActivePlayerKillAnimation.js';

/**
 * Integration test for active player kill animation on actual hardware.
 */
describe('ActivePlayerKillAnimation Integration', () => {
    test('playActivePlayerKillAnimation', async () => {
        //given
        new GameStateMocks().playerList();
        //then
        await new AnimationTester().testAnimation(new ActivePlayerKillAnimation());
    }, 30000);
});
