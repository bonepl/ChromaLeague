import {AnimatedFrame} from '../../razer-sdk/animation/AnimatedFrame.js';
import {SimpleFrame} from '../../razer-sdk/animation/SimpleFrame.js';
import {BreathingColor} from '../../razer-sdk/color/BreathingColor.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';
import {ProgressiveRzKeySelector} from '../../razer-sdk/sdk/ProgressiveRzKeySelector.js';
import {RzKey} from '../../razer-sdk/sdk/RzKey.js';
import {KEYBOARD_ROWS, RzKeySelector} from '../../razer-sdk/sdk/RzKeySelector.js';

function buildRowParts(): ReadonlySet<RzKey>[] {
    const parts: ReadonlySet<RzKey>[] = [];
    for (let row = 0; row < KEYBOARD_ROWS; row++) {
        parts.push(new RzKeySelector().withRow(row).asSet());
    }
    return parts;
}

const TOTAL_FRAMES = 1200; // ~60 seconds at 20fps

export class LoseAnimation extends AnimatedFrame {
    constructor() {
        super();
        const progressiveRzKeySelector = new ProgressiveRzKeySelector(buildRowParts(), 4);
        const breathingColor = new BreathingColor(StaticColor.RED, new StaticColor(10, 0, 0), 50);
        let currentSet: Set<RzKey> = new Set();
        let i = 0;

        for (let frame = 0; frame < TOTAL_FRAMES; frame++) {
            if (i === 0) {
                currentSet = progressiveRzKeySelector.getNextPart();
                i = 4;
            }
            this.addAnimationFrame(new SimpleFrame([...currentSet], breathingColor));
            i--;
        }
    }
}
