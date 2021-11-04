package com.bonepl.chromaleague.hud.parts.dragons;

import com.bonepl.chromaleague.hud.colors.BreathingColor;
import com.bonepl.chromaleague.rest.eventdata.DragonType;
import com.bonepl.chromaleague.state.GameStateHelper;
import com.bonepl.razersdk.color.Color;
import com.bonepl.razersdk.animation.SimpleFrame;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.bonepl.razersdk.sdk.RzKey.*;

public class KilledDragonsBar extends SimpleFrame {
    public static final RzKey FIRST_DRAGON_ROW = RZKEY_BACKSLASH;
    public static final RzKey SECOND_DRAGON_ROW = RZKEY_ENTER;
    public static final RzKey THIRD_DRAGON_ROW = RZKEY_RSHIFT;
    public static final List<RzKey> FOURTH_DRAGON_ROW = List.of(RZKEY_FN, RZKEY_RMENU, RZKEY_RCTRL);
    public static final List<RzKey> SOUL_BAR = List.of(RZKEY_UP, RZKEY_LEFT, RZKEY_DOWN, RZKEY_RIGHT);

    private static BreathingColor dragonSoulColor;

    public KilledDragonsBar() {
        super(getKilledDragonsBar());
    }

    private static Map<RzKey, Color> getKilledDragonsBar() {
        final List<DragonType> killedDragons = GameStateHelper.getKilledDragons();
        Map<RzKey, Color> killedDragonsBar = new EnumMap<>(RzKey.class);
        final Optional<DragonType> dragonSoul = getDragonSoul(killedDragons);
        if (dragonSoul.isPresent()) {
            final DragonType soulType = dragonSoul.get();
            if (dragonSoulColor == null) {
                dragonSoulColor = new BreathingColor(soulType.getColor());
            }
            final Color nextSoulColor = dragonSoulColor.getNextColor();
            getDragonType(0).map(dragonType -> swapColorIfSoul(dragonType, soulType, nextSoulColor))
                    .ifPresent(dragonColor -> killedDragonsBar.put(FIRST_DRAGON_ROW, dragonColor));
            getDragonType(1).map(dragonType -> swapColorIfSoul(dragonType, soulType, nextSoulColor))
                    .ifPresent(dragonColor -> killedDragonsBar.put(SECOND_DRAGON_ROW, dragonColor));
            getDragonType(2).map(dragonType -> swapColorIfSoul(dragonType, soulType, nextSoulColor))
                    .ifPresent(dragonColor -> killedDragonsBar.put(THIRD_DRAGON_ROW, dragonColor));
            getDragonType(3).map(dragonType -> swapColorIfSoul(dragonType, soulType, nextSoulColor))
                    .ifPresent(dragonColor -> FOURTH_DRAGON_ROW.forEach(rzKey -> killedDragonsBar.put(rzKey, dragonColor)));
            SOUL_BAR.forEach(rzKey -> killedDragonsBar.put(rzKey, nextSoulColor));
        } else {
            dragonSoulColor = null;
            getDragonType(0).map(DragonType::getColor)
                    .ifPresent(dragonColor -> killedDragonsBar.put(FIRST_DRAGON_ROW, dragonColor));
            getDragonType(1).map(DragonType::getColor)
                    .ifPresent(dragonColor -> killedDragonsBar.put(SECOND_DRAGON_ROW, dragonColor));
            getDragonType(2).map(DragonType::getColor)
                    .ifPresent(dragonColor -> killedDragonsBar.put(THIRD_DRAGON_ROW, dragonColor));
            getDragonType(3).map(DragonType::getColor)
                    .ifPresent(dragonColor -> FOURTH_DRAGON_ROW.forEach(rzKey -> killedDragonsBar.put(rzKey, dragonColor)));
        }
        return killedDragonsBar;
    }

    private static Color swapColorIfSoul(DragonType dragonType, DragonType soulType, Color soulColor) {
        if (soulType != null && dragonType == soulType) {
            return soulColor;
        }
        return dragonType.getColor();
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
