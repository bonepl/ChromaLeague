import {AnimatedFrame} from '../../../razer-sdk/animation/AnimatedFrame.js';
import {SimpleFrame} from '../../../razer-sdk/animation/SimpleFrame.js';
import {StaticColor} from '../../../razer-sdk/color/StaticColor.js';
import {TransitionColor} from '../../../razer-sdk/color/TransitionColor.js';
import {RzKey} from '../../../razer-sdk/sdk/RzKey.js';
import {DEFAULT_BACKGROUND_COLOR} from '../Background.js';
import {getHpBarPart} from './HealthBar.js';

const LOST_HEALTH_CHANGE_STEPS = 5;

export class LostHealthAnimation extends AnimatedFrame {
    constructor(previousHp: number, currentHp: number) {
        super();
        this.createLostHealthAnimation(previousHp, currentHp);
    }

    private createLostHealthAnimation(previousHp: number, currentHp: number): void {
        const rzKeys = computeLostHealth(previousHp, currentHp);
        if (rzKeys.length > 0) {
            const transitionColor = new TransitionColor(StaticColor.RED, DEFAULT_BACKGROUND_COLOR, LOST_HEALTH_CHANGE_STEPS);
            for (let i = 0; i < LOST_HEALTH_CHANGE_STEPS; i++) {
                this.addAnimationFrame(new SimpleFrame(rzKeys, transitionColor.getColor()));
            }
        }
    }
}

function computeLostHealth(previousHp: number, currentHp: number): RzKey[] {
    return getHpBarPart(currentHp, previousHp);
}
