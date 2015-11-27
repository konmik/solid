package test_utils;

import java.util.Iterator;

import solid.collections.Grouped;

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

    public static <T extends Grouped> void assertGroupedEquals(Iterable<T> iterable1, Iterable<T> iterable2) {
        Iterator<T> iterator1 = iterable1.iterator();
        Iterator<T> iterator2 = iterable2.iterator();
        while (iterator1.hasNext() && iterator2.hasNext()) {
            T next1 = iterator1.next();
            T next2 = iterator2.next();
            assertEquals(next1.group, next2.group);
            assertIterableEquals(next1.stream, next2.stream);
        }
        assertFalse(iterator1.hasNext());
        assertFalse(iterator2.hasNext());
    }
}
