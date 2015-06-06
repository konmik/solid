package solid.converters;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static test_utils.AssertIterableEquals.assertIterableEquals;

public class DoublesTest {
    @Test
    public void testIterator() throws Exception {
        assertIterableEquals(Arrays.asList(1., 2., 3.), new Doubles(new double[]{1, 2, 3}));
        assertIterableEquals(Collections.<Double>emptyList(), new Doubles(new double[0]));
        assertIterableEquals(new Doubles(new double[]{1, 2, 3}), Doubles.doubles(new double[]{1, 2, 3}));
    }
}
