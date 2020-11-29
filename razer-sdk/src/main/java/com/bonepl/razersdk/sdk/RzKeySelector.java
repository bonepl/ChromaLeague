package com.bonepl.razersdk.sdk;

import com.bonepl.razersdk.sdk.json.request.KeyboardEffect;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Helper class for selecting desired key sets
 */
public final class RzKeySelector {
    private Predicate<Integer> columnPredicate = column -> false;
    private Predicate<Integer> rowPredicate = row -> false;
    private Comparator<RzKey> sort = Comparator.naturalOrder();

    private RzKeySelector withColumn(Predicate<Integer> predicate) {
        columnPredicate = predicate;
        return this;
    }

    private RzKeySelector withRow(Predicate<Integer> predicate) {
        rowPredicate = predicate;
        return this;
    }

    private RzKeySelector withSort(Comparator<RzKey> comparator) {
        sort = comparator;
        return this;
    }

    public RzKeySelector withColumnOf(RzKey rzKey) {
        return withColumn(rzKey.getColumn());
    }

    public RzKeySelector withRowOf(RzKey rzKey) {
        return withRow(rzKey.getRow());
    }

    public RzKeySelector withColumn(final int column) {
        if (column < 0 || column > KeyboardEffect.KEYBOARD_COLUMNS) {
            throw new IllegalArgumentException(String.format("Column number should be between 0 and %d, but was %d",
                    KeyboardEffect.KEYBOARD_COLUMNS, column));
        }
        return withColumn(col -> col == column);
    }

    public RzKeySelector withRow(final int row) {
        if (row < 0 || row > KeyboardEffect.KEYBOARD_ROWS) {
            throw new IllegalArgumentException(String.format("Row number should be between 0 and %d, but was %d",
                    KeyboardEffect.KEYBOARD_ROWS, row));
        }
        return withRow(ro -> ro == row);
    }

    public RzKeySelector withAnyColumn() {
        return withColumn(column -> true);
    }

    public RzKeySelector withAnyRow() {
        return withRow(row -> true);
    }

    public RzKeySelector withColumnBetween(RzKey from, RzKey to) {
        return withColumn(column -> column >= from.getColumn() && column <= to.getColumn());
    }

    public RzKeySelector withRowBetween(RzKey from, RzKey to) {
        return withRow(row -> row >= from.getRow() && row <= to.getRow());
    }

    public RzKeySelector withRectangleBetween(RzKey topLeft, RzKey bottomRight) {
        return withRowBetween(topLeft, bottomRight).withColumnBetween(topLeft, bottomRight);
    }

    public RzKeySelector sortedByColumn() {
        return withSort(Comparator.comparingInt(RzKey::getColumn));
    }

    public RzKeySelector sortedByRow() {
        return withSort(Comparator.comparingInt(RzKey::getRow));
    }

    public Set<RzKey> asSet() {
        return asStream().collect(Collectors.toUnmodifiableSet());
    }

    public List<RzKey> asList() {
        return asStream().sorted(sort).collect(Collectors.toUnmodifiableList());
    }

    private Stream<RzKey> asStream() {
        return Arrays.stream(RzKey.values())
                .filter(rzKey -> columnPredicate.test(rzKey.getColumn()))
                .filter(rzKey -> rowPredicate.test(rzKey.getRow()));
    }
}
