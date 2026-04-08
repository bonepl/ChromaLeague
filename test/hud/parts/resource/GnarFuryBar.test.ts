import {describe, test} from 'vitest';
import {AnimationTester} from '../../../AnimationTester.js';
import {GnarFuryBar} from '../../../../src/hud/parts/resource/GnarFuryBar.js';
import {mockRange, mockResource, setupResourceTest} from './AbstractResourceTest.js';

/** Integration test for Gnar fury resource bar on actual hardware. */
describe('GnarFury Integration', () => {
    setupResourceTest();

    test('testGnarFuryBar', async () => {
        let currentResource = 0;
        let currentRange = 400;
        const gnarFuryBar = new GnarFuryBar();

        await new AnimationTester()
            .withBeforeIterationAction(i => {
                if (i < 120) {
                    if (currentResource < 100) {
                        currentResource++;
                    }
                    currentRange = 400;
                } else if (i > 150) {
                    if (currentResource > 0) {
                        currentResource -= 2;
                    }
                    currentRange = 170;
                }
                mockResource(currentResource, 100);
                mockRange(currentRange);
            })
            .testAnimation(gnarFuryBar, 220);
    }, 30000);
});
