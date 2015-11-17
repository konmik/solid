package solid.converters;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static test_utils.AssertIterableEquals.assertIterableEquals;

public class ToArrayListTest {
    @Test
    public void testToArrayList() throws Exception {
        assertIterableEquals(Arrays.asList(1, 2, 3), ToArrayList.<Integer>toArrayList().call(Arrays.asList(1, 2, 3)));
        assertIterableEquals(Collections.<Integer>emptyList(), ToArrayList.<Integer>toArrayList().call(Collections.<Integer>emptyList()));
        assertIterableEquals(Arrays.asList(1, 2, 3), ToArrayList.<Integer>toArrayList(10).call(Arrays.asList(1, 2, 3)));
        assertIterableEquals(Collections.<Integer>emptyList(), ToArrayList.<Integer>toArrayList(10).call(Collections.<Integer>emptyList()));
    }
}