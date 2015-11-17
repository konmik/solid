package solid.stream;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import solid.converters.AccumulateTest;
import solid.converters.FoldTest;
import solid.converters.ReduceTest;
import solid.converters.ToFirstTest;
import solid.converters.ToLastTest;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static solid.stream.Stream.of;
import static solid.stream.Stream.stream;
import static test_utils.AssertIterableEquals.assertIterableEquals;

public class StreamTest {

    @Test
    public void testStreamOfArray() throws Exception {
        assertIterableEquals(asList(1, 2, 3), stream(asList(1, 2, 3).toArray(new Integer[3])));
        assertIterableEquals(Collections.emptyList(), stream(new Object[]{}));
        assertIterableEquals(asList(null, null), stream(new Object[]{null, null}));
    }

    @Test
    public void testStreamOfIterator() throws Exception {
        assertIterableEquals(asList(1, 2, 3), stream(asList(1, 2, 3)));
        assertIterableEquals(Collections.emptyList(), stream(Collections.emptyList()));
        assertIterableEquals(asList(null, null), stream(asList(null, null)));
    }

    @Test
    public void testOf() throws Exception {
        assertIterableEquals(singletonList(1), of(1));
        assertIterableEquals(asList(null, null), of(null, null));
        assertIterableEquals(asList(1, 2, 3), of(1, 2, 3));
    }

    @Test
    public void testOfVararg() throws Exception {
        assertIterableEquals(asList(1, null, 2), of(1, null, 2));
        assertIterableEquals(emptyList(), of());
    }

    @Test
    public void testEmpty() throws Exception {
        assertIterableEquals(emptyList(), Stream.of());
        Iterator<Object> iterator = Stream.of().iterator();
        assertFalse(iterator.hasNext());
    }

    @Test(expected = IllegalStateException.class)
    public void testEmptyThrows() throws Exception {
        Stream.of().iterator().next();
    }

    @Test
    public void testLift() throws Exception {
        assertIterableEquals(asList(1, 3, 6), stream(asList(1, 2, 3))
            .compose(value -> new Stream<Integer>() {
                @Override
                public Iterator<Integer> iterator() {
                    return new ReadOnlyIterator<Integer>() {

                        Iterator<Integer> source = value.iterator();
                        int count;

                        @Override
                        public boolean hasNext() {
                            return source.hasNext();
                        }

                        @Override
                        public Integer next() {
                            return count += source.next();
                        }
                    };
                }
            }));
    }

    @Test
    public void testMap() throws Exception {
        assertIterableEquals(asList("1", "2", "3"), of(1, 2, 3).map(Object::toString));
        assertIterableEquals(Arrays.<Integer>asList(null, null), of(null, null).map(value -> null));
        assertIterableEquals(emptyList(), of().map(value -> null));
    }

    @Test
    public void testFlatMap() throws Exception {
        assertIterableEquals(asList("2", "3", "4", "3", "4", "5", "4", "5", "6"), of(1, 2, 3).flatMap(value -> asList("" + (value + 1), "" + (value + 2), "" + (value + 3))));
        assertIterableEquals(Arrays.<Integer>asList(null, null, null, null), of(null, null).flatMap(value -> Arrays.asList(null, null)));
        assertIterableEquals(emptyList(), of().flatMap(value -> null));
    }

    @Test
    public void testFilter() throws Exception {
        assertIterableEquals(asList(1, 2, 3), stream(asList(1, 2, 3, 4)).filter(value -> value != 4));
        List<Integer> list123 = asList(1, 2, 3);
        assertIterableEquals(list123, of(1, 2, 3, 4).filter(value -> value != 4));
        assertIterableEquals(list123, of(4, 1, 2, 3).filter(value -> value != 4));
        assertIterableEquals(list123, of(1, 2, 4, 3).filter(value -> value != 4));
        assertIterableEquals(list123, of(1, 2, 3).filter(value -> true));
        assertIterableEquals(Collections.<Integer>emptyList(), of(1, 2, 4, 3).filter(value -> false));
        assertIterableEquals(Collections.<Integer>emptyList(), Stream.<Integer>of().filter(value -> true));
        assertIterableEquals(Collections.<Integer>emptyList(), Stream.<Integer>of(null, null).filter(value -> false));
    }

    @Test
    public void testWithout() throws Exception {
        assertIterableEquals(asList(1, 2, 3), stream(asList(1, 2, 3, 4)).without(4));
        assertIterableEquals(asList(1, 2, 3), stream(asList(1, 2, 3, null)).without(null));
    }

    @Test
    public void testWith() throws Exception {
        assertIterableEquals(asList(1, 2, 3), stream(asList(1, 2)).with(3));
    }

    @Test
    public void testMerge() throws Exception {
        assertIterableEquals(asList(1, 2, 3, 4), stream(asList(1, 2)).merge(asList(3, 4)));
        new MergeTest().testIterator();
    }

    @Test
    public void testSeparate() throws Exception {
        assertIterableEquals(asList(1, 2, 3), stream(asList(0, 1, 4, 5, 6, 2, 3, null)).separate(asList(0, 4, 5, 6, null)));
    }

    @Test
    public void testTake() throws Exception {
        assertTrue(stream(asList(1, 2, 3)).take(2) instanceof Take);
        new TakeTest().testIterator();
    }

    @Test
    public void testSkip() throws Exception {
        assertTrue(stream(asList(1, 2, 3)).skip(2) instanceof Skip);
        new SkipTest().testIterator();
    }

    @Test
    public void testDistinct() throws Exception {
        assertIterableEquals(asList(1, 2, 3), stream(asList(1, 2, 3, 3, 3)).distinct());
        assertIterableEquals(singletonList(null), stream(asList(null, null)).distinct());
        assertIterableEquals(emptyList(), stream(asList()).distinct());
    }

    @Test
    public void testSort() throws Exception {
        assertIterableEquals(asList(1, 2, 3), stream(asList(3, 2, 1)).sorted((lhs, rhs) -> lhs < rhs ? -1 : (lhs.equals(rhs) ? 0 : 1)));
        assertIterableEquals(asList(1, 2, 3), of(3, 2, 1).sorted((lhs, rhs) -> lhs < rhs ? -1 : (lhs.equals(rhs) ? 0 : 1)));
        assertIterableEquals(asList(null, null), of(null, null).sorted((lhs, rhs) -> 0));
        assertIterableEquals(emptyList(), of().sorted((lhs, rhs) -> 0));
    }

    @Test
    public void testReverse() throws Exception {
        assertIterableEquals(asList(1, 2, 3), of(3, 2, 1).reverse());
        assertIterableEquals(asList(null, null), of(null, null).reverse());
        assertIterableEquals(singletonList(1), of(1));
        assertIterableEquals(emptyList(), of().reverse());
    }

    @Test
    public void testCollect() throws Exception {
        final ArrayList<Integer> target = new ArrayList<>();
        ArrayList<Integer> result = stream(asList(1, 2, 3)).collect(value -> {
            for (Integer v : value)
                target.add(v);
            return target;
        });
        assertTrue(target == result);
        assertEquals(target, result);
    }

    @Test
    public void testFold() throws Exception {
        Assert.assertEquals(
            (Integer) 10,
            of(2, 3, 4)
                .fold(1, (value1, value2) -> value1 + value2));
        new FoldTest().all();
    }

    @Test
    public void testReduce() throws Exception {
        Assert.assertEquals(
            (Integer) 9,
            of(2, 3, 4)
                .reduce((value1, value2) -> value1 + value2));
        new ReduceTest().all();
    }

    @Test
    public void testAccumulate() throws Exception {
        Assert.assertEquals(
            (Integer) 109,
            of(2, 3, 4)
                .accumulate(100, (value1, value2) -> value1 + value2));
        new AccumulateTest().all();
    }

    @Test
    public void testFirst() throws Exception {
        assertEquals(1, (int) stream(asList(1, 2, 3)).first(10));
        new ToFirstTest().testToFirst();
    }

    @Test
    public void testLast() throws Exception {
        assertEquals(3, (int) stream(asList(1, 2, 3)).last(10));
        new ToLastTest().testToLast();
    }

    @Test
    public void testCast() throws Exception {
        List<Integer> list = asList(1, 2, 3);
        List<Number> numbers = asList((Number) 1, 2, 3);
        assertIterableEquals(numbers, stream(list).cast(Number.class));
    }

    @Test(expected = ClassCastException.class)
    public void testCastException() throws Exception {
        List<Integer> numbers = asList(1, 2, 3);
        //noinspection unchecked
        assertIterableEquals(numbers, of("1").cast(Integer.class));
    }
}
