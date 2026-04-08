import {Color} from "./Color.js";
import {StaticColor} from "./StaticColor.js";

const DEFAULT_STEPS = 20;

export class TransitionColor implements Color {
    private from: StaticColor;
    private to: StaticColor;
    private steps: number;
    private currentStep: number = 0;

    constructor(from: StaticColor, to: StaticColor, steps: number = DEFAULT_STEPS) {
        this.from = from;
        this.to = to;
        if (steps < 2) {
            throw new Error(
                "TransitionColor needs at least 2 steps for transition, provided steps: " + steps
            );
        }
        this.steps = steps;
    }

    getColorAtPercent(percent: number): StaticColor {
        return new StaticColor(
            Math.trunc(this.from.red - (this.from.red - this.to.red) * percent * 0.01),
            Math.trunc(this.from.green - (this.from.green - this.to.green) * percent * 0.01),
            Math.trunc(this.from.blue - (this.from.blue - this.to.blue) * percent * 0.01)
        );
    }

    private getRedStep(): number {
        return Math.round((this.from.red - this.to.red) / (this.steps - 1));
    }

    private getGreenStep(): number {
        return Math.round((this.from.green - this.to.green) / (this.steps - 1));
    }

    private getBlueStep(): number {
        return Math.round((this.from.blue - this.to.blue) / (this.steps - 1));
    }

    transitionFinished(): boolean {
        return this.currentStep === this.steps;
    }

    resetTransition(): void {
        this.currentStep = 0;
    }

    setFrom(from: StaticColor): void {
        this.from = from;
    }

    setTo(to: StaticColor): void {
        this.to = to;
    }

    setSteps(newSteps: number): void {
        if (newSteps !== this.steps) {
            const stepsRatio = this.currentStep / this.steps;
            this.currentStep = Math.trunc(stepsRatio * newSteps);
            this.steps = newSteps;
        }
    }

    getColor(): StaticColor {
        if (this.transitionFinished()) {
            return this.to;
        }

        if (this.currentStep + 1 === this.steps) {
            this.currentStep += 1;
            return this.to;
        }

        const color = new StaticColor(
            this.from.red - this.getRedStep() * this.currentStep,
            this.from.green - this.getGreenStep() * this.currentStep,
            this.from.blue - this.getBlueStep() * this.currentStep
        );
        this.currentStep += 1;
        return color;
    }
}
