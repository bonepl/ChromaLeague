package com.bonepl.razersdk.sdk;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class RzKeySelector {
    private Predicate<Integer> columnPredicate = column -> true;
    private Predicate<Integer> rowPredicate = row -> true;
    private Comparator<RzKey> comparator = Comparator.naturalOrder();

    public RzKeySelector withColumn(Predicate<Integer> predicate) {
        columnPredicate = predicate;
        return this;
    }

    public RzKeySelector withRow(Predicate<Integer> predicate) {
        rowPredicate = predicate;
        return this;
    }

    public RzKeySelector withColumnOf(RzKey rzKey) {
        columnPredicate = column -> column == rzKey.getColumn();
        return this;
    }

    public RzKeySelector withRowOf(RzKey rzKey) {
        rowPredicate = row -> row == rzKey.getRow();
        return this;
    }

    public RzKeySelector withColumnBetween(RzKey from, RzKey to) {
        columnPredicate = column -> column >= from.getColumn() && column <= to.getColumn();
        return this;
    }

    public RzKeySelector withRowBetween(RzKey from, RzKey to) {
        rowPredicate = row -> row >= from.getRow() && row <= to.getRow();
        return this;
    }

    public RzKeySelector sortedByColumn(){
        comparator = Comparator.comparingInt(RzKey::getColumn);
        return this;
    }

    public RzKeySelector sortedByRow(){
        comparator = Comparator.comparingInt(RzKey::getRow);
        return this;
    }

    public List<RzKey> asList() {
        return asStream().collect(Collectors.toUnmodifiableList());
    }

    public Stream<RzKey> asStream() {
        return Arrays.stream(RzKey.values())
                .filter(rzKey -> columnPredicate.test(rzKey.getColumn()))
                .filter(rzKey -> rowPredicate.test(rzKey.getRow()))
                .sorted(comparator);
    }
}
