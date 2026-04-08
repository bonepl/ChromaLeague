import {describe, test} from 'vitest';
import {IntSteps} from '../../../IntSteps.js';
import {AnimationTester} from '../../../AnimationTester.js';
import {EnergyBar} from '../../../../src/hud/parts/resource/EnergyBar.js';
import {mockResource, setupResourceTest} from './AbstractResourceTest.js';

/** Integration test for energy resource bar on actual hardware. */
describe('Energy Integration', () => {
    setupResourceTest();

    test('testEnergyBar', async () => {
        const intSteps = new IntSteps(0, 100, 5);
        const energyBar = new EnergyBar();

        await new AnimationTester()
            .withBeforeIterationAction(() => mockResource(intSteps.nextInt(), 100))
            .testAnimation(energyBar, 40);
    }, 30000);
});
