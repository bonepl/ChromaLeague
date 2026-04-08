import {AnimatedFrame} from '../../razer-sdk/animation/AnimatedFrame.js';
import {Animation} from '../../razer-sdk/animation/Animation.js';
import {Frame} from '../../razer-sdk/animation/Frame.js';
import {SimpleFrame} from '../../razer-sdk/animation/SimpleFrame.js';
import {Color} from '../../razer-sdk/color/Color.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';
import {
    ProgressiveRzKeySelector,
    ProgressiveRzKeySelectorBuilder
} from '../../razer-sdk/sdk/ProgressiveRzKeySelector.js';
import {
    BLACKWIDOW_FIFTH_ROW,
    BLACKWIDOW_FIRST_ROW,
    BLACKWIDOW_FOURTH_ROW,
    BLACKWIDOW_SECOND_ROW,
    BLACKWIDOW_SIXTH_ROW,
    BLACKWIDOW_THIRD_ROW,
} from '../PredefinedKeySets.js';

function buildMovingFrame(): ProgressiveRzKeySelector {
    return new ProgressiveRzKeySelectorBuilder()
        .addPart(BLACKWIDOW_SIXTH_ROW)
        .addPart(BLACKWIDOW_FIFTH_ROW)
        .addPart(BLACKWIDOW_FOURTH_ROW)
        .addPart(BLACKWIDOW_THIRD_ROW)
        .addPart(BLACKWIDOW_SECOND_ROW)
        .addPart(BLACKWIDOW_FIRST_ROW)
        .withLength(2)
        .build();
}

function createUpMovingFrame(delay: number, color: Color): AnimatedFrame {
    const animatedFrame = new AnimatedFrame();
    if (delay > 0) {
        animatedFrame.addAnimationFrame(delay << 2, new SimpleFrame());
    }
    const progressiveRzKeySelector = buildMovingFrame();
    for (let i = 0; i < progressiveRzKeySelector.getTotalSteps(); i++) {
        animatedFrame.addAnimationFrame(new SimpleFrame([...progressiveRzKeySelector.getNextPart()], color));
    }
    return animatedFrame;
}

export class WinAnimation extends Animation {
    constructor() {
        super();
        let counter = 0;
        while (counter < 500) {
            this.addToFront(createUpMovingFrame(counter++, StaticColor.GREEN));
            this.addToFront(createUpMovingFrame(counter++, StaticColor.WHITE));
            this.addToFront(createUpMovingFrame(counter++, StaticColor.BLUE));
            this.addToFront(createUpMovingFrame(counter++, StaticColor.YELLOW));
            this.addToFront(createUpMovingFrame(counter++, StaticColor.CYAN));
            this.addToFront(createUpMovingFrame(counter++, StaticColor.ORANGE));
            this.addToFront(createUpMovingFrame(counter++, StaticColor.RED));
        }
    }

    pollFrame(): Frame {
        this.addToBack(new SimpleFrame(StaticColor.BLACK));
        return super.pollFrame();
    }
}
