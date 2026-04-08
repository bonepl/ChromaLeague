import {describe, test} from 'vitest';
import {IntSteps} from '../../../IntSteps.js';
import {AnimationTester} from '../../../AnimationTester.js';
import {YasuoWindBar} from '../../../../src/hud/parts/resource/YasuoWindBar.js';
import {mockResource, setupResourceTest} from './AbstractResourceTest.js';

/** Integration test for Yasuo wind resource bar on actual hardware. */
describe('YasuoWind Integration', () => {
    setupResourceTest();

    test('testYasuoWindBar', async () => {
        const intSteps = new IntSteps(0, 100, 5);
        const yasuoWindBar = new YasuoWindBar();

        await new AnimationTester()
            .withBeforeIterationAction(i => {
                if (i >= 20 && i <= 50) {
                    mockResource(100, 100);
                } else {
                    mockResource(intSteps.nextInt(), 100);
                }
            })
            .testAnimation(yasuoWindBar, 70);
    }, 30000);
});
