package solid.stream;

import org.junit.Test;

import java.util.Collections;

import static test_utils.AssertIterableEquals.assertIterableEquals;

public class EmptyTest {
    @Test
    public void testIterator() throws Exception {
        assertIterableEquals(Collections.emptyList(), new Empty<>());
    }
}