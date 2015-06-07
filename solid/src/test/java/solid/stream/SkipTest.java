package solid.stream;

import org.junit.Test;

import java.util.Collections;

import static java.util.Arrays.asList;
import static test_utils.AssertIterableEquals.assertIterableEquals;

public class SkipTest {
    @Test
    public void testIterator() throws Exception {
        assertIterableEquals(asList(3, 4, 5), new Skip<>(asList(1, 2, 3, 4, 5), 2));
        assertIterableEquals(Collections.<Integer>emptyList(), new Skip<>(asList(1, 2, 3, 4, 5), 5));
        assertIterableEquals(Collections.<Integer>emptyList(), new Skip<>(asList(1, 2, 3), 5));
        assertIterableEquals(Collections.<Integer>emptyList(), new Skip<>(Collections.<Integer>emptyList(), 5));
        assertIterableEquals(asList(null, null), new Skip<>(asList(null, null, null), 1));
    }
}
