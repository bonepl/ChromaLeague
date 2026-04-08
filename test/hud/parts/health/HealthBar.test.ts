import {beforeEach, describe, test} from 'vitest';
import {HealthBar} from '../../../../src/hud/parts/health/HealthBar.js';
import {RunningState} from '../../../../src/state/RunningState.js';
import {ChromaNativeSDK} from '../../../../src/razer-sdk/ChromaNativeSDK.js';

function mockActivePlayerHealth(currentHealth: number, maxHealth: number): void {
    RunningState.getGameState().activePlayer = {
        riotId: 'test#1',
        riotIdGameName: 'test',
        currentGold: 0,
        level: 1,
        championStats: {
            attackRange: 550,
            currentHealth,
            maxHealth,
            resourceMax: 500,
            resourceValue: 500,
            resourceType: 'MANA',
        },
    };
}

describe('HealthBar Integration', () => {
    beforeEach(() => {
        RunningState.setRunningGame(true);
    });

    /** Integration test for HealthBar lost health animation on actual hardware. */
    test('hp bar lost health animation', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();
        try {
            mockActivePlayerHealth(1000, 1000);
            const healthBar = new HealthBar();

            for (let i = 0; i < 60; i++) {
                if (i % 5 === 0) {
                    const currentHealth = 1000 - i * 20;
                    if (currentHealth >= 0) {
                        mockActivePlayerHealth(currentHealth, 1000);
                    }
                }
                await sdk.createKeyboardEffect(healthBar);
                await new Promise(resolve => setTimeout(resolve, 50));
            }
        } finally {
            await sdk.close();
        }
    }, 30000);

    /** Integration test for HealthBar gained health animation on actual hardware. */
    test('hp bar gained health animation', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();
        try {
            mockActivePlayerHealth(0, 1000);
            const healthBar = new HealthBar();

            for (let i = 0; i < 60; i++) {
                if (i % 5 === 0) {
                    const currentHealth = i * 20;
                    if (currentHealth <= 1000) {
                        mockActivePlayerHealth(currentHealth, 1000);
                    }
                }
                await sdk.createKeyboardEffect(healthBar);
                await new Promise(resolve => setTimeout(resolve, 50));
            }
        } finally {
            await sdk.close();
        }
    }, 30000);
});
