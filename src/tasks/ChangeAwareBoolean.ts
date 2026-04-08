export class ChangeAwareBoolean {
    private value = false;
    private hasPendingChange = false;
    private promise: Promise<void> | null = null;
    private resolve: (() => void) | null = null;

    async waitForChange(): Promise<boolean> {
        if (this.hasPendingChange) {
            this.hasPendingChange = false;
            return this.value;
        }

        if (!this.promise) {
            this.promise = new Promise<void>(r => { this.resolve = r; });
        }

        await this.promise;
        this.hasPendingChange = false;
        return this.value;
    }

    reset(): void {
        this.value = false;
        this.hasPendingChange = false;
        if (this.resolve) {
            this.resolve();
            this.promise = null;
            this.resolve = null;
        }
    }

    different(other: boolean): boolean {
        return this.value !== other;
    }

    setValue(newValue: boolean): void {
        if (this.different(newValue)) {
            this.value = newValue;
            this.hasPendingChange = true;
            if (this.resolve) {
                this.resolve();
                this.promise = null;
                this.resolve = null;
            }
        }
    }

    getValue(): boolean {
        return this.value;
    }
}
