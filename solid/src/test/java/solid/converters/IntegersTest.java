package solid.converters;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static test_utils.AssertIterableEquals.assertIterableEquals;

public class IntegersTest {
    @Test
    public void testIterator() throws Exception {
        assertIterableEquals(Arrays.asList(1, 2, 3), new Integers(new int[]{1, 2, 3}));
        assertIterableEquals(Collections.<Integer>emptyList(), new Integers(new int[0]));
    }
}