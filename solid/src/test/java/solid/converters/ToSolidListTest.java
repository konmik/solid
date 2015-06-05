package solid.converters;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static test_utils.AssertIterableEquals.assertIterableEquals;

public class ToSolidListTest {
    @Test
    public void testToSolidList() throws Exception {
        assertIterableEquals(Arrays.asList(1, 2, 3), ToSolidList.<Integer>toSolidList().call(Arrays.asList(1, 2, 3)));
        assertIterableEquals(Collections.<Integer>emptyList(), ToSolidList.<Integer>toSolidList().call(Collections.<Integer>emptyList()));
    }

    @Test
    public void testNewAndCall() throws Exception {
        assertIterableEquals(Arrays.asList(1, 2, 3), new ToSolidList<Integer>().call(Arrays.asList(1, 2, 3)));
        assertIterableEquals(Collections.<Integer>emptyList(), new ToSolidList<Integer>().call(Collections.<Integer>emptyList()));
    }
}