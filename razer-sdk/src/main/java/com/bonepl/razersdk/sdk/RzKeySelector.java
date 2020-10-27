package com.bonepl.razersdk.sdk;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return withColumn(column -> column == rzKey.getColumn());
    }

    public RzKeySelector withRowOf(RzKey rzKey) {
        return withRow(row -> row == rzKey.getRow());
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

    public List<RzKey> asList() {
        return asStream().collect(Collectors.toUnmodifiableList());
    }

    public Stream<RzKey> asStream() {
        return Arrays.stream(RzKey.values())
                .filter(rzKey -> columnPredicate.test(rzKey.getColumn()))
                .filter(rzKey -> rowPredicate.test(rzKey.getRow()))
                .sorted(sort);
    }
}
