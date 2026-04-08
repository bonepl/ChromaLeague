import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {GameStateMocks} from '../../GameStateMocks.js';
import {ActivePlayerAssistAnimation} from '../../../src/hud/animations/ActivePlayerAssistAnimation.js';

/**
 * Integration test for active player assist animation on actual hardware.
 */
describe('ActivePlayerAssistAnimation Integration', () => {
    test('playActivePlayerAssistAnimation', async () => {
        //given
        new GameStateMocks().playerList();
        //then
        await new AnimationTester().testAnimation(new ActivePlayerAssistAnimation());
    }, 30000);
});
