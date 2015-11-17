package solid.converters;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertArrayEquals;

public class ToPrimitiveDoubleArrayTest {
    @Test
    public void testToPrimitiveDoubleArray() throws Exception {
        assertArrayEquals(new double[]{1, 2, 3}, ToPrimitiveDoubleArray.toPrimitiveDoubleArray().call(Arrays.asList(1., 2., 3.)), 0);
        assertArrayEquals(new double[]{}, ToPrimitiveDoubleArray.toPrimitiveDoubleArray().call(Collections.<Double>emptyList()), 0);
    }

    @Test
    public void testCall() throws Exception {
        assertArrayEquals(new double[]{1, 2, 3}, new ToPrimitiveDoubleArray().call(Arrays.asList(1., 2., 3.)), 0);
        assertArrayEquals(new double[]{}, new ToPrimitiveDoubleArray().call(Collections.<Double>emptyList()), 0);
    }
}
