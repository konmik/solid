package solid.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static test_utils.AssertIterableEquals.assertIterableEquals;

public class MergeTest {
    @Test
    public void testIterator() throws Exception {
        assertIterableEquals(Arrays.asList(1, 2, 3, 4, 5, 6), new Merge<>(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6)));
        assertIterableEquals(Arrays.asList(1, 2, 3), new Merge<>(Arrays.asList(1, 2, 3), Collections.<Integer>emptyList()));
        assertIterableEquals(Arrays.asList(4, 5, 6), new Merge<>(Collections.<Integer>emptyList(), Arrays.asList(4, 5, 6)));
        assertIterableEquals(Collections.<Integer>emptyList(), new Merge<>(Collections.<Integer>emptyList(), Collections.<Integer>emptyList()));
        assertIterableEquals(Arrays.asList(null, null, null, null), new Merge<>(Arrays.asList(null, null), Arrays.asList(null, null)));
    }
}
