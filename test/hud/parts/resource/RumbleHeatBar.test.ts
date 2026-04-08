import {describe, test} from 'vitest';
import {IntSteps} from '../../../IntSteps.js';
import {AnimationTester} from '../../../AnimationTester.js';
import {RumbleHeatBar} from '../../../../src/hud/parts/resource/RumbleHeatBar.js';
import {mockResource, setupResourceTest} from './AbstractResourceTest.js';

/** Integration test for Rumble heat resource bar on actual hardware. */
describe('RumbleHeat Integration', () => {
    setupResourceTest();

    test('testRumbleStandardHeat', async () => {
        const heatSteps = new IntSteps(0, 100, 5);
        const cooldownSteps = new IntSteps(100, 0, 2);

        const rumbleHeatBar = new RumbleHeatBar();
        await new AnimationTester()
            .withBeforeIterationAction(i => {
                if (i < 20) {
                    mockResource(heatSteps.nextInt(), 100);
                } else {
                    mockResource(cooldownSteps.nextInt(), 100);
                }
            })
            .withSleepTime(300)
            .testAnimation(rumbleHeatBar, 67);
    }, 30000);

    test('testRumbleOverheatHeat', async () => {
        const heatSteps = new IntSteps(0, 100, 5);

        const rumbleHeatBar = new RumbleHeatBar();
        await new AnimationTester()
            .withBeforeIterationAction(() => mockResource(heatSteps.nextInt(), 100))
            .withSleepTime(300)
            .testAnimation(rumbleHeatBar, 40);
    }, 30000);
});
