package solid.converters;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertArrayEquals;

public class ToPrimitiveByteArrayTest {
    @Test
    public void testToPrimitiveByteArray() throws Exception {
        assertArrayEquals(new byte[]{1, 2, 3}, ToPrimitiveByteArray.toPrimitiveByteArray().call(Arrays.asList((byte) 1, (byte) 2, (byte) 3)));
        assertArrayEquals(new byte[]{}, ToPrimitiveByteArray.toPrimitiveByteArray().call(Collections.<Byte>emptyList()));
    }
}
