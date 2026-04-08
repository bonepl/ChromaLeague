import {AnimatedFrame} from '../../../razer-sdk/animation/AnimatedFrame.js';
import {Frame} from '../../../razer-sdk/animation/Frame.js';
import {StaticColor} from '../../../razer-sdk/color/StaticColor.js';
import {TransitionColor} from '../../../razer-sdk/color/TransitionColor.js';
import {BackgroundBreathingColor} from '../../colors/BackgroundBreathingColor.js';
import {ProgressBar} from '../ProgressBar.js';
import {getResourceBarKeys} from './ResourceBars.js';
import {getActivePlayerRange, getResourcePercentage} from '../../../state/GameStateHelper.js';

const COLOR_TRANSITION_PERCENT_START = 50;
const COLOR_TRANSITION_PERCENT_STEP = 100 / COLOR_TRANSITION_PERCENT_START;

export class GnarFuryBar extends AnimatedFrame {
    private readonly fromYellowToRed = new TransitionColor(StaticColor.YELLOW, StaticColor.RED);
    private readonly aboutToTransform = new BackgroundBreathingColor(StaticColor.YELLOW, 20, false);

    pollFrame(): Frame {
        const activePlayerRange = getActivePlayerRange();
        const gnarFuryPercent = getResourcePercentage();
        if (activePlayerRange < 300) {
            this.addAnimationFrame(new ProgressBar(getResourceBarKeys(), gnarFuryPercent, StaticColor.RED));
        } else {
            if (gnarFuryPercent < COLOR_TRANSITION_PERCENT_START) {
                this.addAnimationFrame(new ProgressBar(getResourceBarKeys(), gnarFuryPercent, StaticColor.YELLOW));
            } else if (gnarFuryPercent < 85) {
                this.addAnimationFrame(new ProgressBar(getResourceBarKeys(), gnarFuryPercent,
                    this.fromYellowToRed.getColorAtPercent(COLOR_TRANSITION_PERCENT_STEP * (gnarFuryPercent - COLOR_TRANSITION_PERCENT_START))));
            } else if (gnarFuryPercent < 100) {
                this.aboutToTransform.setUpColor(this.fromYellowToRed.getColorAtPercent(COLOR_TRANSITION_PERCENT_STEP * (gnarFuryPercent - COLOR_TRANSITION_PERCENT_START)));
                this.addAnimationFrame(new ProgressBar(getResourceBarKeys(), gnarFuryPercent,
                    this.aboutToTransform));
            } else {
                this.aboutToTransform.setUpColor(StaticColor.RED);
                this.aboutToTransform.setSteps(5);
                this.addAnimationFrame(new ProgressBar(getResourceBarKeys(), gnarFuryPercent, this.aboutToTransform));
            }
        }
        return super.pollFrame();
    }
}
