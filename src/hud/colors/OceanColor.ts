import {Color} from '../../razer-sdk/color/Color.js';
import {MultiTransitionColor} from '../../razer-sdk/color/MultiTransitionColor.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';
import {CLColor} from './CLColor.js';

const DARKER_OCEAN = new StaticColor(0, 60, 60);

export class OceanColor implements Color {
    readonly oceanColor = new MultiTransitionColor.Builder(DARKER_OCEAN)
        .addTransition(CLColor.OCEAN, 10)
        .addTransition(DARKER_OCEAN, 10)
        .addTransition(CLColor.OCEAN, 10)
        .addTransition(new StaticColor(30, 40, 40), 5)
        .looped(5)
        .build();

    constructor() {
        const random = Math.trunc(Math.random() * this.oceanColor.getTotalTransitions());
        for (let i = 0; i < random; i++) {
            this.oceanColor.getColor();
        }
    }

    getColor(): StaticColor {
        return this.oceanColor.getColor();
    }
}
