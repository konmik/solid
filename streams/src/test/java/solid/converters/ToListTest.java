package solid.converters;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import solid.collectors.ToList;

import static test_utils.AssertIterableEquals.assertIterableEquals;

public class ToListTest {
    @Test
    public void testToList() throws Exception {
        assertIterableEquals(Arrays.asList(1, 2, 3), ToList.<Integer>toList().call(Arrays.asList(1, 2, 3)));
        assertIterableEquals(Collections.<Integer>emptyList(), ToList.<Integer>toList().call(Collections.<Integer>emptyList()));
        assertIterableEquals(Arrays.asList(1, 2, 3), ToList.<Integer>toList(10).call(Arrays.asList(1, 2, 3)));
        assertIterableEquals(Collections.<Integer>emptyList(), ToList.<Integer>toList(10).call(Collections.<Integer>emptyList()));
    }
}