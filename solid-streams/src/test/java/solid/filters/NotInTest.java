package solid.filters;

import org.junit.Test;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NotInTest {
    @Test
    public void testCall() throws Exception {
        NotIn<Integer> notIn = new NotIn<>(asList(1, 2, 3));
        assertTrue(notIn.call(4));
        assertTrue(notIn.call(null));

        notIn = new NotIn<>(Collections.singletonList((Integer)null));
        assertTrue(notIn.call(4));
        assertFalse(notIn.call(null));
    }
}