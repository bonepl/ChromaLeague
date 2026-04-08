import {beforeEach, describe, test} from 'vitest';
import {GoldBar} from '../../../src/hud/parts/GoldBar.js';
import {RunningState} from '../../../src/state/RunningState.js';
import {ChromaNativeSDK} from '../../../src/razer-sdk/ChromaNativeSDK.js';

function mockActivePlayerGold(gold: number): void {
    const gs = RunningState.getGameState();
    gs.activePlayer = {
        riotId: 'test#1',
        riotIdGameName: 'test',
        currentGold: gold,
        level: 1,
        championStats: {
            attackRange: 550,
            currentHealth: 1000,
            maxHealth: 1000,
            resourceMax: 500,
            resourceValue: 500,
            resourceType: 'MANA',
        },
    };
}

describe('GoldBar Integration', () => {
    beforeEach(() => {
        RunningState.setRunningGame(true);
    });

    /** Integration test for GoldBar with increasing gold on actual hardware. */
    test('produces frames as gold increases', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();
        try {
            mockActivePlayerGold(0);
            const goldBar = new GoldBar(10, 150);

            for (let i = 0; i < 50; i++) {
                mockActivePlayerGold(i * 30);
                await sdk.createKeyboardEffect(goldBar);
                await new Promise(resolve => setTimeout(resolve, 50));
            }
        } finally {
            await sdk.close();
        }
    }, 30000);

    /** Integration test for GoldBar with default parameters on actual hardware. */
    test('creates gold bar with default parameters', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();
        try {
            mockActivePlayerGold(500);
            const goldBar = new GoldBar();
            await sdk.createKeyboardEffect(goldBar);
            await new Promise(resolve => setTimeout(resolve, 1000));
        } finally {
            await sdk.close();
        }
    }, 30000);
});
