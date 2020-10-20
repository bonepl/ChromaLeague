package com.bonepl.chromaleague.hud.parts;

import com.bonepl.chromaleague.GameStateHelper;
import com.bonepl.chromaleague.hud.DragonType;
import com.bonepl.razersdk.animation.Color;
import com.bonepl.razersdk.animation.Frame;
import com.bonepl.razersdk.sdk.RzKey;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

import static com.bonepl.razersdk.sdk.RzKey.*;

public class KilledDragonBar extends Frame {
    private static final List<RzKey> FIRST_DRAGON_ROW = Arrays.asList(RZKEY_FN, RZKEY_RMENU, RZKEY_RCTRL);
    private static final RzKey SECOND_DRAGON_ROW = RZKEY_RSHIFT;
    private static final RzKey THIRD_DRAGON_ROW = RZKEY_ENTER;
    private static final RzKey FOURTH_DRAGON_ROW = RZKEY_OEM_6;

    public KilledDragonBar() {
        super(getKilledDragonsBar());
    }

    private static EnumMap<RzKey, Color> getKilledDragonsBar() {
        EnumMap<RzKey, Color> killedDragonsBar = new EnumMap<>(RzKey.class);
        getDragonColor(0).ifPresent(dragonColor -> FIRST_DRAGON_ROW.forEach(rzKey -> killedDragonsBar.put(rzKey, dragonColor)));
        getDragonColor(1).ifPresent(dragonColor -> killedDragonsBar.put(SECOND_DRAGON_ROW, dragonColor));
        getDragonColor(2).ifPresent(dragonColor -> killedDragonsBar.put(THIRD_DRAGON_ROW, dragonColor));
        getDragonColor(3).ifPresent(dragonColor -> killedDragonsBar.put(FOURTH_DRAGON_ROW, dragonColor));
        return killedDragonsBar;
    }

    private static Optional<Color> getDragonColor(int index) {
        final List<DragonType> killedDragons = GameStateHelper.getKilledDragons();
        if (killedDragons.size() > index) {
            return Optional.of(killedDragons.get(index)).map(DragonType::getColor);
        }
        return Optional.empty();
    }
}
