import {beforeEach, describe, test} from 'vitest';
import {DragonBar} from '../../../../src/hud/parts/dragons/DragonBar.js';
import {DragonType} from '../../../../src/rest/eventdata/DragonType.js';
import {Team} from '../../../../src/rest/playerlist/Team.js';
import {PlayerList} from '../../../../src/rest/playerlist/PlayerList.js';
import {RunningState} from '../../../../src/state/RunningState.js';
import {ChromaNativeSDK} from '../../../../src/razer-sdk/ChromaNativeSDK.js';

describe('DragonBar Integration', () => {
    beforeEach(() => {
        RunningState.setRunningGame(true);
        const gs = RunningState.getGameState();
        gs.gameStats = {
            gameMode: 'CLASSIC',
            gameTime: 1000,
            mapTerrain: 'Default',
        };
        gs.playerRiotId = 'test#1';
        gs.activePlayer = {
            riotId: 'test#1',
            riotIdGameName: 'test',
            currentGold: 0,
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
        gs.playerList = new PlayerList([
            { riotId: 'test#1', riotIdGameName: 'test', team: Team.ORDER, championName: 'Ahri', respawnTimer: 0, isDead: false },
        ]);
    });

    /** Integration test for DragonBar with killed dragons on actual hardware. */
    test('produces frames with killed dragons', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();
        try {
            const eventData = RunningState.getGameState().eventData;
            const dragonBar = new DragonBar();

            const dragonOrder = [DragonType.INFERNAL, DragonType.CLOUD, DragonType.OCEAN, DragonType.OCEAN];

            for (let i = 0; i < 150; i++) {
                if (i % 5 === 0 && dragonOrder.length > 0 && i / 5 < dragonOrder.length) {
                    eventData.addKilledDragon(dragonOrder[Math.trunc(i / 5)]);
                }
                await sdk.createKeyboardEffect(dragonBar);
                await new Promise(resolve => setTimeout(resolve, 50));
            }
        } finally {
            await sdk.close();
        }
    }, 30000);

    /** Integration test for DragonBar with elder buff active on actual hardware. */
    test('produces frames with elder buff active', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();
        try {
            const eventData = RunningState.getGameState().eventData;
            eventData.addKilledDragon(DragonType.INFERNAL);
            eventData.addKilledDragon(DragonType.CLOUD);
            eventData.addKilledDragon(DragonType.OCEAN);
            eventData.addKilledDragon(DragonType.OCEAN);
            eventData.elderBuffEnd = 2000;

            const dragonBar = new DragonBar();
            for (let i = 0; i < 60; i++) {
                await sdk.createKeyboardEffect(dragonBar);
                await new Promise(resolve => setTimeout(resolve, 50));
            }
        } finally {
            await sdk.close();
        }
    }, 30000);
});
