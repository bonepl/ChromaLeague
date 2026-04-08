import {ActivePlayerAssistAnimation} from '../hud/animations/ActivePlayerAssistAnimation.js';
import {ActivePlayerKillAnimation} from '../hud/animations/ActivePlayerKillAnimation.js';
import {AllyBaronKillAnimation} from '../hud/animations/AllyBaronKillAnimation.js';
import {AllyChemtechDragonKillAnimation} from '../hud/animations/AllyChemtechDragonKillAnimation.js';
import {AllyCloudDragonKillAnimation} from '../hud/animations/AllyCloudDragonKillAnimation.js';
import {AllyElderDragonKillAnimation} from '../hud/animations/AllyElderDragonKillAnimation.js';
import {AllyHeraldKillAnimation} from '../hud/animations/AllyHeraldKillAnimation.js';
import {AllyVoidGrubKillAnimation} from '../hud/animations/AllyVoidGrubKillAnimation.js';
import {AllyHextechDragonKillAnimation} from '../hud/animations/AllyHextechDragonKillAnimation.js';
import {AllyInfernalDragonKillAnimation} from '../hud/animations/AllyInfernalDragonKillAnimation.js';
import {AllyMountainDragonKillAnimation} from '../hud/animations/AllyMountainDragonKillAnimation.js';
import {AllyOceanDragonKillAnimation} from '../hud/animations/AllyOceanDragonKillAnimation.js';
import {EnemyBaronKillAnimation} from '../hud/animations/EnemyBaronKillAnimation.js';
import {EnemyChemtechDragonKillAnimation} from '../hud/animations/EnemyChemtechDragonKillAnimation.js';
import {EnemyCloudDragonKillAnimation} from '../hud/animations/EnemyCloudDragonKillAnimation.js';
import {EnemyElderDragonKillAnimation} from '../hud/animations/EnemyElderDragonKillAnimation.js';
import {EnemyHeraldKillAnimation} from '../hud/animations/EnemyHeraldKillAnimation.js';
import {EnemyVoidGrubKillAnimation} from '../hud/animations/EnemyVoidGrubKillAnimation.js';
import {EnemyHextechDragonKillAnimation} from '../hud/animations/EnemyHextechDragonKillAnimation.js';
import {EnemyInfernalDragonKillAnimation} from '../hud/animations/EnemyInfernalDragonKillAnimation.js';
import {EnemyMountainDragonKillAnimation} from '../hud/animations/EnemyMountainDragonKillAnimation.js';
import {EnemyOceanDragonKillAnimation} from '../hud/animations/EnemyOceanDragonKillAnimation.js';
import {LoseAnimation} from '../hud/animations/LoseAnimation.js';
import {WinAnimation} from '../hud/animations/WinAnimation.js';
import type {IFrame} from '../razer-sdk/animation/IFrame.js';
import {SimpleFrame} from '../razer-sdk/animation/SimpleFrame.js';
import type {Event} from '../rest/eventdata/Event.js';
import {EventType, eventTypeFromEvent} from '../rest/eventdata/EventType.js';
import {RunningState} from '../state/RunningState.js';

export function processNewEventAnimations(events: Event[]): void {
    for (const event of events) {
        processEventAnimation(eventTypeFromEvent(event));
    }
}

function processEventAnimation(eventType: EventType): void {
    const animation = getEventAnimation(eventType);
    RunningState.getGameState().mainHud.getEventAnimation().addAnimation(animation);
}

function getEventAnimation(eventType: EventType): IFrame {
    switch (eventType) {
        case EventType.ALLY_BARON_KILL: return new AllyBaronKillAnimation();
        case EventType.ALLY_HERALD_KILL: return new AllyHeraldKillAnimation();
        case EventType.ALLY_VOID_GRUB_KILL: return new AllyVoidGrubKillAnimation();
        case EventType.ALLY_CLOUD_DRAGON_KILL: return new AllyCloudDragonKillAnimation();
        case EventType.ALLY_CHEMTECH_DRAGON_KILL: return new AllyChemtechDragonKillAnimation();
        case EventType.ALLY_HEXTECH_DRAGON_KILL: return new AllyHextechDragonKillAnimation();
        case EventType.ALLY_INFERNAL_DRAGON_KILL: return new AllyInfernalDragonKillAnimation();
        case EventType.ALLY_OCEAN_DRAGON_KILL: return new AllyOceanDragonKillAnimation();
        case EventType.ALLY_MOUNTAIN_DRAGON_KILL: return new AllyMountainDragonKillAnimation();
        case EventType.ALLY_ELDER_DRAGON_KILL: return new AllyElderDragonKillAnimation();
        case EventType.ENEMY_BARON_KILL: return new EnemyBaronKillAnimation();
        case EventType.ENEMY_HERALD_KILL: return new EnemyHeraldKillAnimation();
        case EventType.ENEMY_VOID_GRUB_KILL: return new EnemyVoidGrubKillAnimation();
        case EventType.ENEMY_CLOUD_DRAGON_KILL: return new EnemyCloudDragonKillAnimation();
        case EventType.ENEMY_CHEMTECH_DRAGON_KILL: return new EnemyChemtechDragonKillAnimation();
        case EventType.ENEMY_HEXTECH_DRAGON_KILL: return new EnemyHextechDragonKillAnimation();
        case EventType.ENEMY_INFERNAL_DRAGON_KILL: return new EnemyInfernalDragonKillAnimation();
        case EventType.ENEMY_OCEAN_DRAGON_KILL: return new EnemyOceanDragonKillAnimation();
        case EventType.ENEMY_MOUNTAIN_DRAGON_KILL: return new EnemyMountainDragonKillAnimation();
        case EventType.ENEMY_ELDER_DRAGON_KILL: return new EnemyElderDragonKillAnimation();
        case EventType.GAME_END_VICTORY: return new WinAnimation();
        case EventType.GAME_END_DEFEAT: return new LoseAnimation();
        case EventType.ACTIVE_PLAYER_KILL: return new ActivePlayerKillAnimation();
        case EventType.ACTIVE_PLAYER_ASSIST: return new ActivePlayerAssistAnimation();
        case EventType.ACTIVE_PLAYER_DIED:
        case EventType.GAME_START:
        case EventType.UNSUPPORTED:
            return new SimpleFrame();
    }
}
