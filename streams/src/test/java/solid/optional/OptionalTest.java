package solid.optional;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import solid.functions.Action1;
import solid.functions.Func0;
import solid.functions.Func1;
import solid.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static test_utils.AssertIterableEquals.assertIterableEquals;

public class OptionalTest {

    @Test
    public void testNull() throws Exception {
        Optional<Object> o = Optional.of(null);

        assertFalse(o.isPresent());

        Action1 mockAction = mock(Action1.class);
        o.ifPresent(mockAction);
        verifyNoMoreInteractions(mockAction);

        assertEquals(1, o.or(1));
        assertEquals(1, o.or(new Func0<Object>() {
            @Override
            public Object call() {
                return 1;
            }
        }));
        assertNull(o.orNull());
        assertNull(o.map(null).orNull());

        assertTrue(o.equals(Optional.empty()));

        assertEquals(o.hashCode(), Optional.empty().hashCode());
    }

    @Test(expected = NullPointerException.class)
    public void testNullNPE() throws Exception {
        Optional.of(null).get();
    }

    @Test
    public void testValue() throws Exception {
        Optional<Integer> o = Optional.of(1);

        assertTrue(o.isPresent());

        Action1<Integer> mockAction = mock(Action1.class);
        o.ifPresent(mockAction);
        verify(mockAction, times(1)).call(eq(1));

        assertEquals((Integer) 1, o.or(2));
        assertEquals((Integer) 1, o.or(new Func0<Integer>() {
            @Override
            public Integer call() {
                return 2;
            }
        }));
        assertEquals((Integer) 1, o.orNull());
        assertEquals("1", o.map(new Func1<Integer, String>() {
            @Override
            public String call(Integer value) {
                return value.toString();
            }
        }).get());

        assertTrue(Optional.of(1).equals(o));

        assertEquals(o.hashCode(), Optional.of(1).hashCode());

        assertEquals((Integer) 1, o.get());
    }

    @Test
    public void testStream() throws Exception {
        Optional<List<Integer>> o = Optional.of(Arrays.asList(1, 2, 3, 4));
        assertNotNull(o.stream());
        assertTrue(o.stream().first().isPresent());

        Stream<Integer> s = o.stream().flatMap(new Func1<List<Integer>, Iterable<Integer>>() {
            @Override
            public Iterable<Integer> call(List<Integer> value) {
                return value;
            }
        });
        assertEquals(Integer.valueOf(1), s.first().get());
        assertEquals(Integer.valueOf(4), s.last().get());

        assertEquals(Optional.empty().stream(), Stream.of());

        assertIterableEquals(s, Arrays.asList(1, 2, 3, 4));

        assertEquals(Optional.of("Test").stream().first().get(), "Test");
    }
}