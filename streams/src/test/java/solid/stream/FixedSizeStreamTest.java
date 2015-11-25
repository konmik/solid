package solid.stream;

import org.junit.Test;

import java.util.Iterator;

import solid.functions.Func1;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static test_utils.AssertIterableEquals.assertIterableEquals;

public class FixedSizeStreamTest {

    @Test
    public void testIterator() throws Exception {
        assertIterableEquals(asList("0", "1", "2"), create3());
        assertIterableEquals(emptyList(), new FixedSizeStream<>(0, null));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testReadOnly() throws Exception {
        Iterator<String> iterator = create3().iterator();
        iterator.next();
        iterator.remove();
    }

    private FixedSizeStream<String> create3() {
        return new FixedSizeStream<>(3, new Func1<Integer, String>() {
            @Override
            public String call(Integer value) {
                return value.toString();
            }
        });
    }
}