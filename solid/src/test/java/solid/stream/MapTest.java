package solid.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import solid.functions.SolidFunc1;

import static test_utils.AssertIterableEquals.assertIterableEquals;

public class MapTest {
    @Test
    public void testIterator() throws Exception {
        assertIterableEquals(Arrays.asList("1", "2", "3"), new Map<>(Arrays.asList(1, 2, 3), new SolidFunc1<Integer, String>() {
            @Override
            public String call(Integer value) {
                return value.toString();
            }
        }));
        assertIterableEquals(Arrays.<Integer>asList(null, null), new Map<>(Arrays.<Integer>asList(null, null), new SolidFunc1<Integer, Integer>() {
            @Override
            public Integer call(Integer value) {
                return null;
            }
        }));
        assertIterableEquals(Collections.emptyList(), new Map<>(Collections.emptyList(), new SolidFunc1<Object, Object>() {
            @Override
            public Object call(Object value) {
                return null;
            }
        }));
    }
}
