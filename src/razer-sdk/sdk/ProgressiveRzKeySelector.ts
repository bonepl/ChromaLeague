import {RzKey} from './RzKey.js';

export class ProgressiveRzKeySelector {
    private readonly length: number;
    private readonly parts: ReadonlySet<RzKey>[];
    private currentStep = 0;

    constructor(parts: ReadonlySet<RzKey>[], length = 1) {
        if (!parts || parts.length === 0) {
            throw new Error('There must be at least one part');
        }
        if (length <= 0) {
            throw new Error('Length must be a positive number');
        }
        if (length > parts.length) {
            throw new Error('Length cannot be larger than number of parts');
        }
        this.parts = parts;
        this.length = length;
    }

    getNextPart(): Set<RzKey> {
        const span = this.length - 1;
        if (this.currentStep === this.parts.length + span) {
            this.currentStep = 0;
        }

        let returnedPart: Set<RzKey>;
        if (this.currentStep - span <= 0) {
            returnedPart = this.joinSets(0, this.currentStep);
        } else if (this.currentStep >= this.parts.length - 1) {
            returnedPart = this.joinSets(this.currentStep - span, this.parts.length - 1);
        } else {
            returnedPart = this.joinSets(this.currentStep - span, this.currentStep);
        }

        this.currentStep++;
        return returnedPart;
    }

    getTotalSteps(): number {
        return this.parts.length + this.length - 1;
    }

    private joinSets(startIndex: number, endIndex: number): Set<RzKey> {
        const result = new Set<RzKey>();
        for (let i = startIndex; i <= endIndex; i++) {
            for (const key of this.parts[i]) {
                result.add(key);
            }
        }
        return result;
    }
}

export class ProgressiveRzKeySelectorBuilder {
    private readonly parts: Set<RzKey>[] = [];
    private length = 1;

    addPart(rzKeys: Iterable<RzKey>): this {
        this.parts.push(new Set(rzKeys));
        return this;
    }

    addPartFromKeys(...rzKeys: RzKey[]): this {
        return this.addPart(rzKeys);
    }

    withLength(length: number): this {
        this.length = length;
        return this;
    }

    build(): ProgressiveRzKeySelector {
        return new ProgressiveRzKeySelector(this.parts, this.length);
    }
}
