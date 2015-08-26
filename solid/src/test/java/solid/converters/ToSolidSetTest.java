package solid.converters;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static test_utils.AssertIterableEquals.assertIterableEquals;

public class ToSolidSetTest {
    @Test
    public void testToSolidList() throws Exception {
        assertIterableEquals(Arrays.asList(1, 2, 3), ToSolidSet.<Integer>toSolidSet().call(Arrays.asList(1, 2, 3)));
        assertIterableEquals(Collections.<Integer>emptyList(), ToSolidSet.<Integer>toSolidSet().call(Collections.<Integer>emptyList()));
        assertIterableEquals(Arrays.asList(1, 2, 3), ToSolidSet.<Integer>toSolidSet(10).call(Arrays.asList(1, 2, 3)));
        assertIterableEquals(Collections.<Integer>emptyList(), ToSolidSet.<Integer>toSolidSet(10).call(Collections.<Integer>emptyList()));
    }

    @Test
    public void testNewAndCall() throws Exception {
        assertIterableEquals(Arrays.asList(1, 2, 3), new ToSolidSet<Integer>(10).call(Arrays.asList(1, 2, 3)));
        assertIterableEquals(Collections.<Integer>emptyList(), new ToSolidSet<Integer>(10).call(Collections.<Integer>emptyList()));
    }
}
