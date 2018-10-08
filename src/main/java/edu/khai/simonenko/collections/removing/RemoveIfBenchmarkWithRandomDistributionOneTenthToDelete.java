package edu.khai.simonenko.collections.removing;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
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
public class RemoveIfBenchmarkWithRandomDistributionOneTenthToDelete {

    private static final String STRING_TO_REMOVAL = "STRING_TO_REMOVAL";
    private static final List<String> LIST = new ArrayList<>();

    @Setup
    public void init() {
        for (int i = 0; i < 1_000_000; ++i) {
            String element = RandomStringUtils.randomAlphanumeric(5);
            if (RandomUtils.nextInt(0, 11) == 0) {
                element = STRING_TO_REMOVAL;
            }
            LIST.add(element);
        }
    }

    @Benchmark
    public void testRemovingElementFromLinkedListWithRemoveAll() {
        new LinkedList<>(LIST).removeAll(Collections.singleton(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromLinkedListWithRemoveIf() {
        new LinkedList<>(LIST).removeIf(s -> s.equals(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromLinkedListWithIterator() {
        removeIfBasedOnIterator(new LinkedList<>(LIST), s -> s.equals(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromArrayListWithRemoveAll() {
        new ArrayList<>(LIST).removeAll(Collections.singleton(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromArrayListWithRemoveIf() {
        new ArrayList<>(LIST).removeIf(s -> s.equals(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromArrayListWithIterator() {
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
     * Benchmark                                                                                               Mode  Cnt     Score     Error  Units
     * RemoveIfBenchmarkWithRandomDistributionOneTenthToDelete.testRemovingElementFromArrayListWithIterator    avgt   50  9136.783 ± 226.798  ms/op
     * RemoveIfBenchmarkWithRandomDistributionOneTenthToDelete.testRemovingElementFromArrayListWithRemoveAll   avgt   50    11.496 ±   2.099  ms/op
     * RemoveIfBenchmarkWithRandomDistributionOneTenthToDelete.testRemovingElementFromArrayListWithRemoveIf    avgt   50    13.677 ±   2.036  ms/op
     * RemoveIfBenchmarkWithRandomDistributionOneTenthToDelete.testRemovingElementFromLinkedListWithIterator   avgt   50    29.960 ±  12.339  ms/op
     * RemoveIfBenchmarkWithRandomDistributionOneTenthToDelete.testRemovingElementFromLinkedListWithRemoveAll  avgt   50    25.952 ±  10.011  ms/op
     * RemoveIfBenchmarkWithRandomDistributionOneTenthToDelete.testRemovingElementFromLinkedListWithRemoveIf   avgt   50    26.411 ±  11.569  ms/op
     */
}
