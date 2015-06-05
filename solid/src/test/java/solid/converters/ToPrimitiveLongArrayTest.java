package solid.converters;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertArrayEquals;

public class ToPrimitiveLongArrayTest {
    @Test
    public void testToPrimitiveByteArray() throws Exception {
        assertArrayEquals(new long[]{1, 2, 3}, ToPrimitiveLongArray.toPrimitiveLongArray().call(Arrays.asList(1l, 2l, 3l)));
        assertArrayEquals(new long[]{}, ToPrimitiveLongArray.toPrimitiveLongArray().call(Collections.<Long>emptyList()));
    }

    @Test
    public void testCall() throws Exception {
        assertArrayEquals(new long[]{1, 2, 3}, new ToPrimitiveLongArray().call(Arrays.asList(1l, 2l, 3l)));
        assertArrayEquals(new long[]{}, new ToPrimitiveLongArray().call(Collections.<Long>emptyList()));
    }
}
