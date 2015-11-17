package solid.stream;

import org.junit.Test;

import java.util.Collections;

import static test_utils.AssertIterableEquals.assertIterableEquals;

public class EmptyTest {
    @Test
    public void testIterator() throws Exception {
        assertIterableEquals(Collections.emptyList(), new Empty<>());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testException() throws Exception {
        new Empty<>().iterator().next();
    }

    @Test
    public void testStatic() throws Exception {
        assertIterableEquals(Collections.emptyList(), Empty.empty());
    }
}
