import {BreathingColor} from '../../razer-sdk/color/BreathingColor.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';
import {DEFAULT_BACKGROUND_COLOR} from '../parts/BackgroundConstants.js';

export class BackgroundBreathingColor extends BreathingColor {
    constructor(upColor: StaticColor, steps?: number, startUpDirection?: boolean) {
        if (steps !== undefined && startUpDirection !== undefined) {
            super(upColor, DEFAULT_BACKGROUND_COLOR, steps, startUpDirection);
        } else if (steps !== undefined) {
            super(upColor, DEFAULT_BACKGROUND_COLOR, steps);
        } else {
            super(upColor, DEFAULT_BACKGROUND_COLOR);
        }
    }
}
