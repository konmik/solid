package solid.filters;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class DistinctFilterTest {
    @Test
    public void testCall() throws Exception {
        assertTrue(new DistinctFilter<>().call(1));
        assertTrue(new DistinctFilter<>().call(null));
        DistinctFilter<Object> filter = new DistinctFilter<>();
        assertTrue(filter.call(null));
        assertFalse(filter.call(null));
        assertTrue(filter.call(1));
        assertTrue(filter.call(2));
        assertTrue(filter.call(3));
        assertFalse(filter.call(1));
    }
}
