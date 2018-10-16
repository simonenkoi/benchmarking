package edu.khai.simonenko.collections.adding;

import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 25, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 25, timeUnit = TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@CompilerControl(CompilerControl.Mode.DONT_INLINE)
public class AddElementsToListWithoutInitialCapacityAndWith {

    private static final List<String> LIST = new ArrayList<>();

    @Setup
    public void init() {
        for (int i = 0; i < 1_000_000; ++i) {
            LIST.add(RandomStringUtils.randomAlphanumeric(5));
        }
    }

    @Benchmark
    public void testAddingElementToListWithoutInitialCapacity() {
        List<String> result = new ArrayList<>();
        for (var string : LIST) {
            result.add(string);
        }
    }

    @Benchmark
    public void testAddingElementToListWithInitialCapacity() {
        List<String> result = new ArrayList<>(LIST.size());
        for (var string : LIST) {
            result.add(string);
        }
    }

    /*
     * Benchmark                                                                                     Mode  Cnt   Score   Error  Units
     * AddElementsToListWithoutInitialCapacityAndWith.testAddingElementToListWithInitialCapacity     avgt   50  12.715 ± 1.574  ms/op
     * AddElementsToListWithoutInitialCapacityAndWith.testAddingElementToListWithoutInitialCapacity  avgt   50  13.205 ± 0.495  ms/op
     */
}
