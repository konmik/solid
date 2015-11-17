package solid.stream;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import solid.converters.AccumulateTest;
import solid.converters.FoldTest;
import solid.converters.ReduceTest;
import solid.converters.ToFirstTest;
import solid.converters.ToLastTest;
import solid.converters.ToListTest;
import solid.filters.DistinctFilterTest;
import solid.functions.SolidFunc1;
import solid.functions.SolidFunc2;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static test_utils.AssertIterableEquals.assertIterableEquals;

public class StreamTest {

    @Test
    public void testStreamOfArray() throws Exception {
        assertIterableEquals(asList(1, 2, 3), Stream.stream(asList(1, 2, 3).toArray(new Integer[3])));
        assertIterableEquals(Collections.emptyList(), Stream.stream(new Object[]{}));
        assertIterableEquals(asList(null, null), Stream.stream(new Object[]{null, null}));
    }

    @Test
    public void testStreamOfIterator() throws Exception {
        assertIterableEquals(asList(1, 2, 3), Stream.stream(asList(1, 2, 3)));
        assertIterableEquals(Collections.emptyList(), Stream.stream(Collections.emptyList()));
        assertIterableEquals(asList(null, null), Stream.stream(asList(null, null)));
    }

    @Test
    public void testOfSingle() throws Exception {
        assertIterableEquals(singletonList(1), Stream.of(1));
    }

    @Test
    public void testOfVararg() throws Exception {
        assertIterableEquals(asList(1, null, 2), Stream.of(1, null, 2));
        assertIterableEquals(emptyList(), Stream.of());
    }

    @Test
    public void testToList() throws Exception {
        assertIterableEquals(asList(1, 2, 3), Stream.stream(asList(1, 2, 3)).toList());
        assertIterableEquals(asList(1, 2, 3), Stream.stream(asList(1, 2, 3)).toList(10));
        new ToListTest().testToList();
        new ToListTest().testNewAndCall();
    }

    @Test
    public void testLift() throws Exception {
        assertIterableEquals(asList(1, 3, 6), Stream.stream(asList(1, 2, 3))
            .compose(new SolidFunc1<Stream<Integer>, Stream<Integer>>() {
                @Override
                public Stream<Integer> call(final Stream<Integer> value) {
                    return new Stream<Integer>() {
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
                    };
                }
            }));
    }

    @Test
    public void testMap() throws Exception {
        assertIterableEquals(asList("1", "2", "3"), Stream.stream(asList(1, 2, 3)).map(new SolidFunc1<Integer, String>() {
            @Override
            public String call(Integer value) {
                return value.toString();
            }
        }));
        new MapTest().testIterator();
    }

    @Test
    public void testFlatMap() throws Exception {
        assertTrue(Stream.stream(asList(1, 2, 3)).flatMap(new SolidFunc1<Integer, Iterable<Object>>() {
            @Override
            public Iterable<Object> call(Integer value) {
                return null;
            }
        }) instanceof FlatMap);
        new FlatMapTest().testIterator();
    }

    @Test
    public void testFilter() throws Exception {
        assertIterableEquals(asList(1, 2, 3), Stream.stream(asList(1, 2, 3, 4)).filter(new SolidFunc1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer value) {
                return value != 4;
            }
        }));
        new FilterTest().testIterator();
    }

    @Test
    public void testWithout() throws Exception {
        assertIterableEquals(asList(1, 2, 3), Stream.stream(asList(1, 2, 3, 4)).without(4));
    }

    @Test
    public void testWith() throws Exception {
        assertIterableEquals(asList(1, 2, 3), Stream.stream(asList(1, 2)).with(3));
    }

    @Test
    public void testMerge() throws Exception {
        assertIterableEquals(asList(1, 2, 3, 4), Stream.stream(asList(1, 2)).merge(asList(3, 4)));
        new MergeTest().testIterator();
    }

    @Test
    public void testSeparate() throws Exception {
        assertIterableEquals(asList(1, 2, 3), Stream.stream(asList(0, 1, 4, 5, 6, 2, 3, null)).separate(asList(0, 4, 5, 6, null)));
    }

    @Test
    public void testTake() throws Exception {
        assertTrue(Stream.stream(asList(1, 2, 3)).take(2) instanceof Take);
        new TakeTest().testIterator();
    }

    @Test
    public void testSkip() throws Exception {
        assertTrue(Stream.stream(asList(1, 2, 3)).skip(2) instanceof Skip);
        new SkipTest().testIterator();
    }

    @Test
    public void testDistinct() throws Exception {
        assertIterableEquals(asList(1, 2, 3), Stream.stream(asList(1, 2, 3, 3, 3)).distinct());
        new DistinctFilterTest().testCall();
    }

    @Test
    public void testMergeDistinct() throws Exception {
        assertTrue(Stream.stream(asList(1, 2)).mergeDistinct(asList(3, 4)) instanceof MergeDistinct);
        new MergeDistinctTest().testIterator();
    }

    @Test
    public void testSort() throws Exception {
        assertIterableEquals(asList(1, 2, 3), Stream.stream(asList(3, 2, 1)).sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return lhs < rhs ? -1 : (lhs.equals(rhs) ? 0 : 1);
            }
        }));
        new SortTest().testIterator();
    }

    @Test
    public void testReverse() throws Exception {
        assertIterableEquals(asList(1, 2, 3), Stream.stream(asList(3, 2, 1)).reverse());
        new ReverseTest().testIterator();
    }

    @Test
    public void testCollect() throws Exception {
        final ArrayList<Integer> target = new ArrayList<>();
        ArrayList<Integer> result = Stream.stream(asList(1, 2, 3)).collect(new SolidFunc1<Iterable<Integer>, ArrayList<Integer>>() {
            @Override
            public ArrayList<Integer> call(Iterable<Integer> value) {
                for (Integer v : value)
                    target.add(v);
                return target;
            }
        });
        assertTrue(target == result);
        assertEquals(target, result);
    }

    @Test
    public void testFold() throws Exception {
        Assert.assertEquals(
            (Integer) 10,
            Stream
                .of(2, 3, 4)
                .fold(1, new SolidFunc2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer value1, Integer value2) {
                        return value1 + value2;
                    }
                }));
        new FoldTest().all();
    }

    @Test
    public void testReduce() throws Exception {
        Assert.assertEquals(
            (Integer) 9,
            Stream
                .of(2, 3, 4)
                .reduce(new SolidFunc2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer value1, Integer value2) {
                        return value1 + value2;
                    }
                }));
        new ReduceTest().all();
    }

    @Test
    public void testAccumulate() throws Exception {
        Assert.assertEquals(
            (Integer) 109,
            Stream
                .of(2, 3, 4)
                .accumulate(100, new SolidFunc2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer value1, Integer value2) {
                        return value1 + value2;
                    }
                }));
        new AccumulateTest().all();
    }

    @Test
    public void testFirst() throws Exception {
        assertEquals(1, (int) Stream.stream(asList(1, 2, 3)).first(10));
        new ToFirstTest().testToFirst();
    }

    @Test
    public void testLast() throws Exception {
        assertEquals(3, (int) Stream.stream(asList(1, 2, 3)).last(10));
        new ToLastTest().testToLast();
    }

    @Test
    public void testCast() throws Exception {
        assertTrue(Stream.stream(asList(1, 2, 3)).cast(Number.class) instanceof Cast);
        new CastTest().testIterator();
    }

    @Test(expected = ClassCastException.class)
    public void testCastException() throws Exception {
        assertTrue(Stream.stream(asList(1, 2, 3)).cast(Number.class) instanceof Cast);
        new CastTest().testIteratorException();
    }
}
