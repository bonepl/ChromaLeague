package net.booone.chromaleague.hud;

import net.booone.razersdk.sdk.RzKey;
import net.booone.razersdk.sdk.RzKeySelector;

import java.util.List;

import static net.booone.razersdk.sdk.RzKey.RZKEY_PAGEDOWN;
import static net.booone.razersdk.sdk.RzKey.RZKEY_PRINTSCREEN;

public final class PredefinedKeySets {
    public static final List<RzKey> BLACKWIDOW_FIRST_ROW = new RzKeySelector().withRow(0).sortedByColumn().asList();
    public static final List<RzKey> BLACKWIDOW_SECOND_ROW = new RzKeySelector().withRow(1).sortedByColumn().asList();
    public static final List<RzKey> BLACKWIDOW_THIRD_ROW = new RzKeySelector().withRow(2).sortedByColumn().asList();
    public static final List<RzKey> BLACKWIDOW_FOURTH_ROW = new RzKeySelector().withRow(3).sortedByColumn().asList();
    public static final List<RzKey> BLACKWIDOW_FIFTH_ROW = new RzKeySelector().withRow(4).sortedByColumn().asList();
    public static final List<RzKey> BLACKWIDOW_SIXTH_ROW = new RzKeySelector().withRow(5).sortedByColumn().asList();
    public static final List<RzKey> BLACKWIDOW_FUNCTIONAL = new RzKeySelector().withRectangleBetween(RZKEY_PRINTSCREEN, RZKEY_PAGEDOWN).asList();
    public static final List<RzKey> FIRST_NUMPAD_COLUMN = new RzKeySelector().withColumn(18).sortedByRow().asList();
    public static final List<RzKey> SECOND_NUMPAD_COLUMN = new RzKeySelector().withColumn(19).sortedByRow().asList();
    public static final List<RzKey> THIRD_NUMPAD_COLUMN = new RzKeySelector().withColumn(20).sortedByRow().asList();
    public static final List<RzKey> FOURTH_NUMPAD_COLUMN = new RzKeySelector().withColumn(21).sortedByRow().asList();

    private PredefinedKeySets() {
    }
}
