package solid.stream;

import org.junit.Test;

import java.util.Collections;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static test_utils.AssertIterableEquals.assertIterableEquals;

public class MergeDistinctTest {
    @Test
    public void testIterator() throws Exception {
        assertIterableEquals(asList(1, 2, 3, 4), new MergeDistinct<>(asList(1, 2, 3), asList(3, 4, 1)));
        assertIterableEquals(asList(1, 2, 3), new MergeDistinct<>(asList(1, 2, 3), Collections.<Integer>emptyList()));
        assertIterableEquals(asList(4, 5, 6), new MergeDistinct<>(Collections.<Integer>emptyList(), asList(4, 5, 6)));
        assertIterableEquals(emptyList(), new MergeDistinct<>(emptyList(), emptyList()));
        assertIterableEquals(singletonList(null), new MergeDistinct<>(asList(null, null), asList(null, null)));
    }
}
