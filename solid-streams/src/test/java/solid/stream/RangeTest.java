package solid.stream;

import org.junit.Test;

import java.util.Collections;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static junit.framework.Assert.assertTrue;
import static test_utils.AssertIterableEquals.assertIterableEquals;

public class RangeTest {
    @Test
    public void testIterator() throws Exception {
        assertIterableEquals(asList(1, 2, 3), new Range(1, 3));
        assertIterableEquals(asList(-3, -2, -1), new Range(-3, 3));
        assertIterableEquals(Collections.<Integer>emptyList(), new Range(1, -1));
        assertIterableEquals(singletonList(1), new Range(1, 1));
    }

    @Test
    public void testFactory() throws Exception {
        assertTrue(Range.range(1, 1) instanceof Range);
        assertIterableEquals(asList(1, 2, 3), Range.range(1, 3));
    }
}
