package solid.stream;

import org.junit.Test;

import java.util.Collections;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static solid.stream.Range.range;
import static test_utils.AssertIterableEquals.assertIterableEquals;

public class RangeTest {
    @Test
    public void testRange() throws Exception {
        assertIterableEquals(asList(1, 2, 3), range(1, 4));
        assertIterableEquals(asList(1, 3, 5), range(1, 6, 2));
        assertIterableEquals(asList(-3, -2, -1), range(-3, 0));
        assertIterableEquals(Collections.<Integer>emptyList(), range(1, 1));
        assertIterableEquals(singletonList(1), range(1, 2));
    }
}