import {AnimatedFrame} from '../../../razer-sdk/animation/AnimatedFrame.js';
import {Frame} from '../../../razer-sdk/animation/Frame.js';
import {Color} from '../../../razer-sdk/color/Color.js';
import {StaticColor} from '../../../razer-sdk/color/StaticColor.js';
import {TransitionColor} from '../../../razer-sdk/color/TransitionColor.js';
import {ProgressBar} from '../ProgressBar.js';
import {getResourceBarKeys} from './ResourceBars.js';
import {getResourcePercentage} from '../../../state/GameStateHelper.js';

const HEAT_THRESHOLD = 8.0;
const HEAT_COLOR = new TransitionColor(StaticColor.YELLOW, StaticColor.RED);

export class RumbleHeatBar extends AnimatedFrame {
    private lastHeatingTime = Date.now();
    private previousResourcePercentage = 0;
    private highestResourceValue = 0;
    private overheating = false;
    private coolingDown = false;

    pollFrame(): Frame {
        const resourcePercentage = getResourcePercentage();
        if (resourcePercentage !== this.previousResourcePercentage) {
            const now = Date.now();
            if (resourcePercentage < this.previousResourcePercentage) {
                if (!this.coolingDown) {
                    this.highestResourceValue = this.previousResourcePercentage;
                }
                this.coolingDown = true;
                const durationOfChange = now - this.lastHeatingTime;
                const delta = this.highestResourceValue - resourcePercentage;
                const decayRate = delta * 1000.0 / durationOfChange;
                this.overheating = decayRate > HEAT_THRESHOLD;
            }
            if (resourcePercentage > this.previousResourcePercentage) {
                this.coolingDown = false;
                this.overheating = false;
                this.lastHeatingTime = now;
                this.highestResourceValue = resourcePercentage;
            }
            this.previousResourcePercentage = resourcePercentage;
        }

        let color: Color;
        if (this.overheating) {
            color = StaticColor.RED;
        } else {
            if (resourcePercentage >= 50) {
                color = HEAT_COLOR.getColorAtPercent((resourcePercentage - 50) << 1);
            } else {
                color = StaticColor.WHITE;
            }
        }

        this.addAnimationFrame(new ProgressBar(getResourceBarKeys(), resourcePercentage, color));
        return super.pollFrame();
    }
}
