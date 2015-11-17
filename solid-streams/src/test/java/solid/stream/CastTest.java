package solid.stream;

import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static test_utils.AssertIterableEquals.assertIterableEquals;

public class CastTest {
    @Test
    public void testIterator() throws Exception {
        List<Integer> list = Arrays.asList(1, 2, 3);
        List<Number> numbers = Arrays.asList((Number)1, 2, 3);
        assertIterableEquals(numbers, new Cast<>(list, Number.class));

        assertIterableEquals(emptyList(), new Cast<>(emptyList(), null));
    }

    @Test(expected = ClassCastException.class)
    public void testIteratorException() throws Exception {
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        //noinspection unchecked
        assertIterableEquals(numbers, new Cast(numbers, BigInteger.class));
    }
}
