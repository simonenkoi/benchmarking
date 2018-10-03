package edu.khai.simonenko.collections;

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
public class RemoveIfBenchmark {

    private static final String STRING_TO_REMOVAL = "STRING_TO_REMOVAL";
    private List<String> arrayList;
    private List<String> linkedList;

    @Setup(Level.Invocation)
    public void init() {
        arrayList = new ArrayList<>();
        linkedList = new LinkedList<>();
        for (int i = 0; i < 700_000; ++i) {
            arrayList.add(RandomStringUtils.randomAlphanumeric(5));
        }
        arrayList.add(STRING_TO_REMOVAL);
        for (int i = 0; i < 700_000; ++i) {
            arrayList.add(RandomStringUtils.randomAlphanumeric(5));
        }
        linkedList.addAll(arrayList);
    }

    @Benchmark
    public void testRemovingElementFromMiddleOfLinkedListWithRemoveAll() {
        linkedList.removeAll(Collections.singleton(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromMiddleOfLinkedListWithRemoveIf() {
        linkedList.removeIf(s -> s.equals(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromMiddleOfLinkedListWithIterator() {
        removeIfBasedOnIterator(linkedList, s -> s.equals(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromMiddleOfArrayListWithRemoveAll() {
        arrayList.removeAll(Collections.singleton(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromMiddleOfArrayListWithRemoveIf() {
        arrayList.removeIf(s -> s.equals(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromMiddleOfArrayListWithIterator() {
        removeIfBasedOnIterator(arrayList, s -> s.equals(STRING_TO_REMOVAL));
    }

    private static void removeIfBasedOnIterator(List<String> list, Predicate<String> predicate) {
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (predicate.test(iterator.next())){
                iterator.remove();
            }
        }
    }

    /*
     * Benchmark                                                                 Mode  Cnt   Score   Error  Units
     * RemoveIfBenchmark.testRemovingElementFromMiddleOfArrayListWithIterator    avgt   50   7.824 ± 0.653  ms/op
     * RemoveIfBenchmark.testRemovingElementFromMiddleOfArrayListWithRemoveAll   avgt   50   8.638 ± 0.395  ms/op
     * RemoveIfBenchmark.testRemovingElementFromMiddleOfArrayListWithRemoveIf    avgt   50  12.088 ± 0.462  ms/op
     * RemoveIfBenchmark.testRemovingElementFromMiddleOfLinkedListWithIterator   avgt   50   9.447 ± 0.539  ms/op
     * RemoveIfBenchmark.testRemovingElementFromMiddleOfLinkedListWithRemoveAll  avgt   50   9.054 ± 0.620  ms/op
     * RemoveIfBenchmark.testRemovingElementFromMiddleOfLinkedListWithRemoveIf   avgt   50   9.416 ± 0.489  ms/op
     */
}
