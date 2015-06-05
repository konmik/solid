package solid.stream;

import org.junit.Test;

import java.util.Collections;

import static test_utils.AssertIterableEquals.assertIterableEquals;

public class SingleTest {
    @Test
    public void testIterator() throws Exception {
        assertIterableEquals(Collections.singletonList(1), new Single<>(1));
        assertIterableEquals(Collections.singletonList(null), new Single<>(null));
    }
}
