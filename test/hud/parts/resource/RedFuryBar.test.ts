import {describe, test} from 'vitest';
import {IntSteps} from '../../../IntSteps.js';
import {AnimationTester} from '../../../AnimationTester.js';
import {RedFuryBar} from '../../../../src/hud/parts/resource/RedFuryBar.js';
import {mockResource, setupResourceTest} from './AbstractResourceTest.js';

/** Integration test for red fury resource bar on actual hardware. */
describe('RedFury Integration', () => {
    setupResourceTest();

    test('testRedFuryBar', async () => {
        const intSteps = new IntSteps(0, 100, 5);
        const redFuryBar = new RedFuryBar();

        await new AnimationTester()
            .withBeforeIterationAction(() => mockResource(intSteps.nextInt(), 100))
            .testAnimation(redFuryBar, 40);
    }, 30000);
});
