import {describe, test} from 'vitest';
import {IntSteps} from '../../../IntSteps.js';
import {AnimationTester} from '../../../AnimationTester.js';
import {ShyvanaDragonFuryBar} from '../../../../src/hud/parts/resource/ShyvanaDragonFuryBar.js';
import {mockResource, setupResourceTest} from './AbstractResourceTest.js';

/** Integration test for Shyvana dragon fury resource bar on actual hardware. */
describe('ShyvanaDragonFury Integration', () => {
    setupResourceTest();

    test('testShyvanaDragonPoolBar', async () => {
        const intSteps = new IntSteps(0, 100, 5);
        const shyvanaDragonFuryBar = new ShyvanaDragonFuryBar();

        await new AnimationTester()
            .withBeforeIterationAction(i => {
                if (i >= 20 && i <= 50) {
                    mockResource(100, 100);
                } else {
                    mockResource(intSteps.nextInt(), 100);
                }
            })
            .testAnimation(shyvanaDragonFuryBar, 70);
    }, 30000);
});
