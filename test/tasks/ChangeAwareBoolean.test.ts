import {describe, expect, test} from 'vitest';
import {ChangeAwareBoolean} from '../../src/tasks/ChangeAwareBoolean.js';

describe('ChangeAwareBoolean', () => {
    test('waitForChange returns immediately when pending change exists', async () => {
        const cab = new ChangeAwareBoolean();
        cab.setValue(true);

        const result = await cab.waitForChange();

        expect(result).toBe(true);
    });

    test('waitForChange waits until setValue is called', async () => {
        const cab = new ChangeAwareBoolean();
        let resolved = false;

        const waiter = cab.waitForChange().then(val => {
            resolved = true;
            return val;
        });

        expect(resolved).toBe(false);
        cab.setValue(true);

        const result = await waiter;
        expect(resolved).toBe(true);
        expect(result).toBe(true);
    });

    test('setValue with same value does not trigger change', async () => {
        const cab = new ChangeAwareBoolean();
        cab.setValue(false); // same as default

        // No pending change, so waitForChange would block — we verify via getValue
        expect(cab.getValue()).toBe(false);

        // Confirm no pending change by checking hasPendingChange indirectly:
        // setValue(true) should work after this
        cab.setValue(true);
        const result = await cab.waitForChange();
        expect(result).toBe(true);
    });

    test('reset sets value to false and unblocks waiting caller', async () => {
        const cab = new ChangeAwareBoolean();
        cab.setValue(true);
        await cab.waitForChange(); // consume pending change

        const waiter = cab.waitForChange();
        cab.reset();

        const result = await waiter;
        expect(result).toBe(false);
        expect(cab.getValue()).toBe(false);
    });

    test('getValue returns current value without consuming pending change', () => {
        const cab = new ChangeAwareBoolean();
        cab.setValue(true);

        expect(cab.getValue()).toBe(true);
        expect(cab.getValue()).toBe(true); // still true, not consumed
    });

    test('different returns true only when value differs', () => {
        const cab = new ChangeAwareBoolean();

        expect(cab.different(true)).toBe(true);
        expect(cab.different(false)).toBe(false);

        cab.setValue(true);
        expect(cab.different(true)).toBe(false);
        expect(cab.different(false)).toBe(true);
    });

    test('sequential changes: true then false', async () => {
        const cab = new ChangeAwareBoolean();

        cab.setValue(true);
        expect(await cab.waitForChange()).toBe(true);

        cab.setValue(false);
        expect(await cab.waitForChange()).toBe(false);
    });
});
