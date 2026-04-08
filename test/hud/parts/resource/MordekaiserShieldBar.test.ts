import {describe, test} from 'vitest';
import {IntSteps} from '../../../IntSteps.js';
import {AnimationTester} from '../../../AnimationTester.js';
import {MordekaiserShieldBar} from '../../../../src/hud/parts/resource/MordekaiserShieldBar.js';
import {mockResource, setupResourceTest} from './AbstractResourceTest.js';

/** Integration test for Mordekaiser shield resource bar on actual hardware. */
describe('MordekaiserShield Integration', () => {
    setupResourceTest();

    test('testMordekaiserShieldBar', async () => {
        const intSteps = new IntSteps(0, 100, 5);
        const mordekaiserShieldBar = new MordekaiserShieldBar();

        await new AnimationTester()
            .withBeforeIterationAction(() => mockResource(intSteps.nextInt(), 100))
            .testAnimation(mordekaiserShieldBar, 40);
    }, 30000);
});
