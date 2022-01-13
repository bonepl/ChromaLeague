package net.booone.chromaleague.hud.parts.resource;

import net.booone.chromaleague.state.RunningState;
import net.booone.razersdk.animation.IFrame;
import net.booone.razersdk.sdk.RzKey;
import net.booone.razersdk.sdk.RzKeySelector;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static net.booone.razersdk.sdk.RzKey.RZKEY_BACKSPACE;
import static net.booone.razersdk.sdk.RzKey.RZKEY_TILDE;

public final class ResourceBars {
    private static final List<RzKey> RESOURCE_BAR_KEYS = new RzKeySelector()
            .withRowOf(RZKEY_TILDE)
            .withColumnBetween(RZKEY_TILDE, RZKEY_BACKSPACE)
            .sortedByColumn()
            .asList();

    private static final Set<String> NO_MANA_BAR_CHAMPIONS
            = Set.of("Aatrox", "Dr. Mundo", "Garen", "Katarina", "Riven", "Viego", "Zac");
    private static final Set<String> ENERGY_BAR_CHAMPIONS
            = Set.of("Akali", "Kennen", "Lee Sin", "Shen", "Zed");

    private ResourceBars() {
    }

    public static IFrame getResourceBarForActivePlayerChampion() {
        String activePlayerChampionName = RunningState.getGameState().getPlayerList().getActivePlayer().championName();
        if (NO_MANA_BAR_CHAMPIONS.contains(activePlayerChampionName)) {
            return new NoResourceBar();
        }

        if (ENERGY_BAR_CHAMPIONS.contains(activePlayerChampionName)) {
            return new EnergyBar();
        }

        return switch (activePlayerChampionName) {
            case "Gnar" -> new GnarFuryBar();
            case "Kled" -> new KledCourageBar();
            case "Mordekaiser" -> new MordekaiserShieldBar();
            case "Renekton" -> new RenektonFuryBar();
            case "Rengar" -> new RengarFerocityBar();
            case "Rek'Sai", "Tryndamere" -> new RedFuryBar();
            case "Rumble" -> new RumbleHeatBar();
            case "Sett" -> new SettGritBar();
            case "Shyvana" -> new ShyvanaDragonFuryBar();
            case "Vladimir" -> new VladimirBloodPoolBar();
            case "Yasuo" -> new YasuoWindBar();
            case "Yone" -> new YoneCloneBar();
            default -> new ManaBar();
        };
    }

    public static List<RzKey> getResourceBarKeys() {
        return Collections.unmodifiableList(RESOURCE_BAR_KEYS);
    }

    public static Set<String> getEnergyBarChampions() {
        return ENERGY_BAR_CHAMPIONS;
    }
}
