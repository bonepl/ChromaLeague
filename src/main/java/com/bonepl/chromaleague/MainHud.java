package com.bonepl.chromaleague;

import com.bonepl.chromaleague.league.EventProcessor;
import com.bonepl.chromaleague.league.hud.*;
import com.bonepl.chromaleague.league.json.GameDetectionThread;
import com.bonepl.chromaleague.league.json.LeagueHttpClient;
import com.bonepl.chromaleague.league.json.activeplayer.ActivePlayerThread;
import com.bonepl.chromaleague.league.json.eventdata.EventDataThread;
import com.bonepl.chromaleague.league.json.playerlist.PlayerListThread;
import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.effects.keyboard.LayeredCustomEffect;

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
        final int hpPercentage = playerListThread.isActivePlayerDead() ? 0 : activePlayerThread.getHpPercentage();
        layeredCustomEffect.addCustomKeyboardEffect(new HpBar(hpPercentage));
        final int resourcePercentage = playerListThread.isActivePlayerDead() ? 0 : activePlayerThread.getResourcePercentage();
        ResourceType resourceType = activePlayerThread.getResourceType();
        layeredCustomEffect.addCustomKeyboardEffect(new ResourceBar(resourcePercentage, resourceType));
        layeredCustomEffect.addCustomKeyboardEffect(new GoldBar(activePlayerThread));
        razerSDKClient.createKeyboardEffect(layeredCustomEffect);
    }
}
