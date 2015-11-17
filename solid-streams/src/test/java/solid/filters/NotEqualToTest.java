package solid.filters;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NotEqualToTest {
    @Test
    public void testCall() throws Exception {
        assertTrue(new NotEqualTo<>(1).call(2));
        assertTrue(new NotEqualTo<>(null).call(2));
        assertTrue(new NotEqualTo<>(1).call(null));
        assertFalse(new NotEqualTo<>(1).call(1));
        assertFalse(new NotEqualTo<>(null).call(null));
    }
}
