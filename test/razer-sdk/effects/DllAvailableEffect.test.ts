import {describe, test, expect} from 'vitest';
import {ChromaNativeSDK} from '../../../src/razer-sdk/ChromaNativeSDK.js';

/**
 * Integration test verifying the Razer Chroma SDK DLL is present on the system.
 */
describe('DllAvailable Integration', () => {
    test('isDllAvailable returns true when Razer Chroma SDK is installed', () => {
        const sdk = new ChromaNativeSDK();
        expect(sdk.isDllAvailable()).toBe(true);
    });
});
