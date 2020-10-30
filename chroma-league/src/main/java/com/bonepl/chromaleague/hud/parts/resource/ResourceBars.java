package com.bonepl.chromaleague.hud.parts.resource;

import com.bonepl.chromaleague.state.RunningState;
import com.bonepl.razersdk.animation.IFrame;
import com.bonepl.razersdk.sdk.RzKey;
import com.bonepl.razersdk.sdk.RzKeySelector;

import java.util.Collections;
import java.util.List;

import static com.bonepl.razersdk.sdk.RzKey.RZKEY_BACKSPACE;
import static com.bonepl.razersdk.sdk.RzKey.RZKEY_TILDE;

public final class ResourceBars {
    private static final List<RzKey> RESOURCE_BAR_KEYS = new RzKeySelector()
            .withRowOf(RZKEY_TILDE)
            .withColumnBetween(RZKEY_TILDE, RZKEY_BACKSPACE)
            .sortedByColumn()
            .asList();

    private static final List<String> NO_MANA_BAR_CHAMPIONS
            = List.of("Aatrox", "Dr. Mundo", "Garen", "Katarina", "Riven", "Yone", "Zac");
    private static final List<String> ENERGY_BAR_CHAMPIONS
            = List.of("Akali", "Kennen", "Lee Sin", "Shen", "Zed");

    private ResourceBars() {
    }

    public static IFrame getResourceBarForActivePlayerChampion() {
        String activePlayerChampionName = RunningState.getGameState().getPlayerList().getActivePlayer().getChampionName();
        if (NO_MANA_BAR_CHAMPIONS.contains(activePlayerChampionName)) {
            return new NoResourceBar();
        }

        if (ENERGY_BAR_CHAMPIONS.contains(activePlayerChampionName)) {
            return new EnergyBar();
        }

        if ("Renekton".equals(activePlayerChampionName)) {
            return new RenektonResourceBar();
        }

        if ("Vladimir".equals(activePlayerChampionName)) {
            return new VladimirResourceBar();
        }

        if ("Gnar".equals(activePlayerChampionName)) {
            return new GnarFuryBar();
        }
        return new ManaBar();
    }

    public static List<RzKey> getResourceBarKeys() {
        return Collections.unmodifiableList(RESOURCE_BAR_KEYS);
    }
}
