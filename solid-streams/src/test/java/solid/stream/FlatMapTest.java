package solid.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import solid.functions.SolidFunc1;

import static java.util.Arrays.asList;
import static test_utils.AssertIterableEquals.assertIterableEquals;

public class FlatMapTest {
    @Test
    public void testIterator() throws Exception {
        assertIterableEquals(asList("2", "3", "4", "3", "4", "5", "4", "5", "6"), new FlatMap<>(asList(1, 2, 3), value -> asList("" + (value + 1), "" + (value + 2), "" + (value + 3))));
        assertIterableEquals(Arrays.<Integer>asList(null, null, null, null), new FlatMap<>(Arrays.<Integer>asList(null, null), value -> Arrays.asList(null, null)));
        assertIterableEquals(Collections.emptyList(), new FlatMap<>(Collections.emptyList(), value -> null));
    }
}
