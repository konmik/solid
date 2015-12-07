package solid.collectors;

import org.junit.Test;

import solid.stream.Stream;

import static org.junit.Assert.assertArrayEquals;
import static solid.collectors.ToArray.toArray;

public class ToArrayTest {

    @Test
    public void testToArray() throws Exception {
        assertArrayEquals(new Integer[]{1, 2, 3}, Stream.of(1, 2, 3).collect(toArray(Integer.class)));
        assertArrayEquals(new Integer[]{null}, Stream.of((Integer) null).collect(toArray(Integer.class)));
        assertArrayEquals(new Integer[]{}, Stream.<Integer>of().collect(toArray(Integer.class)));
    }
}