package edu.khai.simonenko.collections.removing;

import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 25, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 25, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@CompilerControl(CompilerControl.Mode.DONT_INLINE)
public class RemoveBenchmarkWithOneElementOnTheEnd {

    private static final String STRING_TO_REMOVAL = "STRING_TO_REMOVAL";
    private static final List<String> LIST = new ArrayList<>();

    @Setup
    public void init() {
        for (int i = 0; i < 999_999; ++i) {
            LIST.add(RandomStringUtils.randomAlphanumeric(5));
        }
        LIST.add(STRING_TO_REMOVAL);
    }

    @Benchmark
    public void testRemovingElementFromEndOfLinkedListWithRemoveAll() {
        new LinkedList<>(LIST).removeAll(Collections.singleton(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromEndOfLinkedListWithRemoveIf() {
        new LinkedList<>(LIST).removeIf(s -> s.equals(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromEndOfLinkedListWithIterator() {
        removeIfBasedOnIterator(new LinkedList<>(LIST), s -> s.equals(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromEndOfArrayListWithRemoveAll() {
        new ArrayList<>(LIST).removeAll(Collections.singleton(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromEndOfArrayListWithRemoveIf() {
        new ArrayList<>(LIST).removeIf(s -> s.equals(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromEndOfArrayListWithIterator() {
        removeIfBasedOnIterator(new ArrayList<>(LIST), s -> s.equals(STRING_TO_REMOVAL));
    }

    private static void removeIfBasedOnIterator(List<String> list, Predicate<String> predicate) {
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (predicate.test(iterator.next())) {
                iterator.remove();
            }
        }
    }

    /*
     * Benchmark                                                                                  Mode  Cnt   Score    Error  Units
     * RemoveBenchmarkWithOneElementOnTheEnd.testRemovingElementFromEndOfArrayListWithIterator    avgt   50   7.470 ±  0.421  ms/op
     * RemoveBenchmarkWithOneElementOnTheEnd.testRemovingElementFromEndOfArrayListWithRemoveAll   avgt   50   5.004 ±  0.236  ms/op
     * RemoveBenchmarkWithOneElementOnTheEnd.testRemovingElementFromEndOfArrayListWithRemoveIf    avgt   50   6.812 ±  0.254  ms/op
     * RemoveBenchmarkWithOneElementOnTheEnd.testRemovingElementFromEndOfLinkedListWithIterator   avgt   50  29.013 ± 12.618  ms/op
     * RemoveBenchmarkWithOneElementOnTheEnd.testRemovingElementFromEndOfLinkedListWithRemoveAll  avgt   50  24.769 ±  9.447  ms/op
     * RemoveBenchmarkWithOneElementOnTheEnd.testRemovingElementFromEndOfLinkedListWithRemoveIf   avgt   50  29.585 ± 12.430  ms/op
     */
}
