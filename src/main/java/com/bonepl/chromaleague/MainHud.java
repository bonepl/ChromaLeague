package com.bonepl.chromaleague;

import com.bonepl.chromaleague.league.EventProcessor;
import com.bonepl.chromaleague.league.GameState;
import com.bonepl.chromaleague.league.hud.Background;
import com.bonepl.chromaleague.league.hud.GoldBar;
import com.bonepl.chromaleague.league.hud.HpBar;
import com.bonepl.chromaleague.league.hud.ResourceBar;
import com.bonepl.chromaleague.league.rest.activeplayer.FetchActivePlayerTask;
import com.bonepl.chromaleague.league.rest.activeplayer.model.ChampionStats;
import com.bonepl.chromaleague.league.rest.activeplayername.FetchActivePlayerNameTask;
import com.bonepl.chromaleague.league.rest.eventdata.FetchNewEventsTask;
import com.bonepl.chromaleague.league.rest.playerlist.FetchPlayerListTask;
import com.bonepl.chromaleague.league.rest.playerlist.model.Player;
import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.effects.keyboard.LayeredCustomEffect;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainHud {
    private static ScheduledExecutorService scheduledExecutorService;
    boolean alive = true;

    public void start() {
        final ScheduledExecutorService gameActiveExecutor = Executors.newSingleThreadScheduledExecutor();
        gameActiveExecutor.scheduleAtFixedRate(new FetchActivePlayerNameTask(), 0, 1000, TimeUnit.MILLISECONDS);

        while (alive) {
            if (GameState.isGameActive()) {
                initializeThreads();
                try (RazerSDKClient razerSDKClient = new RazerSDKClient()) {
                    while (GameState.isGameActive()) {
                        if (GameState.hasUnprocessedEvents()) {
                            EventProcessor.processEvents(razerSDKClient);
                        }
                        drawHud(razerSDKClient);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                shutdownThreads();
            }
        }

        gameActiveExecutor.shutdown();
    }

    private void initializeThreads() {
        scheduledExecutorService = Executors.newScheduledThreadPool(5);
        scheduledExecutorService.scheduleAtFixedRate(new FetchPlayerListTask(), 60, 1000, TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleAtFixedRate(new FetchActivePlayerTask(), 110, 300, TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleAtFixedRate(new FetchNewEventsTask(), 160, 1000, TimeUnit.MILLISECONDS);
    }

    private void shutdownThreads() {
        if (scheduledExecutorService != null && !scheduledExecutorService.isShutdown())
            scheduledExecutorService.shutdown();
    }

    private void drawHud(RazerSDKClient razerSDKClient) {
        final LayeredCustomEffect layeredCustomEffect = new LayeredCustomEffect();
        layeredCustomEffect.addCustomKeyboardEffect(new Background());
        final int hpPercentage = isActivePlayerDead() ? 0 : getHpPercentage();
        layeredCustomEffect.addCustomKeyboardEffect(new HpBar(hpPercentage));
        final int resourcePercentage = isActivePlayerDead() ? 0 : getResourcePercentage();
        layeredCustomEffect.addCustomKeyboardEffect(new ResourceBar(resourcePercentage));
        layeredCustomEffect.addCustomKeyboardEffect(new GoldBar(getGoldPercentage()));
        razerSDKClient.createKeyboardEffect(layeredCustomEffect);
    }

    public boolean isActivePlayerDead() {
        if (GameState.isPlayerListAvailable()) {
            final Player activePlayer = GameState.getPlayerList().getActivePlayer();
            if (activePlayer != null) {
                return activePlayer.isDead();
            }
        }
        return false;
    }

    public int getHpPercentage() {
        if (!isActivePlayerDead() && GameState.isActivePlayerAvailable()) {
            final ChampionStats championStats = GameState.getActivePlayer().getChampionStats();
            if (championStats != null) {
                return (int) Math.floor((championStats.getCurrentHealth() / championStats.getMaxHealth()) * 100);
            }
        }
        return 0;
    }

    public int getResourcePercentage() {
        if (!isActivePlayerDead() && GameState.isActivePlayerAvailable()) {
            final ChampionStats championStats = GameState.getActivePlayer().getChampionStats();
            if (championStats != null) {
                return (int) Math.floor((championStats.getResourceValue() / championStats.getResourceMax()) * 100);
            }
        }
        return 0;
    }

    public int getGoldPercentage() {
        if (GameState.isActivePlayerAvailable()) {
            return (int) Math.floor((GameState.getActivePlayer().getCurrentGold() / GoldBar.GOLD_FULL) * 100);
        }
        return 0;
    }
}
