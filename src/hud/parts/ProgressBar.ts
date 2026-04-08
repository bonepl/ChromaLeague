import {Color} from '../../razer-sdk/color/Color.js';
import {SimpleFrame} from '../../razer-sdk/animation/SimpleFrame.js';
import {RzKey} from '../../razer-sdk/sdk/RzKey.js';

export class ProgressBar extends SimpleFrame {
    constructor(progressBar: readonly RzKey[], percent: number, color: Color);
    constructor(progressBar: Map<RzKey, Color>, percent: number);
    constructor(
        progressBar: readonly RzKey[] | Map<RzKey, Color>,
        percent: number,
        color?: Color,
    ) {
        if (progressBar instanceof Map) {
            const limit = indexToFill(progressBar.size, percent);
            const limited = new Map<RzKey, Color>();
            let i = 0;
            for (const [key, val] of progressBar) {
                if (i >= limit) break;
                limited.set(key, val);
                i++;
            }
            super(limited);
        } else {
            super(getBarPercent(progressBar, percent), color!);
        }
    }
}

function getBarPercent(progressBar: readonly RzKey[], percent: number): readonly RzKey[] {
    return progressBar.slice(0, indexToFill(progressBar.length, percent));
}

export function indexToFill(progressBarSize: number, percent: number): number {
    if (percent >= 100) {
        return progressBarSize;
    }
    return Math.trunc(progressBarSize * (percent * 0.01));
}
