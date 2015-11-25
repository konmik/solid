package solid.converters;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertArrayEquals;

public class ToNumbersTest {

    @Test
    public void toBytes() throws Exception {
        assertArrayEquals(new byte[]{1, 2, 3}, ToNumbers.toBytes().call(Arrays.asList((byte) 1, (byte) 2, (byte) 3)));
        assertArrayEquals(new byte[]{}, ToNumbers.toBytes().call(Collections.<Byte>emptyList()));
        assertArrayEquals(new byte[]{1, 2, 3}, ToNumbers.toBytes().call(Arrays.asList(1L, 2L, 3L)));
        assertArrayEquals(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13},
            ToNumbers.toBytes().call(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)));
    }

    @Test
    public void toDoubles() throws Exception {
        assertArrayEquals(new double[]{1., 2., 3.}, ToNumbers.toDoubles().call(Arrays.asList((double) 1, (double) 2, (double) 3)), 0);
        assertArrayEquals(new double[]{}, ToNumbers.toDoubles().call(Collections.<Byte>emptyList()), 0);
        assertArrayEquals(new double[]{1, 2, 3}, ToNumbers.toDoubles().call(Arrays.asList(1L, 2L, 3L)), 0);
        assertArrayEquals(new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13},
            ToNumbers.toDoubles().call(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)), 0);
    }

    @Test
    public void toFloats() throws Exception {
        assertArrayEquals(new float[]{1, 2, 3}, ToNumbers.toFloats().call(Arrays.asList((double) 1, (double) 2, (double) 3)), 0);
        assertArrayEquals(new float[]{}, ToNumbers.toFloats().call(Collections.<Byte>emptyList()), 0);
        assertArrayEquals(new float[]{1, 2, 3}, ToNumbers.toFloats().call(Arrays.asList(1L, 2L, 3L)), 0);
        assertArrayEquals(new float[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13},
            ToNumbers.toFloats().call(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)), 0);
    }

    @Test
    public void toInts() throws Exception {
        assertArrayEquals(new int[]{1, 2, 3}, ToNumbers.toInts().call(Arrays.asList(1, 2, 3)));
        assertArrayEquals(new int[]{}, ToNumbers.toInts().call(Collections.<Byte>emptyList()));
        assertArrayEquals(new int[]{1, 2, 3}, ToNumbers.toInts().call(Arrays.asList(1L, 2L, 3L)));
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13},
            ToNumbers.toInts().call(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)));
    }

    @Test
    public void toLongs() throws Exception {
        assertArrayEquals(new long[]{1, 2, 3}, ToNumbers.toLongs().call(Arrays.asList(1, 2, 3)));
        assertArrayEquals(new long[]{}, ToNumbers.toLongs().call(Collections.<Byte>emptyList()));
        assertArrayEquals(new long[]{1, 2, 3}, ToNumbers.toLongs().call(Arrays.asList(1L, 2L, 3L)));
        assertArrayEquals(new long[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13},
            ToNumbers.toLongs().call(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)));
    }

    @Test
    public void toShorts() throws Exception {
        assertArrayEquals(new short[]{1, 2, 3}, ToNumbers.toShorts().call(Arrays.asList(1, 2, 3)));
        assertArrayEquals(new short[]{}, ToNumbers.toShorts().call(Collections.<Byte>emptyList()));
        assertArrayEquals(new short[]{1, 2, 3}, ToNumbers.toShorts().call(Arrays.asList(1L, 2L, 3L)));
        assertArrayEquals(new short[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13},
            ToNumbers.toShorts().call(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)));
    }
}
