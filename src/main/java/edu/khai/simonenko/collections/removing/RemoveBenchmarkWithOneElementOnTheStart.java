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
public class RemoveBenchmarkWithOneElementOnTheStart {

    private static final String STRING_TO_REMOVAL = "STRING_TO_REMOVAL";
    private static final List<String> LIST = new ArrayList<>();

    @Setup
    public void init() {
        LIST.add(STRING_TO_REMOVAL);
        for (int i = 0; i < 999_999; ++i) {
            LIST.add(RandomStringUtils.randomAlphanumeric(5));
        }
    }

    @Benchmark
    public void testRemovingElementFromStartOfLinkedListWithRemoveAll() {
        new LinkedList<>(LIST).removeAll(Collections.singleton(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromStartOfLinkedListWithRemoveIf() {
        new LinkedList<>(LIST).removeIf(s -> s.equals(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromStartOfLinkedListWithIterator() {
        removeIfBasedOnIterator(new LinkedList<>(LIST), s -> s.equals(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromStartOfArrayListWithRemoveAll() {
        new ArrayList<>(LIST).removeAll(Collections.singleton(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromStartOfArrayListWithRemoveIf() {
        new ArrayList<>(LIST).removeIf(s -> s.equals(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromStartOfArrayListWithIterator() {
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
     * Benchmark                                                                                      Mode  Cnt   Score    Error  Units
     * RemoveBenchmarkWithOneElementOnTheStart.testRemovingElementFromStartOfArrayListWithIterator    avgt   50   5.733 ±  1.514  ms/op
     * RemoveBenchmarkWithOneElementOnTheStart.testRemovingElementFromStartOfArrayListWithRemoveAll   avgt   50  10.863 ±  0.851  ms/op
     * RemoveBenchmarkWithOneElementOnTheStart.testRemovingElementFromStartOfArrayListWithRemoveIf    avgt   50  13.727 ±  1.522  ms/op
     * RemoveBenchmarkWithOneElementOnTheStart.testRemovingElementFromStartOfLinkedListWithIterator   avgt   50  34.553 ± 16.321  ms/op
     * RemoveBenchmarkWithOneElementOnTheStart.testRemovingElementFromStartOfLinkedListWithRemoveAll  avgt   50  31.891 ± 13.490  ms/op
     * RemoveBenchmarkWithOneElementOnTheStart.testRemovingElementFromStartOfLinkedListWithRemoveIf   avgt   50  24.545 ± 10.615  ms/op
     */
}
