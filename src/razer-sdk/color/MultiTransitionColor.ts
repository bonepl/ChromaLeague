import {Color} from "./Color.js";
import {StaticColor} from "./StaticColor.js";
import {TransitionColor} from "./TransitionColor.js";

export class MultiTransitionColor implements Color {
    private readonly colorsList: StaticColor[];
    private i: number = 0;

    private constructor(colorsList: StaticColor[]) {
        this.colorsList = colorsList;
    }

    getColor(): StaticColor {
        if (this.i === this.colorsList.length) {
            this.i = 0;
        }
        return this.colorsList[this.i++];
    }

    getTotalTransitions(): number {
        return this.colorsList.length;
    }

    static Builder = class {
        readonly builderList: StaticColor[] = [];
        loopedSteps: number = 0;

        constructor(startingColor: StaticColor) {
            this.builderList.push(startingColor);
        }

        addTransition(toColor: StaticColor, steps: number): this {
            if (steps < 2) {
                throw new Error(
                    "Minimum amount of steps in transition color is 2, requested: " + steps
                );
            }
            const transitionColor = new TransitionColor(
                this.builderList[this.builderList.length - 1],
                toColor,
                steps
            );
            // skip first color as it is already on list
            transitionColor.getColor();
            while (!transitionColor.transitionFinished()) {
                this.builderList.push(transitionColor.getColor());
            }
            return this;
        }

        looped(loopedSteps: number): this {
            this.loopedSteps = loopedSteps;
            return this;
        }

        build(): MultiTransitionColor {
            if (this.loopedSteps !== 0) {
                this.addTransition(this.builderList[0], this.loopedSteps);
                this.builderList.pop();
            }
            return new MultiTransitionColor(this.builderList);
        }
    };
}
