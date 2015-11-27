package solid.converters;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import solid.collectors.ToArrays;

import static org.junit.Assert.assertArrayEquals;
import static solid.collectors.ToArrays.toBytes;
import static solid.stream.Stream.of;

public class ToArraysTest {

    @Test
    public void toBytes1() throws Exception {
        of((byte) 1, (byte) 2, (byte) 3).collect(ToArrays.toBytes());
        assertArrayEquals(new byte[]{1, 2, 3}, toBytes().call(Arrays.asList((byte) 1, (byte) 2, (byte) 3)));
        assertArrayEquals(new byte[]{}, toBytes().call(Collections.<Byte>emptyList()));
    }

    @Test
    public void toDoubles() throws Exception {
        of(1., 2., 3.).collect(ToArrays.toDoubles());
        assertArrayEquals(new double[]{1., 2., 3.}, ToArrays.toDoubles().call(Arrays.asList((double) 1, (double) 2, (double) 3)), 0);
        assertArrayEquals(new double[]{}, ToArrays.toDoubles().call(Collections.<Double>emptyList()), 0);
    }

    @Test
    public void toFloats() throws Exception {
        of(1f, 2f, 3f).collect(ToArrays.toFloats());
        assertArrayEquals(new float[]{1, 2, 3}, ToArrays.toFloats().call(Arrays.asList(1f, 2f, 3f)), 0);
        assertArrayEquals(new float[]{}, ToArrays.toFloats().call(Collections.<Float>emptyList()), 0);
    }

    @Test
    public void toInts() throws Exception {
        of(1, 2, 3).collect(ToArrays.toInts());
        assertArrayEquals(new int[]{1, 2, 3}, ToArrays.toInts().call(Arrays.asList(1, 2, 3)));
        assertArrayEquals(new int[]{}, ToArrays.toInts().call(Collections.<Integer>emptyList()));
    }

    @Test
    public void toLongs() throws Exception {
        of(1l, 2l, 3l).collect(ToArrays.toLongs());
        assertArrayEquals(new long[]{1, 2, 3}, ToArrays.toLongs().call(Arrays.asList(1L, 2L, 3L)));
        assertArrayEquals(new long[]{}, ToArrays.toLongs().call(Collections.<Long>emptyList()));
    }

    @Test
    public void toShorts() throws Exception {
        of((short) 1, (short) 2, (short) 3).collect(ToArrays.toShorts());
        assertArrayEquals(new short[]{1, 2, 3}, ToArrays.toShorts().call(Arrays.asList((short) 1, (short) 2, (short) 3)));
        assertArrayEquals(new short[]{}, ToArrays.toShorts().call(Collections.<Short>emptyList()));
    }
}
