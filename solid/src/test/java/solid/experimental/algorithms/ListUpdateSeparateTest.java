package solid.experimental.algorithms;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import solid.collections.Pair;
import solid.functions.SolidAction2;
import solid.functions.SolidFunc1;
import solid.stream.Stream;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static test_utils.AssertIterableEquals.assertIterableEquals;

public class ListUpdateSeparateTest {
    @Test
    public void testIterator() throws Exception {
        //noinspection unchecked
        StreamComparisonSeparate<Integer, String> comparison = mock(StreamComparisonSeparate.class);
        when(comparison.both()).thenReturn(Stream.stream(Arrays.asList(new Pair<>(1, "1"), new Pair<>(2, "2"))));
        when(comparison.first()).thenReturn(Stream.stream(Arrays.asList(9, 9)));
        when(comparison.second()).thenReturn(Stream.stream(Arrays.asList("3", "4")));
        assertIterableEquals(Arrays.asList(1, 2, 3, 4), createListUpdateConvert(comparison));
    }

    @Test
    public void testNulls() throws Exception {
        //noinspection unchecked
        StreamComparisonSeparate<Integer, String> comparison = mock(StreamComparisonSeparate.class);
        when(comparison.both()).thenReturn(Stream.stream(Arrays.asList(new Pair<Integer, String>(null, null), new Pair<Integer, String>(null, null))));
        when(comparison.first()).thenReturn(Stream.stream(Arrays.asList(9, 9)));
        when(comparison.second()).thenReturn(Stream.stream(Arrays.<String>asList(null, null)));
        assertIterableEquals(Arrays.<Integer>asList(null, null, null, null), createListUpdateConvert(comparison));
    }

    @Test
    public void testEmpty() throws Exception {
        //noinspection unchecked
        StreamComparisonSeparate<Integer, String> comparison = mock(StreamComparisonSeparate.class);
        when(comparison.both()).thenReturn(Stream.stream(Collections.<Pair<Integer, String>>emptyList()));
        when(comparison.first()).thenReturn(Stream.stream(Collections.<Integer>emptyList()));
        when(comparison.second()).thenReturn(Stream.stream(Collections.<String>emptyList()));
        assertIterableEquals(Stream.stream(Collections.<Integer>emptyList()), createListUpdateConvert(comparison));
    }

    private ListUpdateConvert<Integer, String> createListUpdateConvert(StreamComparisonSeparate<Integer, String> comparison) {
        return new ListUpdateConvert<>(comparison,
            new SolidFunc1<String, Integer>() {
                @Override
                public Integer call(String value) {
                    return value == null ? null : Integer.parseInt(value);
                }
            },
            new SolidAction2<Integer, String>() {
                @Override
                public void call(Integer value1, String value2) {
                    assertEquals(value1 == null ? null : value1.toString(), value2);
                }
            });
    }
}
