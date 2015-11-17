package solid.converters;

import org.junit.Test;

import solid.stream.Empty;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static solid.converters.ToFirst.toFist;

public class ToFirstTest {
    @Test
    public void testToFirst() throws Exception {
        assertEquals(1, (int)new ToFirst<>(10).call(singletonList(1)));
        assertEquals(1, (int)new ToFirst<>(10).call(asList(1, 2, 3)));
        assertEquals(10, (int)new ToFirst<>(10).call(new Empty<Integer>()));
        assertEquals(1, (int)toFist(10).call(asList(1, 2, 3)));
        assertEquals(10, (int)toFist(10).call(new Empty<Integer>()));
    }
}
