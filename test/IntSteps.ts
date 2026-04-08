export class IntSteps {
    private readonly from: number;
    private readonly to: number;
    private readonly step: number;
    private current: number;
    private direction = 1;

    constructor(from: number, to: number, step: number) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.current = from;
    }

    nextInt(): number {
        const toReturn = this.current;
        const nextIncrement = this.step * this.direction;
        if (this.from < this.to) {
            if (this.current + nextIncrement > this.to || this.current + nextIncrement < this.from) {
                this.direction = -this.direction;
            }
        } else {
            if (this.current + nextIncrement < this.to || this.current + nextIncrement > this.from) {
                this.direction = -this.direction;
            }
        }
        this.current += this.step * this.direction;
        return toReturn;
    }
}
