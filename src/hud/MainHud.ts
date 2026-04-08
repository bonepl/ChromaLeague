import {AnimatedFrame} from '../razer-sdk/animation/AnimatedFrame.js';
import {Frame} from '../razer-sdk/animation/Frame.js';
import {IFrame} from '../razer-sdk/animation/IFrame.js';
import {LayeredFrame} from '../razer-sdk/animation/LayeredFrame.js';
import * as GameStateHelper from '../state/GameStateHelper.js';
import {LevelUpAnimation} from './animations/LevelUpAnimation.js';
import {RespawnAnimation} from './animations/RespawnAnimation.js';
import {RiftAnimation} from './animations/RiftAnimation.js';
import {AssistKillingSpreeBar} from './parts/AssistKillingSpreeBar.js';
import {Background} from './parts/Background.js';
import {DragonBar} from './parts/dragons/DragonBar.js';
import {EventAnimation} from './parts/EventAnimation.js';
import {GoldBar} from './parts/GoldBar.js';
import {HealthBar} from './parts/health/HealthBar.js';
import {getResourceBarForActivePlayerChampion} from './parts/resource/ResourceBars.js';

export class MainHud extends AnimatedFrame {
    private readonly goldBar = new GoldBar();
    private readonly levelUpAnimation = new LevelUpAnimation();
    private readonly eventAnimation = new EventAnimation();
    private readonly dragonBar = new DragonBar();
    private readonly healthBar = new HealthBar();
    private resourceBar: IFrame | null = null;

    override pollFrame(): Frame {
        const mainHudFrame = new LayeredFrame();
        mainHudFrame.addFrame(new Background());
        if (GameStateHelper.shouldPlayRiftAnimation()) {
            this.eventAnimation.addAnimation(new RiftAnimation());
        }
        if (GameStateHelper.isActivePlayerAlive()) {
            mainHudFrame.addFrame(this.healthBar);
            mainHudFrame.addFrame(this.getResourceBar());
        }
        mainHudFrame.addFrame(new AssistKillingSpreeBar());
        mainHudFrame.addFrame(this.dragonBar);
        mainHudFrame.addFrame(this.goldBar);
        mainHudFrame.addFrame(this.levelUpAnimation);
        if (GameStateHelper.shouldPlayRespawnAnimation()) {
            this.eventAnimation.addAnimation(new RespawnAnimation());
        }
        if (this.eventAnimation.hasFrame()) {
            mainHudFrame.addFrame(this.eventAnimation);
        }
        this.addAnimationFrame(mainHudFrame);
        return super.pollFrame();
    }

    private getResourceBar(): IFrame {
        if (this.resourceBar === null) {
            this.resourceBar = getResourceBarForActivePlayerChampion();
        }
        return this.resourceBar;
    }

    getEventAnimation(): EventAnimation {
        return this.eventAnimation;
    }
}
