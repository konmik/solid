package solid.converters;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static test_utils.AssertIterableEquals.assertIterableEquals;

public class FloatsTest {
    @Test
    public void testIterator() throws Exception {
        assertIterableEquals(Arrays.asList(1f, 2f, 3f), new Floats(new float[]{1, 2, 3}));
        assertIterableEquals(Collections.<Float>emptyList(), new Floats(new float[0]));
        assertIterableEquals(new Floats(new float[]{1, 2, 3}), Floats.floats(new float[]{1, 2, 3}));
    }
}
