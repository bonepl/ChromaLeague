import {AnimatedFrame} from '../../../razer-sdk/animation/AnimatedFrame.js';
import {SimpleFrame} from '../../../razer-sdk/animation/SimpleFrame.js';
import {StaticColor} from '../../../razer-sdk/color/StaticColor.js';
import {TransitionColor} from '../../../razer-sdk/color/TransitionColor.js';
import {RzKey} from '../../../razer-sdk/sdk/RzKey.js';
import {CLColor} from '../../colors/CLColor.js';
import {getHpBarPart} from './HealthBar.js';

const GAINED_HEALTH_CHANGE_STEPS = 5;

export class GainedHealthAnimation extends AnimatedFrame {
    constructor(previousHp: number, currentHp: number) {
        super();
        this.createGainedHealthAnimation(previousHp, currentHp);
    }

    private createGainedHealthAnimation(previousHp: number, currentHp: number): void {
        const rzKeys = computeGainedHealth(previousHp, currentHp);
        if (rzKeys.length > 0) {
            const transitionColor = new TransitionColor(StaticColor.WHITE, CLColor.HEALTH, GAINED_HEALTH_CHANGE_STEPS);
            for (let i = 0; i < GAINED_HEALTH_CHANGE_STEPS; i++) {
                this.addAnimationFrame(new SimpleFrame(rzKeys, transitionColor.getColor()));
            }
        }
    }
}

function computeGainedHealth(previousHp: number, currentHp: number): RzKey[] {
    return getHpBarPart(previousHp, currentHp);
}
