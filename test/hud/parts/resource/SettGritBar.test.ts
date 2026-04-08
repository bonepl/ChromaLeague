import {describe, test} from 'vitest';
import {IntSteps} from '../../../IntSteps.js';
import {AnimationTester} from '../../../AnimationTester.js';
import {SettGritBar} from '../../../../src/hud/parts/resource/SettGritBar.js';
import {mockResource, setupResourceTest} from './AbstractResourceTest.js';

/** Integration test for Sett grit resource bar on actual hardware. */
describe('SettGrit Integration', () => {
    setupResourceTest();

    test('testSettGritBar', async () => {
        const intSteps = new IntSteps(0, 100, 5);
        const settGritBar = new SettGritBar();

        await new AnimationTester()
            .withBeforeIterationAction(i => {
                if (i >= 20 && i <= 50) {
                    mockResource(100, 100);
                } else {
                    mockResource(intSteps.nextInt(), 100);
                }
            })
            .withSleepTime(150)
            .testAnimation(settGritBar, 70);
    }, 30000);
});
