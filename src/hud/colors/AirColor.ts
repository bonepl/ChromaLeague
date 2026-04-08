import {Color} from '../../razer-sdk/color/Color.js';
import {MultiTransitionColor} from '../../razer-sdk/color/MultiTransitionColor.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';
import {CLColor} from './CLColor.js';

export class AirColor implements Color {
    readonly windColor = new MultiTransitionColor.Builder(new StaticColor(15, 18, 18))
        .addTransition(CLColor.AIR, 10)
        .addTransition(new StaticColor(15, 18, 18), 10)
        .addTransition(new StaticColor(60, 75, 75), 5)
        .looped(5)
        .build();

    constructor() {
        const random = Math.trunc(Math.random() * this.windColor.getTotalTransitions());
        for (let i = 0; i < random; i++) {
            this.windColor.getColor();
        }
    }

    getColor(): StaticColor {
        return this.windColor.getColor();
    }
}
