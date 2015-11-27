package testkit;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class AssertIterableEquals {
    public static <T> void assertIterableEquals(Iterable<T> iterable1, Iterable<T> iterable2) {
        Iterator<T> iterator1 = iterable1.iterator();
        Iterator<T> iterator2 = iterable2.iterator();
        while (iterator1.hasNext() && iterator2.hasNext())
            assertEquals(iterator1.next(), iterator2.next());
        assertFalse(iterator1.hasNext());
        assertFalse(iterator2.hasNext());
    }
}
