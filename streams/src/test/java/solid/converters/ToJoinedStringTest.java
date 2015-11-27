package solid.converters;

import org.junit.Test;

import solid.stream.Stream;

import static org.junit.Assert.assertEquals;
import static solid.collectors.ToJoinedString.toJoinedString;
import static solid.stream.Stream.of;

public class ToJoinedStringTest {

    @Test
    public void testToJoinedString() throws Exception {
        assertEquals("1,2,3", of("1", "2", "3").collect(toJoinedString(",")));
        assertEquals("123", of("1", "2", "3").collect(toJoinedString()));
        assertEquals("", Stream.<String>of().collect(toJoinedString()));
    }
}
