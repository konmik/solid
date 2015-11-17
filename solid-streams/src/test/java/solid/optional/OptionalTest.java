package solid.optional;

import org.junit.Test;

import solid.functions.SolidAction1;
import solid.functions.SolidFunc0;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class OptionalTest {

    @Test
    public void testNull() throws Exception {
        Optional<Object> o = Optional.of(null);

        assertFalse(o.isPresent());

        SolidAction1 mockAction = mock(SolidAction1.class);
        o.ifPresent(mockAction);
        verifyNoMoreInteractions(mockAction);

        assertEquals(1, o.or(1));
        assertEquals(1, o.or(() -> 1));
        assertNull(o.orNull());

        assertTrue(o.equals(Optional.empty()));

        assertEquals(o.hashCode(), Optional.empty().hashCode());
    }

    @Test(expected = NullPointerException.class)
    public void testNullNPE() throws Exception {
        Optional.of(null).get();
    }

    @Test(expected = RuntimeException.class)
    public void testNullOrThrow() throws Exception {
        Optional<Object> o = Optional.of(null);

        o.orThrow(() -> new RuntimeException());
    }

    @Test
    public void testValue() throws Exception {
        Optional<Object> o = Optional.<Object>of(1);

        assertTrue(o.isPresent());

        SolidAction1<Object> mockAction = mock(SolidAction1.class);
        o.ifPresent(mockAction);
        verify(mockAction, times(1)).call(eq(1));

        assertEquals(1, o.or(2));
        assertEquals(1, o.or(() -> 2));
        assertEquals(1, o.orNull());

        assertTrue(Optional.of(1).equals(o));

        assertEquals(o.hashCode(), Optional.of(1).hashCode());

        assertEquals(1, o.get());
        assertEquals(1, o.orThrow(() -> new RuntimeException()));
    }
}