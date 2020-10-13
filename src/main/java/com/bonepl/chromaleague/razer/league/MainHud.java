package com.bonepl.chromaleague.razer.league;

import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.effects.keyboard.LayeredCustomEffect;
import com.bonepl.chromaleague.razer.league.hud.Background;
import com.bonepl.chromaleague.razer.league.hud.GoldBar;
import com.bonepl.chromaleague.razer.league.hud.HpBar;
import com.bonepl.chromaleague.razer.league.hud.ResourceBar;
import com.bonepl.chromaleague.razer.league.json.GameDetectionThread;
import com.bonepl.chromaleague.razer.league.json.LeagueHttpClient;
import com.bonepl.chromaleague.razer.league.json.activeplayer.ActivePlayerThread;
import com.bonepl.chromaleague.razer.league.json.eventdata.EventDataThread;
import com.bonepl.chromaleague.razer.league.json.playerlist.PlayerListThread;

public class MainHud {
    private final ActivePlayerThread activePlayerThread;
    private final EventDataThread eventDataThread;
    private final PlayerListThread playerListThread;
    boolean alive = true;

    public MainHud() {
        GameDetectionThread.runThread(new LeagueHttpClient());
        activePlayerThread = new ActivePlayerThread(new LeagueHttpClient());
        activePlayerThread.start();
        eventDataThread = new EventDataThread(new LeagueHttpClient());
        eventDataThread.start();
        playerListThread = new PlayerListThread(new LeagueHttpClient());
        playerListThread.start();
    }

    public void start() {
        while (alive) {
            if (GameDetectionThread.isGameActive()) {
                try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
                    while (GameDetectionThread.isGameActive()) {
                        if (eventDataThread.hasUnprocessedEvents()) {
                            EventProcessor.processEvents(eventDataThread, razerSDKClient);
                        }
                        drawHud(razerSDKClient);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void drawHud(RazerSDKClient razerSDKClient) {
        final LayeredCustomEffect layeredCustomEffect = new LayeredCustomEffect();
        layeredCustomEffect.addCustomKeyboardEffect(new Background());
        layeredCustomEffect.addCustomKeyboardEffect(new HpBar(activePlayerThread));
        layeredCustomEffect.addCustomKeyboardEffect(new ResourceBar(activePlayerThread));
        layeredCustomEffect.addCustomKeyboardEffect(new GoldBar(activePlayerThread));
        razerSDKClient.createKeyboardEffect(layeredCustomEffect);
    }
}
