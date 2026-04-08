import {describe, test} from 'vitest';
import {IntSteps} from '../../../IntSteps.js';
import {AnimationTester} from '../../../AnimationTester.js';
import {RengarFerocityBar} from '../../../../src/hud/parts/resource/RengarFerocityBar.js';
import {mockResource, setupResourceTest} from './AbstractResourceTest.js';

/** Integration test for Rengar ferocity resource bar on actual hardware. */
describe('RengarFerocity Integration', () => {
    setupResourceTest();

    test('testRengarFerocityBar', async () => {
        const intSteps = new IntSteps(0, 100, 25);
        const rengarFerocityBar = new RengarFerocityBar();

        await new AnimationTester()
            .withBeforeIterationAction(i => {
                if (i > 20) {
                    mockResource(100, 100);
                } else if (i % 5 === 0) {
                    mockResource(intSteps.nextInt(), 100);
                }
            })
            .testAnimation(rengarFerocityBar, 40);
    }, 30000);
});
