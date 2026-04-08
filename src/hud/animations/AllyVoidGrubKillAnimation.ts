import {AnimatedFrame} from '../../razer-sdk/animation/AnimatedFrame.js';
import {SimpleFrame} from '../../razer-sdk/animation/SimpleFrame.js';
import {Color} from '../../razer-sdk/color/Color.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';
import {RzKey} from '../../razer-sdk/sdk/RzKey.js';
import {RzKeySelector} from '../../razer-sdk/sdk/RzKeySelector.js';

const DOT_COLOR = StaticColor.PURPLE;
const DOT_TRAIL = new StaticColor(80, 0, 80);

// Period of 6 columns — produces ~3.5 full sine waves across the 22-column keyboard
const SINE_PERIOD = 3; // col * PI / SINE_PERIOD
const DOT_SPACING = 7;
const DOT_COUNT = 3;

function dotRow(col: number): number {
    // Sine wave oscillating between rows 1 (number row) and 4 (Z row)
    const sineRow = Math.max(1, Math.min(4, Math.round(2.5 + 1.5 * Math.sin(col * Math.PI / SINE_PERIOD))));
    // Nav cluster (cols 15–17) has no keys in rows 3–4, cap to row 2
    if (col >= 15 && col <= 17) return Math.min(sineRow, 2);
    return sineRow;
}

function keyAt(row: number, col: number): RzKey | null {
    const keys = new RzKeySelector().withRow(row).withColumn(col).asList();
    return keys.length > 0 ? keys[0] : null;
}

function dotKey(col: number): RzKey | null {
    if (col < 0 || col > 21) return null;
    return keyAt(dotRow(col), col);
}

export class AllyVoidGrubKillAnimation extends AnimatedFrame {
    constructor() {
        super();
        this.addOpeningFlashes();
        this.addJumpingDots();
        this.addClosingFlashes();
    }

    private addOpeningFlashes(): void {
        for (let i = 0; i < 3; i++) {
            this.addAnimationFrame(new SimpleFrame(StaticColor.PURPLE));
            this.addAnimationFrame(2, new SimpleFrame(StaticColor.BLACK));
        }
    }

    private addJumpingDots(): void {
        // dot 0 travels cols 0..21, dot 1 starts DOT_SPACING behind, dot 2 behind that
        const totalFrames = 22 + (DOT_COUNT - 1) * DOT_SPACING;
        for (let t = 0; t < totalFrames; t++) {
            const colorMap = new Map<RzKey, Color>();
            for (let d = 0; d < DOT_COUNT; d++) {
                const headCol = t - d * DOT_SPACING;
                const trail = dotKey(headCol - 1);
                const head = dotKey(headCol);
                if (trail !== null) colorMap.set(trail, DOT_TRAIL);
                if (head !== null) colorMap.set(head, DOT_COLOR); // head overwrites trail on same key
            }
            this.addAnimationFrame(new SimpleFrame(colorMap));
        }
    }

    private addClosingFlashes(): void {
        for (let i = 0; i < 3; i++) {
            this.addAnimationFrame(new SimpleFrame(StaticColor.PURPLE));
            this.addAnimationFrame(2, new SimpleFrame(StaticColor.BLACK));
        }
    }
}
