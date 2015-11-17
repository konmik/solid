package solid.stream;

import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static test_utils.AssertIterableEquals.assertIterableEquals;

public class CopyArrayTest {
    @Test
    public void testIterator() throws Exception {
        assertIterableEquals(asList(1, 2, 3), new CopyArray<>(new Integer[]{1, 2, 3}));
        assertIterableEquals(asList(null, null), new CopyArray<Object>(new Integer[]{null, null}));
        assertIterableEquals(emptyList(), new CopyArray<Object>(new Integer[]{}));
    }
}
