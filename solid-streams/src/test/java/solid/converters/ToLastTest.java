package solid.converters;

import org.junit.Test;

import solid.stream.Empty;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static solid.converters.ToLast.toLast;

public class ToLastTest {
    @Test
    public void testToLast() throws Exception {
        assertEquals(1, (int)new ToLast<>(10).call(singletonList(1)));
        assertEquals(3, (int)new ToLast<>(10).call(asList(1, 2, 3)));
        assertEquals(10, (int)new ToLast<>(10).call(new Empty<Integer>()));
        assertEquals(3, (int)toLast(10).call(asList(1, 2, 3)));
        assertEquals(10, (int)toLast(10).call(new Empty<Integer>()));
    }
}
