package solid.stream;

import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static test_utils.AssertIterableEquals.assertIterableEquals;

public class TakeTest {
    @Test
    public void testIterator() throws Exception {
        assertIterableEquals(asList(1, 2), new Take<>(asList(1, 2, 3), 2));
        assertIterableEquals(asList(1, 2), new Take<>(asList(1, 2), 10));
        assertIterableEquals(asList(null, null), new Take<>(asList(null, null), 3));
        assertIterableEquals(emptyList(), new Take<>(emptyList(), 10));
        assertIterableEquals(emptyList(), new Take<>(emptyList(), 0));
    }
}
