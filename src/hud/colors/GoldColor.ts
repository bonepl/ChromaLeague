import {Color} from '../../razer-sdk/color/Color.js';
import {MultiTransitionColor} from '../../razer-sdk/color/MultiTransitionColor.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';

const DARKER_YELLOW = new StaticColor(60, 60, 0);
const BLINK_CHANCE = 0.01;

export class GoldColor implements Color {
    private blinkingCountdown = 0;
    private blinkColor: MultiTransitionColor | null = null;
    private readonly goldColor = new MultiTransitionColor.Builder(StaticColor.YELLOW)
        .addTransition(DARKER_YELLOW, 20)
        .looped(20)
        .build();

    getColor(): StaticColor {
        if (this.blinkingCountdown === 0) {
            if (Math.random() <= BLINK_CHANCE) {
                this.blinkingCountdown = 4;
                this.blinkColor = this.createBlinkColor();
            }
            return this.goldColor.getColor();
        } else {
            this.blinkingCountdown--;
            return this.blinkColor!.getColor();
        }
    }

    private createBlinkColor(): MultiTransitionColor {
        return new MultiTransitionColor.Builder(this.goldColor.getColor())
            .addTransition(StaticColor.WHITE, 3)
            .looped(3)
            .build();
    }
}
