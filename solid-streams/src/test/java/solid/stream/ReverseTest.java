package solid.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static test_utils.AssertIterableEquals.assertIterableEquals;

public class ReverseTest {
    @Test
    public void testIterator() throws Exception {
        assertIterableEquals(Arrays.asList(1, 2, 3), new Reverse<>(Arrays.asList(3, 2, 1)));
        assertIterableEquals(Arrays.asList(null, null), new Reverse<>(Arrays.asList(null, null)));
        assertIterableEquals(Collections.singletonList(1), new Reverse<>(Collections.singletonList(1)));
        assertIterableEquals(Collections.emptyList(), new Reverse<>(Collections.emptyList()));
    }
}