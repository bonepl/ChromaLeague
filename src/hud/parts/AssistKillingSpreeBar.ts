import {LayeredFrame} from '../../razer-sdk/animation/LayeredFrame.js';
import {SimpleFrame} from '../../razer-sdk/animation/SimpleFrame.js';
import {StaticColor} from '../../razer-sdk/color/StaticColor.js';
import {RzKey} from '../../razer-sdk/sdk/RzKey.js';
import * as GameStateHelper from '../../state/GameStateHelper.js';

const KILLING_SPREE_BAR: RzKey[] = [
    RzKey.RZKEY_M, RzKey.RZKEY_COMA, RzKey.RZKEY_DOT,
    RzKey.RZKEY_K, RzKey.RZKEY_L, RzKey.RZKEY_SEMICOLON,
    RzKey.RZKEY_O, RzKey.RZKEY_P, RzKey.RZKEY_SQUARE_BRACKET_LEFT,
];

export class AssistKillingSpreeBar extends LayeredFrame {
    constructor() {
        super();
        this.addFrame(new SimpleFrame(KILLING_SPREE_BAR.slice(0, computeAssistsIndex()), StaticColor.YELLOW));
        this.addFrame(new SimpleFrame(KILLING_SPREE_BAR.slice(0, computeKillsIndex()), StaticColor.RED));
    }
}

function computeAssistsIndex(): number {
    return Math.min(GameStateHelper.getActivePlayerAssistSpree(), KILLING_SPREE_BAR.length);
}

function computeKillsIndex(): number {
    return Math.min(GameStateHelper.getActivePlayerKillingSpree(), KILLING_SPREE_BAR.length);
}

export function getKillingSpreeBar(): RzKey[] {
    return [...KILLING_SPREE_BAR];
}
