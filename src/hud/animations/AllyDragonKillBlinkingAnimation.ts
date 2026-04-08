import {AnimatedFrame} from '../../razer-sdk/animation/AnimatedFrame.js';
import {SimpleFrame} from '../../razer-sdk/animation/SimpleFrame.js';
import {Color} from '../../razer-sdk/color/Color.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';
import {RzKey} from '../../razer-sdk/sdk/RzKey.js';
import {RzKeySelector} from '../../razer-sdk/sdk/RzKeySelector.js';
import {DragonType, dragonTypeGetColor, dragonTypeGetSoulColor} from '../../rest/eventdata/DragonType.js';

const BLINK_TIMES = 2;
const COLOR_LENGTH = 80;

export class AllyDragonKillBlinkingAnimation extends AnimatedFrame {
    constructor(dragonType: DragonType);
    constructor(rzKeys: RzKey[], dragonType: DragonType);
    constructor(rzKeysOrDragonType: RzKey[] | DragonType, dragonType?: DragonType) {
        super();
        let rzKeys: RzKey[];
        let dt: DragonType;

        if (Array.isArray(rzKeysOrDragonType)) {
            rzKeys = rzKeysOrDragonType;
            dt = dragonType!;
        } else {
            rzKeys = new RzKeySelector().asList();
            dt = rzKeysOrDragonType;
        }

        this.addBlinkingColor(rzKeys, dt);
        this.addDynamicColor(rzKeys, dt);
        this.addBlinkingColor(rzKeys, dt);
    }

    private addDynamicColor(rzKeys: RzKey[], dragonType: DragonType): void {
        const colorsMap = new Map<RzKey, Color>();
        for (const rk of rzKeys) {
            colorsMap.set(rk, dragonTypeGetSoulColor(dragonType));
        }
        for (let i = 0; i < COLOR_LENGTH; i++) {
            this.addAnimationFrame(new SimpleFrame(colorsMap));
        }
    }

    private addBlinkingColor(rzKeys: RzKey[], dragonType: DragonType): void {
        for (let i = 0; i < BLINK_TIMES; i++) {
            this.addAnimationFrame(new SimpleFrame(rzKeys, dragonTypeGetColor(dragonType)));
            this.addAnimationFrame(3, new SimpleFrame(rzKeys, StaticColor.BLACK));
        }
    }
}
