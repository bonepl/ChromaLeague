import {Color} from "./Color.js";
import {StaticColor} from "./StaticColor.js";
import {TransitionColor} from "./TransitionColor.js";

export class BreathingColor implements Color {
    private readonly upColor: TransitionColor;
    private readonly downColor: TransitionColor;
    private upDirection: boolean;

    constructor(upColor: StaticColor, downColor: StaticColor, steps?: number, startUpDirection?: boolean);
    constructor(upColor: TransitionColor, downColor: TransitionColor, startUpDirection?: boolean);
    constructor(
        upColor: StaticColor | TransitionColor,
        downColor: StaticColor | TransitionColor,
        stepsOrStartUp?: number | boolean,
        startUpDirection?: boolean
    ) {
        if (upColor instanceof TransitionColor && downColor instanceof TransitionColor) {
            this.upColor = upColor;
            this.downColor = downColor;
            this.upDirection = (stepsOrStartUp as boolean) ?? false;
        } else {
            const steps = (stepsOrStartUp as number) ?? 20;
            const startUp = startUpDirection ?? false;
            this.upColor = new TransitionColor(downColor as StaticColor, upColor as StaticColor, steps);
            this.downColor = new TransitionColor(upColor as StaticColor, downColor as StaticColor, steps);
            this.upDirection = startUp;
        }
    }

    setSteps(steps: number): void {
        this.upColor.setSteps(steps);
        this.downColor.setSteps(steps);
    }

    setUpColor(color: StaticColor): void {
        this.upColor.setTo(color);
        this.downColor.setFrom(color);
    }

    setDownColor(color: StaticColor): void {
        this.upColor.setFrom(color);
        this.downColor.setTo(color);
    }

    getColor(): StaticColor {
        if (this.upDirection) {
            const color = this.upColor.getColor();
            if (this.upColor.transitionFinished()) {
                this.upDirection = false;
                this.upColor.resetTransition();
            }
            return color;
        }
        const color = this.downColor.getColor();
        if (this.downColor.transitionFinished()) {
            this.upDirection = true;
            this.downColor.resetTransition();
        }
        return color;
    }
}
