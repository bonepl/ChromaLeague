import {AnimatedFrame} from '../../../razer-sdk/animation/AnimatedFrame.js';
import {Frame} from '../../../razer-sdk/animation/Frame.js';
import {SimpleFrame} from '../../../razer-sdk/animation/SimpleFrame.js';
import {Color} from '../../../razer-sdk/color/Color.js';
import {RzKey} from '../../../razer-sdk/sdk/RzKey.js';
import {DragonType, dragonTypeGetColor, dragonTypeGetSoulColor} from '../../../rest/eventdata/DragonType.js';
import * as GameStateHelper from '../../../state/GameStateHelper.js';

export const FIRST_DRAGON_ROW: RzKey[] = [RzKey.RZKEY_BACKSLASH];
export const SECOND_DRAGON_ROW: RzKey[] = [RzKey.RZKEY_ENTER];
export const THIRD_DRAGON_ROW: RzKey[] = [RzKey.RZKEY_RSHIFT];
export const FOURTH_DRAGON_ROW: RzKey[] = [RzKey.RZKEY_FN, RzKey.RZKEY_RMENU, RzKey.RZKEY_RCTRL];
export const SOUL_BAR: RzKey[] = [
    RzKey.RZKEY_LCTRL, RzKey.RZKEY_LWIN, RzKey.RZKEY_LALT, RzKey.RZKEY_SPACE, RzKey.RZKEY_RALT,
    RzKey.RZKEY_UP, RzKey.RZKEY_LEFT, RzKey.RZKEY_DOWN, RzKey.RZKEY_RIGHT,
];

const DRAGON_ROWS: RzKey[][] = [FIRST_DRAGON_ROW, SECOND_DRAGON_ROW, THIRD_DRAGON_ROW, FOURTH_DRAGON_ROW];

export class KilledDragonsBar extends AnimatedFrame {
    private soulMode = false;
    private readonly dragonColorsMap = new Map<RzKey, Color>();

    override pollFrame(): Frame {
        this.addAnimationFrame(new SimpleFrame(this.getKilledDragonsBar()));
        return super.pollFrame();
    }

    private getKilledDragonsBar(): Map<RzKey, Color> {
        const killedDragonsBar = new Map<RzKey, Color>();
        const killedDragons = GameStateHelper.getKilledDragons();
        const dragonSoul = getDragonSoul(killedDragons);

        if (dragonSoul !== null && !this.soulMode) {
            for (let i = 0; i < 4; i++) {
                const dt = getDragonType(i);
                if (dt !== null && dt === dragonSoul) {
                    this.swapWithDragonSoulColor(dragonSoul, DRAGON_ROWS[i]);
                }
            }
            this.swapWithDragonSoulColor(dragonSoul, SOUL_BAR);
            this.soulMode = true;
        }

        for (let i = 0; i < 4; i++) {
            const dt = getDragonType(i);
            if (dt !== null) {
                for (const [key, color] of this.computeDragonColor(dt, DRAGON_ROWS[i])) {
                    killedDragonsBar.set(key, color);
                }
            }
        }

        if (dragonSoul !== null) {
            for (const [key, color] of this.computeDragonColor(dragonSoul, SOUL_BAR)) {
                killedDragonsBar.set(key, color);
            }
        }

        return killedDragonsBar;
    }

    private swapWithDragonSoulColor(dragonType: DragonType, keys: RzKey[]): void {
        for (const rzKey of keys) {
            this.dragonColorsMap.set(rzKey, dragonTypeGetSoulColor(dragonType));
        }
    }

    private computeDragonColor(dragonType: DragonType, keys: RzKey[]): Map<RzKey, Color> {
        const currentDragonColorMap = new Map<RzKey, Color>();
        for (const rzKey of keys) {
            if (!this.dragonColorsMap.has(rzKey)) {
                this.dragonColorsMap.set(rzKey, dragonTypeGetColor(dragonType));
            }
        }
        for (const rzKey of keys) {
            currentDragonColorMap.set(rzKey, this.dragonColorsMap.get(rzKey)!);
        }
        return currentDragonColorMap;
    }
}

function getDragonType(index: number): DragonType | null {
    const killedDragons = GameStateHelper.getKilledDragons();
    if (killedDragons.length > index) {
        return killedDragons[index];
    }
    return null;
}

function getDragonSoul(killedDragons: readonly DragonType[]): DragonType | null {
    if (killedDragons.length > 3) {
        return killedDragons[3];
    }
    return null;
}
