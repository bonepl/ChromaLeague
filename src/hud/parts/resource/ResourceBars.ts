import {IFrame} from '../../../razer-sdk/animation/IFrame.js';
import {RzKey} from '../../../razer-sdk/sdk/RzKey.js';
import {RzKeySelector} from '../../../razer-sdk/sdk/RzKeySelector.js';
import {RunningState} from '../../../state/RunningState.js';
import {BelVethLavenderBar} from './BelVethLavenderBar.js';
import {EnergyBar} from './EnergyBar.js';
import {GnarFuryBar} from './GnarFuryBar.js';
import {KledCourageBar} from './KledCourageBar.js';
import {ManaBar} from './ManaBar.js';
import {MordekaiserShieldBar} from './MordekaiserShieldBar.js';
import {NoResourceBar} from './NoResourceBar.js';
import {RedFuryBar} from './RedFuryBar.js';
import {RenektonFuryBar} from './RenektonFuryBar.js';
import {RengarFerocityBar} from './RengarFerocityBar.js';
import {RumbleHeatBar} from './RumbleHeatBar.js';
import {SettGritBar} from './SettGritBar.js';
import {ShyvanaDragonFuryBar} from './ShyvanaDragonFuryBar.js';
import {VladimirBloodPoolBar} from './VladimirBloodPoolBar.js';
import {YasuoWindBar} from './YasuoWindBar.js';
import {YoneCloneBar} from './YoneCloneBar.js';

const RESOURCE_BAR_KEYS: RzKey[] = new RzKeySelector()
    .withRowOf(RzKey.RZKEY_TILDE)
    .withColumnBetween(RzKey.RZKEY_TILDE, RzKey.RZKEY_BACKSPACE)
    .sortedByColumn()
    .asList();

const NO_MANA_BAR_CHAMPIONS = new Set([
    'Aatrox', 'Briar', 'Dr. Mundo', 'Garen', 'Katarina', 'Riven', 'Viego', 'Zac',
]);

const ENERGY_BAR_CHAMPIONS = new Set([
    'Akali', 'Ambessa', 'Kennen', 'Lee Sin', 'Shen', 'Zed',
]);

export function getResourceBarForActivePlayerChampion(): IFrame {
    const gameState = RunningState.getGameState();
    if (!gameState.playerList || !gameState.playerRiotId) return new ManaBar();
    const activePlayerChampionName = gameState.playerList
        .getActivePlayer(gameState.playerRiotId).championName;

    if (NO_MANA_BAR_CHAMPIONS.has(activePlayerChampionName)) {
        return new NoResourceBar();
    }

    if (ENERGY_BAR_CHAMPIONS.has(activePlayerChampionName)) {
        return new EnergyBar();
    }

    switch (activePlayerChampionName) {
        case "Bel'Veth": return new BelVethLavenderBar();
        case 'Gnar': return new GnarFuryBar();
        case 'Kled': return new KledCourageBar();
        case 'Mordekaiser': return new MordekaiserShieldBar();
        case 'Renekton': return new RenektonFuryBar();
        case 'Rengar': return new RengarFerocityBar();
        case "Rek'Sai":
        case 'Tryndamere': return new RedFuryBar();
        case 'Rumble': return new RumbleHeatBar();
        case 'Sett': return new SettGritBar();
        case 'Shyvana': return new ShyvanaDragonFuryBar();
        case 'Vladimir': return new VladimirBloodPoolBar();
        case 'Yasuo': return new YasuoWindBar();
        case 'Yone': return new YoneCloneBar();
        default: return new ManaBar();
    }
}

export function getResourceBarKeys(): readonly RzKey[] {
    return RESOURCE_BAR_KEYS;
}

export function getEnergyBarChampions(): ReadonlySet<string> {
    return ENERGY_BAR_CHAMPIONS;
}
