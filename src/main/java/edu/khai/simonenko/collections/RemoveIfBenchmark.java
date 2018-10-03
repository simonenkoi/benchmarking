package edu.khai.simonenko.collections;

import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 15, time = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
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
    public void testRemovingElementFromMiddleOfLinkedListWithRemove() {
        linkedList.remove(STRING_TO_REMOVAL);
    }

    @Benchmark
    public void testRemovingElementFromMiddleOfLinkedListWithRemoveIf() {
        linkedList.removeIf(s -> s.equals(STRING_TO_REMOVAL));
    }

    @Benchmark
    public void testRemovingElementFromMiddleOfArrayListWithRemove() {
        arrayList.remove(STRING_TO_REMOVAL);
    }

    @Benchmark
    public void testRemovingElementFromMiddleOfArrayListWithRemoveIf() {
        arrayList.removeIf(s -> s.equals(STRING_TO_REMOVAL));
    }

    /*
     * Benchmark                                                                Mode  Cnt   Score   Error  Units
     * RemoveIfBenchmark.testRemovingElementFromMiddleOfArrayListWithRemove     avgt   15   2.723 ± 0.118  ms/op
     * RemoveIfBenchmark.testRemovingElementFromMiddleOfArrayListWithRemoveIf   avgt   15  13.118 ± 0.456  ms/op
     * RemoveIfBenchmark.testRemovingElementFromMiddleOfLinkedListWithRemove    avgt   15   3.089 ± 0.274  ms/op
     * RemoveIfBenchmark.testRemovingElementFromMiddleOfLinkedListWithRemoveIf  avgt   15   7.484 ± 0.136  ms/op
     */
}
