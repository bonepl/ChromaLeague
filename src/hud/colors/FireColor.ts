import {Color} from '../../razer-sdk/color/Color.js';
import {MultiTransitionColor} from '../../razer-sdk/color/MultiTransitionColor.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';

export class FireColor implements Color {
    readonly fireColor = new MultiTransitionColor.Builder(StaticColor.YELLOW)
        .addTransition(StaticColor.ORANGE, 5)
        .addTransition(StaticColor.RED, 10)
        .addTransition(StaticColor.ORANGE, 10)
        .looped(5)
        .build();

    constructor() {
        const random = Math.trunc(Math.random() * this.fireColor.getTotalTransitions());
        for (let i = 0; i < random; i++) {
            this.fireColor.getColor();
        }
    }

    getColor(): StaticColor {
        return this.fireColor.getColor();
    }
}
