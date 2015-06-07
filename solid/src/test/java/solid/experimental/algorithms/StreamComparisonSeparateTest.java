package solid.experimental.algorithms;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import solid.experimental.collections.Pair;
import solid.functions.SolidFunc2;

import static test_utils.AssertIterableEquals.assertIterableEquals;

public class StreamComparisonSeparateTest {
    @Test
    public void testComparison() throws Exception {
        StreamComparisonSeparate<Integer, String> comparison =
            new StreamComparisonSeparate<>(Arrays.asList(1, 2, 3, null), Arrays.asList("3", "4", "5", null),
                new SolidFunc2<Integer, String, Boolean>() {
                    @Override
                    public Boolean call(Integer value1, String value2) {
                        return value1 == null ? value2 == null : Integer.toString(value1).equals(value2);
                    }
                });
        assertIterableEquals(Arrays.asList(new Pair<>(3, "3"), new Pair<Integer, String>(null, null)), comparison.both());
        assertIterableEquals(Arrays.asList(1, 2), comparison.first());
        assertIterableEquals(Arrays.asList("4", "5"), comparison.second());
    }

    @Test
    public void testNulls() throws Exception {
        StreamComparisonSeparate<Integer, String> comparison =
            new StreamComparisonSeparate<>(Arrays.<Integer>asList(null, null), Arrays.<String>asList(null, null),
                new SolidFunc2<Integer, String, Boolean>() {
                    @Override
                    public Boolean call(Integer value1, String value2) {
                        return value1 == null ? value2 == null : Integer.toString(value1).equals(value2);
                    }
                });
        assertIterableEquals(Arrays.asList(new Pair<Integer, String>(null, null), new Pair<Integer, String>(null, null)), comparison.both());
        assertIterableEquals(Collections.<Integer>emptyList(), comparison.first());
        assertIterableEquals(Collections.<String>emptyList(), comparison.second());
    }

    @Test
    public void testEmpty() throws Exception {
        StreamComparisonSeparate<Object, Object> comparison = new StreamComparisonSeparate<>(Collections.emptyList(), Collections.emptyList());
        assertIterableEquals(Collections.<Pair<Object, Object>>emptyList(), comparison.both());
        assertIterableEquals(Collections.emptyList(), comparison.first());
        assertIterableEquals(Collections.emptyList(), comparison.second());
    }
}
