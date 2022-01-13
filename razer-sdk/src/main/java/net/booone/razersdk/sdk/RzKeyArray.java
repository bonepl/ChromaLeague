package net.booone.razersdk.sdk;

import java.util.Arrays;
import java.util.Optional;

public final class RzKeyArray {
    private static final int MAX_ROW = 5;
    private static final int MAX_COLUMN = 21;

    private static final RzKey[][] RZ_KEY_ARRAY = new RzKey[MAX_ROW + 1][MAX_COLUMN + 1];

    static {
        Arrays.stream(RzKey.values()).forEach(rk -> RZ_KEY_ARRAY[rk.getRow()][rk.getColumn()] = rk);
    }

    public static Optional<RzKey> keyAt(int row, int column) {
        if (row > MAX_ROW || row < 0 || column > MAX_COLUMN || column < 0) {
            return Optional.empty();
        }
        return Optional.ofNullable(RZ_KEY_ARRAY[row][column]);
    }
}
