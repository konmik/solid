package solid.converters;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertArrayEquals;

public class ToPrimitiveIntegerArrayTest {
    @Test
    public void testToPrimitiveByteArray() throws Exception {
        assertArrayEquals(new int[]{1, 2, 3}, ToPrimitiveIntegerArray.toPrimitiveIntegerArray().call(Arrays.asList(1, 2, 3)));
        assertArrayEquals(new int[]{}, ToPrimitiveIntegerArray.toPrimitiveIntegerArray().call(Collections.<Integer>emptyList()));
    }

    @Test
    public void testCall() throws Exception {
        assertArrayEquals(new int[]{1, 2, 3}, new ToPrimitiveIntegerArray().call(Arrays.asList(1, 2, 3)));
        assertArrayEquals(new int[]{}, new ToPrimitiveIntegerArray().call(Collections.<Integer>emptyList()));
    }
}
