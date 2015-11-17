package solid.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static test_utils.AssertIterableEquals.assertIterableEquals;

public class CopyTest {
    @Test
    public void testIterator() throws Exception {
        List<Integer> list = Arrays.asList(1, 2, 3);
        assertIterableEquals(list, new Copy<>(list));
        list = Collections.emptyList();
        assertIterableEquals(list, new Copy<>(list));
    }
}
