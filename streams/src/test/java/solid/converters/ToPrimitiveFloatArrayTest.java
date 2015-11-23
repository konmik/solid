package solid.converters;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertArrayEquals;

public class ToPrimitiveFloatArrayTest {
    @Test
    public void testToPrimitiveFloatArray() throws Exception {
        assertArrayEquals(new float[]{1, 2, 3}, ToPrimitiveFloatArray.toPrimitiveFloatArray().call(Arrays.asList(1f, 2f, 3f)), 0);
        assertArrayEquals(new float[]{}, ToPrimitiveFloatArray.toPrimitiveFloatArray().call(Collections.<Float>emptyList()), 0);
    }
}
