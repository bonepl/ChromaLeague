import {beforeEach, describe, test} from 'vitest';
import {KilledDragonsBar} from '../../../../src/hud/parts/dragons/KilledDragonsBar.js';
import {DragonType} from '../../../../src/rest/eventdata/DragonType.js';
import {RunningState} from '../../../../src/state/RunningState.js';
import {ChromaNativeSDK} from '../../../../src/razer-sdk/ChromaNativeSDK.js';

async function addDragonsOverIterations(sdk: ChromaNativeSDK, killedDragonsBar: KilledDragonsBar, dragonOrder: DragonType[]): Promise<void> {
    const eventData = RunningState.getGameState().eventData;
    let dragonIndex = 0;
    for (let i = 0; i < 80; i++) {
        if (i % 5 === 0 && dragonIndex < dragonOrder.length) {
            eventData.addKilledDragon(dragonOrder[dragonIndex++]);
        }
        await sdk.createKeyboardEffect(killedDragonsBar);
        await new Promise(resolve => setTimeout(resolve, 50));
    }
}

describe('KilledDragonsBar Integration', () => {
    beforeEach(() => {
        RunningState.setRunningGame(true);
    });

    /** Integration test for KilledDragonsBar with ocean dragons and soul on actual hardware. */
    test('ocean dragons with soul', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();
        try {
            const bar = new KilledDragonsBar();
            await addDragonsOverIterations(sdk, bar, [DragonType.INFERNAL, DragonType.CLOUD, DragonType.OCEAN, DragonType.OCEAN]);
        } finally {
            await sdk.close();
        }
    }, 30000);

    /** Integration test for KilledDragonsBar with infernal dragons and soul on actual hardware. */
    test('infernal dragons with soul', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();
        try {
            const bar = new KilledDragonsBar();
            await addDragonsOverIterations(sdk, bar, [DragonType.OCEAN, DragonType.MOUNTAIN, DragonType.INFERNAL, DragonType.INFERNAL]);
        } finally {
            await sdk.close();
        }
    }, 30000);

    /** Integration test for KilledDragonsBar with cloud dragons and soul on actual hardware. */
    test('cloud dragons with soul', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();
        try {
            const bar = new KilledDragonsBar();
            await addDragonsOverIterations(sdk, bar, [DragonType.MOUNTAIN, DragonType.OCEAN, DragonType.CLOUD, DragonType.CLOUD]);
        } finally {
            await sdk.close();
        }
    }, 30000);

    /** Integration test for KilledDragonsBar with mountain dragons and soul on actual hardware. */
    test('mountain dragons with soul', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();
        try {
            const bar = new KilledDragonsBar();
            await addDragonsOverIterations(sdk, bar, [DragonType.CLOUD, DragonType.INFERNAL, DragonType.MOUNTAIN, DragonType.MOUNTAIN]);
        } finally {
            await sdk.close();
        }
    }, 30000);

    /** Integration test for KilledDragonsBar with chemtech dragons and soul on actual hardware. */
    test('chemtech dragons with soul', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();
        try {
            const bar = new KilledDragonsBar();
            await addDragonsOverIterations(sdk, bar, [DragonType.HEXTECH, DragonType.INFERNAL, DragonType.CHEMTECH, DragonType.CHEMTECH]);
        } finally {
            await sdk.close();
        }
    }, 30000);

    /** Integration test for KilledDragonsBar with hextech dragons and soul on actual hardware. */
    test('hextech dragons with soul', async () => {
        const sdk = new ChromaNativeSDK();
        await sdk.connect();
        try {
            const bar = new KilledDragonsBar();
            await addDragonsOverIterations(sdk, bar, [DragonType.CHEMTECH, DragonType.INFERNAL, DragonType.HEXTECH, DragonType.HEXTECH]);
        } finally {
            await sdk.close();
        }
    }, 30000);
});
