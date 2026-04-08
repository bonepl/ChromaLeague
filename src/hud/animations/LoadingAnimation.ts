import {AnimatedFrame} from '../../razer-sdk/animation/AnimatedFrame.js';
import {Frame} from '../../razer-sdk/animation/Frame.js';
import {SimpleFrame} from '../../razer-sdk/animation/SimpleFrame.js';
import {Color} from '../../razer-sdk/color/Color.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';
import {ProgressiveRzKeySelector} from '../../razer-sdk/sdk/ProgressiveRzKeySelector.js';
import {RzKey} from '../../razer-sdk/sdk/RzKey.js';
import {KEYBOARD_COLUMNS, RzKeySelector} from '../../razer-sdk/sdk/RzKeySelector.js';

const LOADING_COLOR: Color = StaticColor.GREEN;

function buildParts(): ReadonlySet<RzKey>[] {
    const forward: ReadonlySet<RzKey>[] = [];
    for (let col = 0; col < KEYBOARD_COLUMNS; col++) {
        forward.push(
            new RzKeySelector()
                .withColumn(col)
                .withRowBetween(RzKey.RZKEY_ESC, RzKey.RZKEY_LCTRL)
                .asSet(),
        );
    }
    const backward: ReadonlySet<RzKey>[] = [];
    for (let col = KEYBOARD_COLUMNS - 1; col >= 0; col--) {
        backward.push(
            new RzKeySelector()
                .withColumn(col)
                .withRowBetween(RzKey.RZKEY_ESC, RzKey.RZKEY_LCTRL)
                .asSet(),
        );
    }
    return [...forward, ...backward];
}

const progressiveRzKeySelector = new ProgressiveRzKeySelector(buildParts(), 2);

export class LoadingAnimation extends AnimatedFrame {
    pollFrame(): Frame {
        this.addAnimationFrame(new SimpleFrame([...progressiveRzKeySelector.getNextPart()], LOADING_COLOR));
        return super.pollFrame();
    }
}
