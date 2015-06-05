package solid.experimental.algorithms;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import solid.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static test_utils.AssertIterableEquals.assertIterableEquals;

public class ListUpdateTest {
    @Test
    public void testIterator() throws Exception {
        //noinspection unchecked
        StreamComparison<Integer> comparison = mock(StreamComparison.class);
        when(comparison.both()).thenReturn(Stream.stream(Arrays.asList(1, 2)));
        when(comparison.first()).thenReturn(Stream.stream(Arrays.asList(9, 9)));
        when(comparison.second()).thenReturn(Stream.stream(Arrays.asList(3, 4)));
        assertIterableEquals(Arrays.asList(1, 2, 3, 4), new ListUpdate<>(comparison));
    }

    @Test
    public void testNulls() throws Exception {
        //noinspection unchecked
        StreamComparison<Integer> comparison = mock(StreamComparison.class);
        when(comparison.both()).thenReturn(Stream.stream(Arrays.<Integer>asList(null, null)));
        when(comparison.first()).thenReturn(Stream.stream(Arrays.asList(9, 9)));
        when(comparison.second()).thenReturn(Stream.stream(Arrays.<Integer>asList(null, null)));
        assertIterableEquals(Arrays.<Integer>asList(null, null, null, null), new ListUpdate<>(comparison));
    }

    @Test
    public void testEmpty() throws Exception {
        //noinspection unchecked
        StreamComparison<Integer> comparison = mock(StreamComparison.class);
        when(comparison.both()).thenReturn(Stream.stream(Collections.<Integer>emptyList()));
        when(comparison.first()).thenReturn(Stream.stream(Collections.<Integer>emptyList()));
        when(comparison.second()).thenReturn(Stream.stream(Collections.<Integer>emptyList()));
        assertIterableEquals(Stream.stream(Collections.<Integer>emptyList()), new ListUpdate<>(comparison));
    }
}
