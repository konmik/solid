package solid.converters;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static test_utils.AssertIterableEquals.assertIterableEquals;

public class BytesTest {
    @Test
    public void testIterator() throws Exception {
        assertIterableEquals(Arrays.asList((byte)1, (byte)2, (byte)3), new Bytes(new byte[]{1, 2, 3}));
        assertIterableEquals(Collections.<Byte>emptyList(), new Bytes(new byte[0]));
    }
}