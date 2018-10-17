package edu.khai.simonenko.collections.adding;

import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 20, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 30, time = 20, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@CompilerControl(CompilerControl.Mode.DONT_INLINE)
public class AddElementsToListWithoutInitialCapacityAndWith {

    private static final List<String> LIST = new ArrayList<>();

    @Setup
    public void init() {
        for (int i = 0; i < 100; ++i) {
            LIST.add(RandomStringUtils.randomAlphanumeric(5));
        }
    }

    @Benchmark
    public void testAddingElementsToListOneByOneWithoutInitialCapacity() {
        List<String> result = new ArrayList<>();
        for (var element : LIST) {
            result.add(element);
        }
    }

    @Benchmark
    public void testAddingAllElementsToListWithoutInitialCapacity() {
        List<String> result = new ArrayList<>();
        result.addAll(LIST);
    }

    @Benchmark
    public void testAddingElementsToListOneByOneWithInitialCapacity() {
        List<String> result = new ArrayList<>(LIST.size());
        for (var element : LIST) {
            result.add(element);
        }
    }

    @Benchmark
    public void testAddingAllElementsToListWithInitialCapacity() {
        List<String> result = new ArrayList<>(LIST.size());
        result.addAll(LIST);
    }

    /*
     * Benchmark                                                                                              Mode  Cnt    Score    Error  Units
     * AddElementsToListWithoutInitialCapacityAndWith.testAddingAllElementsToListWithInitialCapacity          avgt  150  165.141 ± 24.709  ns/op
     * AddElementsToListWithoutInitialCapacityAndWith.testAddingAllElementsToListWithoutInitialCapacity       avgt  150  162.037 ± 23.617  ns/op
     * AddElementsToListWithoutInitialCapacityAndWith.testAddingElementsToListOneByOneWithInitialCapacity     avgt  150  429.116 ± 15.566  ns/op
     * AddElementsToListWithoutInitialCapacityAndWith.testAddingElementsToListOneByOneWithoutInitialCapacity  avgt  150  597.379 ± 53.492  ns/op
     */
}
