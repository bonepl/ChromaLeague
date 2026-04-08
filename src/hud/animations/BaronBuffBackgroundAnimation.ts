import {AnimatedFrame} from '../../razer-sdk/animation/AnimatedFrame.js';
import {Frame} from '../../razer-sdk/animation/Frame.js';
import {SimpleFrame} from '../../razer-sdk/animation/SimpleFrame.js';
import {BreathingColor} from '../../razer-sdk/color/BreathingColor.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';
import {RzKey} from '../../razer-sdk/sdk/RzKey.js';
import {RzKeyJoiner} from '../../razer-sdk/sdk/RzKeyJoiner.js';
import {RzKeySelector} from '../../razer-sdk/sdk/RzKeySelector.js';
import {BackgroundBreathingColor} from '../colors/BackgroundBreathingColor.js';

const BARON_AREA: RzKey[] = buildBaronArea();

function buildBaronArea(): RzKey[] {
    return [...new RzKeyJoiner()
        .withSelector(new RzKeySelector().withRowOf(RzKey.RZKEY_Q).withColumnBetween(RzKey.RZKEY_Q, RzKey.RZKEY_U))
        .withSelector(new RzKeySelector().withRowOf(RzKey.RZKEY_A).withColumnBetween(RzKey.RZKEY_A, RzKey.RZKEY_H))
        .withSelector(new RzKeySelector().withRowOf(RzKey.RZKEY_Z).withColumnBetween(RzKey.RZKEY_Z, RzKey.RZKEY_B))
        .join()];
}

export class BaronBuffBackgroundAnimation extends AnimatedFrame {
    private readonly baronBuffColor: BreathingColor = new BackgroundBreathingColor(new StaticColor(200, 0, 200));

    override pollFrame(): Frame {
        if (!this.hasFrame()) {
            this.extendAnimation();
        }
        return super.pollFrame();
    }

    private extendAnimation(): void {
        for (let i = 0; i < 20; i++) {
            this.addAnimationFrame(2, new SimpleFrame(BARON_AREA, this.baronBuffColor.getColor()));
        }
    }
}
