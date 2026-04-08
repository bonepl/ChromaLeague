import {describe, test} from 'vitest';
import {IntSteps} from '../../../IntSteps.js';
import {AnimationTester} from '../../../AnimationTester.js';
import {YoneCloneBar} from '../../../../src/hud/parts/resource/YoneCloneBar.js';
import {mockResource, setupResourceTest} from './AbstractResourceTest.js';

/** Integration test for Yone clone resource bar on actual hardware. */
describe('YoneClone Integration', () => {
    setupResourceTest();

    test('testYoneCloneBar', async () => {
        const intSteps = new IntSteps(100, 0, 5);
        const yoneCloneBar = new YoneCloneBar();

        await new AnimationTester()
            .withBeforeIterationAction(() => mockResource(intSteps.nextInt(), 100))
            .testAnimation(yoneCloneBar, 20);
    }, 30000);
});
