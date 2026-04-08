import {getColumn, getRow, RzKey} from './RzKey.js';
import {keyAt} from './RzKeyArray.js';

export class CircularRzKeySelector {
    /** Number of ripple layers to pre-compute outward from the starting key, covering the full keyboard */
    private static readonly MAX_CIRCUIT_LAYERS = 30;
    private readonly thickness: number;
    private currentStep = 0;
    private readonly layers: Set<RzKey>[];

    constructor(startingKey: RzKey, thickness = 1) {
        this.thickness = thickness;
        this.layers = this.buildLayers(startingKey);
    }

    private buildLayers(startingKey: RzKey): Set<RzKey>[] {
        const list: Set<RzKey>[] = [];
        const allPreviousKeys = new Set<RzKey>([startingKey]);
        list.push(new Set([startingKey]));

        for (let i = 0; i < CircularRzKeySelector.MAX_CIRCUIT_LAYERS; i++) {
            const previousLayer = list[i];
            const newLayer = new Set<RzKey>();

            for (const rzKey of previousLayer) {
                const row = getRow(rzKey);
                const col = getColumn(rzKey);
                const neighbors = [
                    keyAt(row - 1, col - 1),
                    keyAt(row - 1, col),
                    keyAt(row - 1, col + 1),
                    keyAt(row + 1, col - 1),
                    keyAt(row + 1, col),
                    keyAt(row + 1, col + 1),
                    keyAt(row, col - 1),
                    keyAt(row, col + 1),
                ];
                for (const neighbor of neighbors) {
                    if (neighbor !== undefined) {
                        newLayer.add(neighbor);
                    }
                }
            }

            for (const key of allPreviousKeys) {
                newLayer.delete(key);
            }
            for (const key of newLayer) {
                allPreviousKeys.add(key);
            }

            list.push(newLayer);
        }

        return list;
    }

    getNextLayer(): Set<RzKey> {
        if (this.currentStep !== this.layers.length - 1) {
            this.currentStep++;
        }

        const skipped = this.countSkippedLayers(this.currentStep, this.thickness);
        const result = new Set<RzKey>();

        for (let i = skipped; i < this.currentStep; i++) {
            for (const key of this.layers[i]) {
                result.add(key);
            }
        }

        return result;
    }

    private countSkippedLayers(currentStep: number, thickness: number): number {
        if (currentStep > thickness) {
            return currentStep - thickness;
        }
        return 0;
    }
}
