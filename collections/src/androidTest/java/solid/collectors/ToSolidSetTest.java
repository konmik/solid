package solid.collectors;

import org.junit.Test;

import java.util.Collections;

import solid.stream.Stream;

import static java.util.Arrays.asList;
import static solid.stream.Stream.of;
import static testkit.AssertIterableEquals.assertIterableEquals;

public class ToSolidSetTest {
    @Test
    public void testToSolidSet() throws Exception {
        assertIterableEquals(asList(1, 2, 3), of(1, 2, 3).collect(ToSolidSet.<Integer>toSolidSet()));
        assertIterableEquals(Collections.<Integer>emptyList(), Stream.<Integer>of().collect(ToSolidSet.<Integer>toSolidSet()));
    }
}
