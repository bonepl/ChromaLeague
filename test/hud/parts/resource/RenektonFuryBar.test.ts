import {describe, test} from 'vitest';
import {IntSteps} from '../../../IntSteps.js';
import {AnimationTester} from '../../../AnimationTester.js';
import {RenektonFuryBar} from '../../../../src/hud/parts/resource/RenektonFuryBar.js';
import {mockResource, setupResourceTest} from './AbstractResourceTest.js';

/** Integration test for Renekton fury resource bar on actual hardware. */
describe('RenektonFury Integration', () => {
    setupResourceTest();

    test('testRenektonFuryBar', async () => {
        const intSteps = new IntSteps(0, 100, 5);
        const renektonFuryBar = new RenektonFuryBar();

        await new AnimationTester()
            .withBeforeIterationAction(i => {
                if (i >= 20 && i <= 30) {
                    mockResource(100, 100);
                } else {
                    mockResource(intSteps.nextInt(), 100);
                }
            })
            .testAnimation(renektonFuryBar, 50);
    }, 30000);
});
