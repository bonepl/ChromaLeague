import {Color} from '../../razer-sdk/color/Color.js';
import {MultiTransitionColor} from '../../razer-sdk/color/MultiTransitionColor.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';

export class HextechColor implements Color {
    readonly hextechColor = new MultiTransitionColor.Builder(StaticColor.BLUE)
        .addTransition(StaticColor.BLUE, 20)
        .addTransition(StaticColor.WHITE, 3)
        .looped(3)
        .build();

    constructor() {
        const random = Math.trunc(Math.random() * this.hextechColor.getTotalTransitions());
        for (let i = 0; i < random; i++) {
            this.hextechColor.getColor();
        }
    }

    getColor(): StaticColor {
        return this.hextechColor.getColor();
    }
}
