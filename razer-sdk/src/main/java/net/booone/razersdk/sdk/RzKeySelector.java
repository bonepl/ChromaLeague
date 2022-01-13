package net.booone.razersdk.sdk;

import net.booone.razersdk.sdk.json.request.KeyboardEffect;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Helper class for selecting {@link RzKey} sets. <br><br>
 * Usage example:<br>
 * <pre>
 * List<RzKey> selectedKeys = new RzKeySelector()
 *     .withColumnBetween(RzKey.RZKEY_A, RzKey.RZKEY_D)
 *     .withRowBetween(RzKey.RZKEY_A, RzKey.RZKEY_Z)
 *     .sortedByColumn()
 *     .asList();
 * //returns A, Z, S, X, D, C
 * </pre>
 */
public final class RzKeySelector {
    private Predicate<Integer> columnSelector = column -> false;
    private Predicate<Integer> rowSelector = row -> false;
    private Comparator<RzKey> sort = Comparator.naturalOrder();

    private RzKeySelector withColumn(Predicate<Integer> predicate) {
        columnSelector = predicate;
        return this;
    }

    private RzKeySelector withRow(Predicate<Integer> predicate) {
        rowSelector = predicate;
        return this;
    }

    private RzKeySelector withSort(Comparator<RzKey> comparator) {
        sort = comparator;
        return this;
    }

    /**
     * Select keys with the same column as provided {@link RzKey}
     *
     * @param rzKey RzKey with column to select
     * @return this
     */
    public RzKeySelector withColumnOf(final RzKey rzKey) {
        return withColumn(rzKey.getColumn());
    }

    /**
     * Select keys with the same row as provided {@link RzKey}
     *
     * @param rzKey RzKey with row to select
     * @return this
     */
    public RzKeySelector withRowOf(final RzKey rzKey) {
        return withRow(rzKey.getRow());
    }

    /**
     * Select keys with the same column as provided number
     *
     * @param column RzKey with column to select
     * @return this
     * @throws IllegalArgumentException if provided number is not between 0 and 22 inclusive
     */
    public RzKeySelector withColumn(final int column) {
        if (column < 0 || column > KeyboardEffect.KEYBOARD_COLUMNS) {
            throw new IllegalArgumentException(String.format("Column number should be between 0 and %d, but was %d",
                    KeyboardEffect.KEYBOARD_COLUMNS, column));
        }
        return withColumn(col -> col == column);
    }

    /**
     * Select keys with the same row as provided number
     *
     * @param row RzKey with column to select
     * @return this
     * @throws IllegalArgumentException if provided number is not between 0 and 6 inclusive
     */
    public RzKeySelector withRow(final int row) {
        if (row < 0 || row > KeyboardEffect.KEYBOARD_ROWS) {
            throw new IllegalArgumentException(String.format("Row number should be between 0 and %d, but was %d",
                    KeyboardEffect.KEYBOARD_ROWS, row));
        }
        return withRow(r -> r == row);
    }

    /**
     * Select keys with any column
     *
     * @return this
     */
    public RzKeySelector withAnyColumn() {
        return withColumn(column -> true);
    }

    /**
     * Select keys with any row
     *
     * @return this
     */
    public RzKeySelector withAnyRow() {
        return withRow(row -> true);
    }

    public RzKeySelector all() {
        return withAnyColumn().withAnyRow();
    }

    /**
     * Select keys with the column between provided {@link RzKey}s
     *
     * @param from RzKey with first column to select
     * @param to   RzKey with last column
     * @return this
     * @throws IllegalArgumentException if from column is higher than to column
     */
    public RzKeySelector withColumnBetween(final RzKey from, final RzKey to) {
        if (from.getColumn() > to.getColumn()) {
            throw new IllegalArgumentException("Last column to select must be equal or bigger than first column to select");
        }
        return withColumn(column -> column >= from.getColumn() && column <= to.getColumn());
    }

    /**
     * Select keys with the row between provided {@link RzKey}s
     *
     * @param from RzKey with first row to select
     * @param to   RzKey with last row
     * @return this
     * @throws IllegalArgumentException if from row is higher than to row
     */
    public RzKeySelector withRowBetween(final RzKey from, final RzKey to) {
        if (from.getRow() > to.getRow()) {
            throw new IllegalArgumentException("Last row to select must be equal or bigger than first row to select");
        }
        return withRow(row -> row >= from.getRow() && row <= to.getRow());
    }

    /**
     * Select keys as the rectangle between provided {@link RzKey}s
     *
     * @param topLeft     top left rectangle corner represented by RzKey
     * @param bottomRight bottom right rectangle corner represented by RzKey
     * @return this
     * @throws IllegalArgumentException if topLeft column is higher than bottomRight column
     *                                  or if topLeft row is higher than bottomRight row
     */
    public RzKeySelector withRectangleBetween(final RzKey topLeft, final RzKey bottomRight) {
        return withRowBetween(topLeft, bottomRight).withColumnBetween(topLeft, bottomRight);
    }

    /**
     * Sort selected keys by column number (applies only when selecting {@link #asList()})
     *
     * @return this
     */
    public RzKeySelector sortedByColumn() {
        return withSort(Comparator.comparingInt(RzKey::getColumn));
    }

    /**
     * Sort selected keys by row number (applies only when selecting {@link #asList()})
     *
     * @return this
     */
    public RzKeySelector sortedByRow() {
        return withSort(Comparator.comparingInt(RzKey::getRow));
    }

    /**
     * Collect selected {@link RzKey}s to {@link Set}
     *
     * @return selected keys as {@link Set}
     */
    public Set<RzKey> asSet() {
        return asStream().collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Collect selected {@link RzKey}s to sorted {@link List}
     *
     * @return selected keys as {@link List}
     */
    public List<RzKey> asList() {
        return asStream().sorted(sort).toList();
    }

    private Stream<RzKey> asStream() {
        return Arrays.stream(RzKey.values())
                .filter(rzKey -> columnSelector.test(rzKey.getColumn()))
                .filter(rzKey -> rowSelector.test(rzKey.getRow()));
    }
}
