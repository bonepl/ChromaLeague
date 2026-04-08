import {Color} from '../../razer-sdk/color/Color.js';
import {MultiTransitionColor} from '../../razer-sdk/color/MultiTransitionColor.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';
import {CLColor} from './CLColor.js';

export class ChemtechColor implements Color {
    readonly chemtechColor = new MultiTransitionColor.Builder(CLColor.CHEMTECH)
        .addTransition(StaticColor.YELLOW, 10)
        .addTransition(CLColor.CHEMTECH, 10)
        .addTransition(new StaticColor(25, 60, 0), 5)
        .looped(5)
        .build();

    constructor() {
        const random = Math.trunc(Math.random() * this.chemtechColor.getTotalTransitions());
        for (let i = 0; i < random; i++) {
            this.chemtechColor.getColor();
        }
    }

    getColor(): StaticColor {
        return this.chemtechColor.getColor();
    }
}
