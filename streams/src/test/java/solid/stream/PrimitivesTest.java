package solid.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static test_utils.AssertIterableEquals.assertIterableEquals;

public class PrimitivesTest {

    @Test
    public void boxedIntegers() throws Exception {
        assertIterableEquals(Arrays.asList(1, 2, 3), Primitives.box(new int[]{1, 2, 3}));
        assertIterableEquals(Collections.<Integer>emptyList(), Primitives.box(new int[0]));
        assertIterableEquals(Primitives.box(new int[]{1, 2, 3}), Primitives.box(new int[]{1, 2, 3}));
    }

    @Test
    public void boxedLongs() throws Exception {
        assertIterableEquals(Arrays.asList(1l, 2l, 3l), Primitives.box(new long[]{1, 2, 3}));
        assertIterableEquals(Collections.<Long>emptyList(), Primitives.box(new long[0]));
        assertIterableEquals(Primitives.box(new long[]{1, 2, 3}), Primitives.box(new long[]{1, 2, 3}));
    }

    @Test
    public void boxedFloats() throws Exception {
        assertIterableEquals(Arrays.asList(1f, 2f, 3f), Primitives.box(new float[]{1, 2, 3}));
        assertIterableEquals(Collections.<Float>emptyList(), Primitives.box(new float[0]));
        assertIterableEquals(Primitives.box(new float[]{1, 2, 3}), Primitives.box(new float[]{1, 2, 3}));
    }

    @Test
    public void boxedBytes() throws Exception {
        assertIterableEquals(Arrays.asList((byte) 1, (byte) 2, (byte) 3), Primitives.box(new byte[]{1, 2, 3}));
        assertIterableEquals(Collections.<Byte>emptyList(), Primitives.box(new byte[0]));
        assertIterableEquals(Primitives.box(new byte[]{1, 2, 3}), Primitives.box(new byte[]{1, 2, 3}));
    }

    @Test
    public void boxedDoubles() throws Exception {
        assertIterableEquals(Arrays.asList(1., 2., 3.), Primitives.box(new double[]{1, 2, 3}));
        assertIterableEquals(Collections.<Double>emptyList(), Primitives.box(new double[0]));
        assertIterableEquals(Primitives.box(new double[]{1, 2, 3}), Primitives.box(new double[]{1, 2, 3}));
    }
}
