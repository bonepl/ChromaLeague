package com.bonepl.chromaleague.league.hud;

import com.bonepl.chromaleague.league.EventProcessor;
import com.bonepl.chromaleague.league.GameState;
import com.bonepl.chromaleague.league.rest.activeplayer.model.ChampionStats;
import com.bonepl.chromaleague.league.rest.playerlist.model.Player;
import com.bonepl.chromaleague.razer.RazerSDKClient;
import com.bonepl.chromaleague.razer.effects.keyboard.LayeredCustomEffect;

public class RefreshMainHudTask implements Runnable {
    private final RazerSDKClient razerSDKClient;

    public RefreshMainHudTask(RazerSDKClient razerSDKClient) {
        this.razerSDKClient = razerSDKClient;
    }

    @Override
    public void run() {
        if (GameState.hasUnprocessedEvents()) {
            EventProcessor.processEvents(razerSDKClient);
        }
        drawHud();
    }

    private void drawHud() {
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
