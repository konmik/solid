package solid.filters;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NotNullFilterTest {
    @Test
    public void testCall() throws Exception {
        assertTrue(new NotNullFilter<>().call(1));
        assertFalse(new NotNullFilter<>().call(null));
    }
}
