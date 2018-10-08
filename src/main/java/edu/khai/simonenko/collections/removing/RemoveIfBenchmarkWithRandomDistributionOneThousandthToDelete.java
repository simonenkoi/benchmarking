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
public class RemoveIfBenchmarkWithRandomDistributionOneThousandthToDelete {

    private static final String STRING_TO_REMOVAL = "STRING_TO_REMOVAL";
    private static final List<String> LIST = new ArrayList<>();

    @Setup
    public void init() {
        for (int i = 0; i < 1_000_000; ++i) {
            String element = RandomStringUtils.randomAlphanumeric(5);
            if (RandomUtils.nextInt(0, 1001) == 0) {
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

    /**
     * Benchmark                                                                                                    Mode  Cnt    Score    Error  Units
     * RemoveIfBenchmarkWithRandomDistributionOneThousandthToDelete.testRemovingElementFromArrayListWithIterator    avgt   50  106.475 ±  4.321  ms/op
     * RemoveIfBenchmarkWithRandomDistributionOneThousandthToDelete.testRemovingElementFromArrayListWithRemoveAll   avgt   50   12.881 ±  2.231  ms/op
     * RemoveIfBenchmarkWithRandomDistributionOneThousandthToDelete.testRemovingElementFromArrayListWithRemoveIf    avgt   50   16.025 ±  4.720  ms/op
     * RemoveIfBenchmarkWithRandomDistributionOneThousandthToDelete.testRemovingElementFromLinkedListWithIterator   avgt   50   23.438 ±  7.741  ms/op
     * RemoveIfBenchmarkWithRandomDistributionOneThousandthToDelete.testRemovingElementFromLinkedListWithRemoveAll  avgt   50   27.593 ± 11.666  ms/op
     * RemoveIfBenchmarkWithRandomDistributionOneThousandthToDelete.testRemovingElementFromLinkedListWithRemoveIf   avgt   50   25.833 ± 10.203  ms/op
     */
}
