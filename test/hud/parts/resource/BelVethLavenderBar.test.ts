import {describe, test} from 'vitest';
import {IntSteps} from '../../../IntSteps.js';
import {AnimationTester} from '../../../AnimationTester.js';
import {BelVethLavenderBar} from '../../../../src/hud/parts/resource/BelVethLavenderBar.js';
import {mockResource, setupResourceTest} from './AbstractResourceTest.js';

/** Integration test for Bel'Veth lavender resource bar on actual hardware. */
describe('BelVethLavender Integration', () => {
    setupResourceTest();

    test('testBelVethLavenderBar', async () => {
        const intSteps = new IntSteps(100, 0, 5);
        const belVethLavenderBar = new BelVethLavenderBar();

        await new AnimationTester()
            .withBeforeIterationAction(() => mockResource(intSteps.nextInt(), 100))
            .testAnimation(belVethLavenderBar, 20);
    }, 30000);
});
