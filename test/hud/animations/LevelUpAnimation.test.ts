import {describe, test} from 'vitest';
import {AnimationTester} from '../../AnimationTester.js';
import {GameStateMocks} from '../../GameStateMocks.js';
import {LevelUpAnimation} from '../../../src/hud/animations/LevelUpAnimation.js';

/**
 * Integration test for level up animation on actual hardware.
 */
describe('LevelUpAnimation Integration', () => {
    test('playLevelUpAnimation', async () => {
        // given
        const mocks = new GameStateMocks();
        mocks.activePlayer().level = 1;

        // when
        const levelUpAnimation = new LevelUpAnimation();
        levelUpAnimation.levelUp();

        // then
        await new AnimationTester().testAnimation(levelUpAnimation);
    }, 30000);
});
