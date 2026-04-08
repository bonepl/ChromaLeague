import {Color} from "./Color.js";

const MIN_COLOR_VALUE = 0;
const MAX_COLOR_VALUE = 255;

const GREEN_BIT_POS = 8;
const BLUE_BIT_POS = 16;

function validatedColorRange(colorName: string, value: number): number {
    if (value < MIN_COLOR_VALUE || value > MAX_COLOR_VALUE) {
        throw new Error(
            `${colorName} value of provided color is out of range (expected: 0-255, actual: ${value})`
        );
    }
    return value;
}

export class StaticColor implements Color {
    static readonly NONE = new StaticColor(0, 0, 0);
    static readonly WHITE = new StaticColor(255, 255, 255);
    static readonly RED = new StaticColor(255, 0, 0);
    static readonly GREEN = new StaticColor(0, 255, 0);
    static readonly BLUE = new StaticColor(0, 0, 255);
    static readonly BLACK = new StaticColor(0, 0, 0);
    static readonly YELLOW = new StaticColor(255, 255, 0);
    static readonly CYAN = new StaticColor(0, 255, 255);
    static readonly BROWN = new StaticColor(130, 50, 0);
    static readonly PURPLE = new StaticColor(200, 0, 200);
    static readonly ORANGE = new StaticColor(255, 165, 0);
    static readonly GRAY = new StaticColor(100, 100, 100);

    readonly red: number;
    readonly green: number;
    readonly blue: number;

    constructor(red: number, green: number, blue: number) {
        this.red = validatedColorRange("red", red);
        this.green = validatedColorRange("green", green);
        this.blue = validatedColorRange("blue", blue);
    }

    forChroma(): number {
        return this.blue << BLUE_BIT_POS | this.green << GREEN_BIT_POS | this.red;
    }

    getColor(): StaticColor {
        return this;
    }
}
