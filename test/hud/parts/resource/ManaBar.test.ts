import {describe, test} from 'vitest';
import {IntSteps} from '../../../IntSteps.js';
import {AnimationTester} from '../../../AnimationTester.js';
import {ManaBar} from '../../../../src/hud/parts/resource/ManaBar.js';
import {mockResource, setupResourceTest} from './AbstractResourceTest.js';

/** Integration test for mana resource bar on actual hardware. */
describe('Mana Integration', () => {
    setupResourceTest();

    test('testManaBar', async () => {
        const intSteps = new IntSteps(0, 100, 5);
        const manaBar = new ManaBar();

        await new AnimationTester()
            .withBeforeIterationAction(() => mockResource(intSteps.nextInt(), 100))
            .testAnimation(manaBar, 40);
    }, 30000);
});
