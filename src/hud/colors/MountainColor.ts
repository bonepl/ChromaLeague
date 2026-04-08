import {Color} from '../../razer-sdk/color/Color.js';
import {MultiTransitionColor} from '../../razer-sdk/color/MultiTransitionColor.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';

export class MountainColor implements Color {
    readonly mountainColor = new MultiTransitionColor.Builder(new StaticColor(8, 3, 0))
        .addTransition(StaticColor.BROWN, 10)
        .addTransition(new StaticColor(8, 3, 0), 10)
        .addTransition(new StaticColor(64, 24, 0), 4)
        .looped(4)
        .build();

    constructor() {
        const random = Math.trunc(Math.random() * this.mountainColor.getTotalTransitions());
        for (let i = 0; i < random; i++) {
            this.mountainColor.getColor();
        }
    }

    getColor(): StaticColor {
        return this.mountainColor.getColor();
    }
}
