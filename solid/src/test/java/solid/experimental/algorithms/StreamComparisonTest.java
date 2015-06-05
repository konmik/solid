package solid.experimental.algorithms;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static test_utils.AssertIterableEquals.assertIterableEquals;

public class StreamComparisonTest {
    @Test
    public void testComparison() throws Exception {
        StreamComparison<Integer> comparison = new StreamComparison<>(Arrays.asList(1, 2, 3, null), Arrays.asList(3, 4, 5, null));
        assertIterableEquals(Arrays.asList(3, null), comparison.both());
        assertIterableEquals(Arrays.asList(1, 2), comparison.first());
        assertIterableEquals(Arrays.asList(4, 5), comparison.second());

    }

    @Test
    public void testNulls() throws Exception {
        StreamComparison<Integer> comparison = new StreamComparison<>(Arrays.<Integer>asList(null, null), Arrays.<Integer>asList(null, null));
        assertIterableEquals(Arrays.<Integer>asList(null, null), comparison.both());
        assertIterableEquals(Collections.<Integer>emptyList(), comparison.first());
        assertIterableEquals(Collections.<Integer>emptyList(), comparison.second());
    }

    @Test
    public void testEmpty() throws Exception {
        StreamComparison<Object> comparison = new StreamComparison<>(Collections.emptyList(), Collections.emptyList());
        assertIterableEquals(Collections.emptyList(), comparison.both());
        assertIterableEquals(Collections.emptyList(), comparison.first());
        assertIterableEquals(Collections.emptyList(), comparison.second());
    }
}
