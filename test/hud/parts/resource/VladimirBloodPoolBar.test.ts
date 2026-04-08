import {describe, test} from 'vitest';
import {IntSteps} from '../../../IntSteps.js';
import {AnimationTester} from '../../../AnimationTester.js';
import {VladimirBloodPoolBar} from '../../../../src/hud/parts/resource/VladimirBloodPoolBar.js';
import {mockResource, setupResourceTest} from './AbstractResourceTest.js';

/** Integration test for Vladimir blood pool resource bar on actual hardware. */
describe('VladimirBloodPool Integration', () => {
    setupResourceTest();

    test('testVladimirPoolBar', async () => {
        const intSteps = new IntSteps(0, 100, 5);
        const vladimirBloodPoolBar = new VladimirBloodPoolBar();

        await new AnimationTester()
            .withBeforeIterationAction(i => {
                if (i >= 20 && i <= 30) {
                    mockResource(100, 100);
                } else {
                    mockResource(intSteps.nextInt(), 100);
                }
            })
            .testAnimation(vladimirBloodPoolBar, 50);
    }, 30000);
});
