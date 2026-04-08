import {beforeEach, describe, test} from 'vitest';
import {AssistKillingSpreeBar} from '../../../src/hud/parts/AssistKillingSpreeBar.js';
import {RunningState} from '../../../src/state/RunningState.js';
import {ChromaNativeSDK} from '../../../src/razer-sdk/ChromaNativeSDK.js';

describe('AssistKillingSpreeBar Integration', () => {
    beforeEach(() => {
        RunningState.setRunningGame(true);
    });

    /** Integration test for AssistKillingSpreeBar with no kills or assists on actual hardware. */
    test('creates bar with no kills or assists', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();
        try {
            const bar = new AssistKillingSpreeBar();
            await sdk.createKeyboardEffect(bar);
            await new Promise(resolve => setTimeout(resolve, 1000));
        } finally {
            await sdk.close();
        }
    }, 30000);

    /** Integration test for AssistKillingSpreeBar with assists on actual hardware. */
    test('creates bar reflecting assists', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();
        try {
            const eventData = RunningState.getGameState().eventData;
            eventData.activePlayerAssistSpree = 3;

            const bar = new AssistKillingSpreeBar();
            await sdk.createKeyboardEffect(bar);
            await new Promise(resolve => setTimeout(resolve, 1000));
        } finally {
            await sdk.close();
        }
    }, 30000);

    /** Integration test for AssistKillingSpreeBar with kills on top of assists on actual hardware. */
    test('creates bar reflecting kills on top of assists', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();
        try {
            const eventData = RunningState.getGameState().eventData;
            eventData.activePlayerAssistSpree = 5;
            eventData.activePlayerKillingSpree = 2;

            const bar = new AssistKillingSpreeBar();
            // kills layer (2 keys) overwrites part of assists layer (5 keys), total unique = 5
            await sdk.createKeyboardEffect(bar);
            await new Promise(resolve => setTimeout(resolve, 1000));
        } finally {
            await sdk.close();
        }
    }, 30000);

    /** Integration test for AssistKillingSpreeBar at max capacity on actual hardware. */
    test('bar caps at max killing spree bar length', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();
        try {
            const eventData = RunningState.getGameState().eventData;
            eventData.activePlayerAssistSpree = 20;
            eventData.activePlayerKillingSpree = 20;

            const bar = new AssistKillingSpreeBar();
            // KILLING_SPREE_BAR has 9 keys max
            await sdk.createKeyboardEffect(bar);
            await new Promise(resolve => setTimeout(resolve, 1000));
        } finally {
            await sdk.close();
        }
    }, 30000);
});
