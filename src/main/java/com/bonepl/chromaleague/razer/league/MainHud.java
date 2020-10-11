package com.bonepl.chromaleague.razer.league;

import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.effects.keyboard.LayeredCustomEffect;
import com.bonepl.chromaleague.razer.league.hud.HpBar;
import com.bonepl.chromaleague.razer.league.hud.ResourceBar;
import com.bonepl.chromaleague.razer.league.json.LeagueHttpClient;
import com.bonepl.chromaleague.razer.league.json.activeplayer.ActivePlayerThread;

public class MainHud {
    private final ActivePlayerThread activePlayerThread;
    boolean alive = true;

    public MainHud() {
        activePlayerThread = new ActivePlayerThread(new LeagueHttpClient());
        activePlayerThread.start();
    }

    public void start() {
        try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
            while (alive) {
                final LayeredCustomEffect layeredCustomEffect = new LayeredCustomEffect();
                layeredCustomEffect.addCustomKeyboardEffect(new HpBar(activePlayerThread));
                layeredCustomEffect.addCustomKeyboardEffect(new ResourceBar(activePlayerThread));
                razerSDKClient.createKeyboardEffect(layeredCustomEffect);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
