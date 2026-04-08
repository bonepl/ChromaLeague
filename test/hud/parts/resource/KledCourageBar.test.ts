import {describe, test} from 'vitest';
import {IntSteps} from '../../../IntSteps.js';
import {AnimationTester} from '../../../AnimationTester.js';
import {KledCourageBar} from '../../../../src/hud/parts/resource/KledCourageBar.js';
import {mockResource, setupResourceTest} from './AbstractResourceTest.js';

/** Integration test for Kled courage resource bar on actual hardware. */
describe('KledCourage Integration', () => {
    setupResourceTest();

    test('testKledCourageBar', async () => {
        const intSteps = new IntSteps(0, 100, 5);
        const kledCourageBar = new KledCourageBar();

        await new AnimationTester()
            .withBeforeIterationAction(i => {
                if (i >= 20 && i <= 50) {
                    mockResource(100, 100);
                } else {
                    mockResource(intSteps.nextInt(), 100);
                }
            })
            .testAnimation(kledCourageBar, 70);
    }, 30000);
});
