package com.bonepl.chromaleague.hud.parts.dragons;

import com.bonepl.chromaleague.rest.eventdata.DragonType;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.*;

import static com.bonepl.razersdk.sdk.RzKey.*;
import static java.util.Collections.emptyMap;

public class KilledDragonsBar extends SimpleFrame {
    public static final List<RzKey> FIRST_DRAGON_ROW = List.of(RZKEY_BACKSLASH);
    public static final List<RzKey> SECOND_DRAGON_ROW = List.of(RZKEY_ENTER);
    public static final List<RzKey> THIRD_DRAGON_ROW = List.of(RZKEY_RSHIFT);
    public static final List<RzKey> FOURTH_DRAGON_ROW = List.of(RZKEY_FN, RZKEY_RMENU, RZKEY_RCTRL);
    public static final List<RzKey> SOUL_BAR = List.of(RZKEY_LCTRL, RZKEY_LWIN, RZKEY_LALT, RZKEY_SPACE, RZKEY_RALT,
            RZKEY_UP, RZKEY_LEFT, RZKEY_DOWN, RZKEY_RIGHT);

    private static final Map<RzKey, Color> dragonColorsMap = new HashMap<>();

    public KilledDragonsBar() {
        super(getKilledDragonsBar());
    }

    private static Map<RzKey, Color> getKilledDragonsBar() {
        Map<RzKey, Color> killedDragonsBar = new EnumMap<>(RzKey.class);
        killedDragonsBar.putAll(getDragonType(0).map(dragonType -> computeDragonColor(dragonType, FIRST_DRAGON_ROW)).orElse(emptyMap()));
        killedDragonsBar.putAll(getDragonType(1).map(dragonType -> computeDragonColor(dragonType, SECOND_DRAGON_ROW)).orElse(emptyMap()));
        killedDragonsBar.putAll(getDragonType(2).map(dragonType -> computeDragonColor(dragonType, THIRD_DRAGON_ROW)).orElse(emptyMap()));
        killedDragonsBar.putAll(getDragonType(3).map(dragonType -> computeDragonColor(dragonType, FOURTH_DRAGON_ROW)).orElse(emptyMap()));
        killedDragonsBar.putAll(getDragonSoul(GameStateHelper.getKilledDragons()).map(dragonType -> computeDragonColor(dragonType, SOUL_BAR)).orElse(emptyMap()));
        return killedDragonsBar;
    }

    private static Map<RzKey, Color> computeDragonColor(DragonType dragonType, List<RzKey> keys) {
        Map<RzKey, Color> currentDragonColorMap = new HashMap<>();
        keys.forEach(rzKey -> dragonColorsMap.computeIfAbsent(rzKey, rk -> dragonType.getColor()));
        keys.forEach(rzKey -> currentDragonColorMap.put(rzKey, dragonColorsMap.get(rzKey)));
        return currentDragonColorMap;
    }

    private static Optional<DragonType> getDragonType(int index) {
        final List<DragonType> killedDragons = GameStateHelper.getKilledDragons();
        if (killedDragons.size() > index) {
            return Optional.of(killedDragons.get(index));
        }
        return Optional.empty();
    }

    private static Optional<DragonType> getDragonSoul(List<DragonType> killedDragons) {
        if (killedDragons.size() > 3) {
            return Optional.of(killedDragons.get(3));
        }
        return Optional.empty();
    }
}
