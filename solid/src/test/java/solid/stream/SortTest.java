package solid.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import static test_utils.AssertIterableEquals.assertIterableEquals;

public class SortTest {
    @Test
    public void testIterator() throws Exception {
        assertIterableEquals(Arrays.asList(1, 2, 3), new Sort<>(Arrays.asList(3, 2, 1), new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return lhs < rhs ? -1 : (lhs.equals(rhs) ? 0 : 1);
            }
        }));
        //noinspection ComparatorMethodParameterNotUsed
        assertIterableEquals(Arrays.asList(null, null), new Sort<>(Arrays.asList(null, null), new Comparator<Object>() {
            @Override
            public int compare(Object lhs, Object rhs) {
                return 0;
            }
        }));
        //noinspection ComparatorMethodParameterNotUsed
        assertIterableEquals(Collections.emptyList(), new Sort<>(Collections.emptyList(), new Comparator<Object>() {
            @Override
            public int compare(Object lhs, Object rhs) {
                return 0;
            }
        }));
    }
}
