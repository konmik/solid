package solid.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import solid.functions.SolidFunc1;

import static java.util.Arrays.asList;
import static test_utils.AssertIterableEquals.assertIterableEquals;

public class FilterTest {
    @Test
    public void testIterator() throws Exception {
        List<Integer> list123 = asList(1, 2, 3);
        assertIterableEquals(list123, new Filter<>(asList(1, 2, 3, 4), value -> value != 4));
        assertIterableEquals(list123, new Filter<>(asList(4, 1, 2, 3), value -> value != 4));
        assertIterableEquals(list123, new Filter<>(asList(1, 2, 4, 3), value -> value != 4));
        assertIterableEquals(list123, new Filter<>(asList(1, 2, 3), value -> true));
        assertIterableEquals(Collections.<Integer>emptyList(), new Filter<>(asList(1, 2, 4, 3), value -> false));
        assertIterableEquals(Collections.<Integer>emptyList(), new Filter<>(Collections.<Integer>emptyList(), value -> true));
        assertIterableEquals(Collections.<Integer>emptyList(), new Filter<>(Arrays.<Integer>asList(null, null), value -> false));
    }
}
