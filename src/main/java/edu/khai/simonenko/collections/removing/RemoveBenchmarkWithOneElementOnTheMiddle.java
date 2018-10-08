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
public class RemoveBenchmarkWithOneElementOnTheMiddle {

    private static final String STRING_TO_REMOVAL = "STRING_TO_REMOVAL";
    private static final List<String> LIST = new ArrayList<>();

    @Setup
    public void init() {
        for (int i = 0; i < 500_000; ++i) {
            LIST.add(RandomStringUtils.randomAlphanumeric(5));
        }
        LIST.add(STRING_TO_REMOVAL);
        for (int i = 0; i < 499_999; ++i) {
            LIST.add(RandomStringUtils.randomAlphanumeric(5));
        }
    }

    @Benchmark
    public void testRemovingElementFromMiddleOfLinkedListWithRemoveAll() {
        new LinkedList<>(LIST).removeAll(Collections.singleton(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromMiddleOfLinkedListWithRemoveIf() {
        new LinkedList<>(LIST).removeIf(s -> s.equals(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromMiddleOfLinkedListWithIterator() {
        removeIfBasedOnIterator(new LinkedList<>(LIST), s -> s.equals(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromMiddleOfArrayListWithRemoveAll() {
        new ArrayList<>(LIST).removeAll(Collections.singleton(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromMiddleOfArrayListWithRemoveIf() {
        new ArrayList<>(LIST).removeIf(s -> s.equals(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromMiddleOfArrayListWithIterator() {
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
     * Benchmark                                                                                        Mode  Cnt   Score    Error  Units
     * RemoveBenchmarkWithOneElementOnTheMiddle.testRemovingElementFromMiddleOfArrayListWithIterator    avgt   50   5.463 ±  1.431  ms/op
     * RemoveBenchmarkWithOneElementOnTheMiddle.testRemovingElementFromMiddleOfArrayListWithRemoveAll   avgt   50   7.996 ±  0.442  ms/op
     * RemoveBenchmarkWithOneElementOnTheMiddle.testRemovingElementFromMiddleOfArrayListWithRemoveIf    avgt   50   8.608 ±  0.603  ms/op
     * RemoveBenchmarkWithOneElementOnTheMiddle.testRemovingElementFromMiddleOfLinkedListWithIterator   avgt   50  30.099 ± 13.269  ms/op
     * RemoveBenchmarkWithOneElementOnTheMiddle.testRemovingElementFromMiddleOfLinkedListWithRemoveAll  avgt   50  37.052 ± 18.457  ms/op
     * RemoveBenchmarkWithOneElementOnTheMiddle.testRemovingElementFromMiddleOfLinkedListWithRemoveIf   avgt   50  28.659 ± 14.179  ms/op
     */
}
