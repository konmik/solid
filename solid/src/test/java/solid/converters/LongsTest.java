package solid.converters;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static test_utils.AssertIterableEquals.assertIterableEquals;

public class LongsTest {
    @Test
    public void testIterator() throws Exception {
        assertIterableEquals(Arrays.asList(1l, 2l, 3l), new Longs(new long[]{1, 2, 3}));
        assertIterableEquals(Collections.<Long>emptyList(), new Longs(new long[0]));
    }
}