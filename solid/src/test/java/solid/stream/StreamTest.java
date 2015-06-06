package solid.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import solid.filters.DistinctFilterTest;
import solid.functions.SolidFunc1;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static test_utils.AssertIterableEquals.assertIterableEquals;

public class StreamTest {

    @Test
    public void testStream() throws Exception {
        assertIterableEquals(Arrays.asList(1, 2, 3), Stream.stream(Arrays.asList(1, 2, 3).toArray(new Integer[3])));
        assertIterableEquals(Collections.emptyList(), Stream.stream(new Object[]{}));
        assertIterableEquals(Arrays.asList(null, null), Stream.stream(new Object[]{null, null}));
    }

    @Test
    public void testStream1() throws Exception {
        assertIterableEquals(Arrays.asList(1, 2, 3), Stream.stream(Arrays.asList(1, 2, 3)));
        assertIterableEquals(Collections.emptyList(), Stream.stream(Collections.emptyList()));
        assertIterableEquals(Arrays.asList(null, null), Stream.stream(Arrays.asList(null, null)));
    }

    @Test
    public void testToList() throws Exception {
        assertIterableEquals(Arrays.asList(1, 2, 3), Stream.stream(Arrays.asList(1, 2, 3)).toList());
        assertIterableEquals(Collections.emptyList(), Stream.stream(Collections.emptyList()).toList());
        assertIterableEquals(Arrays.asList(null, null), Stream.stream(Arrays.asList(null, null)).toList());
    }

    @Test
    public void testToSolidList() throws Exception {
        assertIterableEquals(Arrays.asList(1, 2, 3), Stream.stream(Arrays.asList(1, 2, 3)).toSolidList());
        assertIterableEquals(Collections.emptyList(), Stream.stream(Collections.emptyList()).toSolidList());
        assertIterableEquals(Arrays.asList(null, null), Stream.stream(Arrays.asList(null, null)).toSolidList());
    }

    @Test
    public void testLift() throws Exception {
        assertIterableEquals(Arrays.asList(1, 3, 6), Stream.stream(Arrays.asList(1, 2, 3))
            .lift(new SolidFunc1<Stream<Integer>, Stream<Integer>>() {
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
        assertIterableEquals(Arrays.asList("1", "2", "3"), Stream.stream(Arrays.asList(1, 2, 3)).map(new SolidFunc1<Integer, String>() {
            @Override
            public String call(Integer value) {
                return value.toString();
            }
        }));
        new MapTest().testIterator();
    }

    @Test
    public void testFlatMap() throws Exception {
        assertTrue(Stream.stream(Arrays.asList(1, 2, 3)).flatMap(new SolidFunc1<Integer, Iterable<Object>>() {
            @Override
            public Iterable<Object> call(Integer value) {
                return null;
            }
        }) instanceof FlatMap);
        new FlatMapTest().testIterator();
    }

    @Test
    public void testFilter() throws Exception {
        assertIterableEquals(Arrays.asList(1, 2, 3), Stream.stream(Arrays.asList(1, 2, 3, 4)).filter(new SolidFunc1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer value) {
                return value != 4;
            }
        }));
        new FilterTest().testIterator();
    }

    @Test
    public void testWithout() throws Exception {
        assertIterableEquals(Arrays.asList(1, 2, 3), Stream.stream(Arrays.asList(1, 2, 3, 4)).without(4));
    }

    @Test
    public void testWith() throws Exception {
        assertIterableEquals(Arrays.asList(1, 2, 3), Stream.stream(Arrays.asList(1, 2)).with(3));
    }

    @Test
    public void testMerge() throws Exception {
        assertIterableEquals(Arrays.asList(1, 2, 3, 4), Stream.stream(Arrays.asList(1, 2)).merge(Arrays.asList(3, 4)));
        new MergeTest().testIterator();
    }

    @Test
    public void testTake() throws Exception {
        assertTrue(Stream.stream(Arrays.asList(1, 2, 3)).take(2) instanceof Take);
        new TakeTest().testIterator();
    }

    @Test
    public void testDistinct() throws Exception {
        assertIterableEquals(Arrays.asList(1, 2, 3), Stream.stream(Arrays.asList(1, 2, 3, 3, 3)).distinct());
        new DistinctFilterTest().testCall();
    }

    @Test
    public void testMergeDistinct() throws Exception {
        assertIterableEquals(Arrays.asList(1, 2, 3, 4, 5, 6), Stream.stream(Arrays.asList(1, 2, 3, 3, 3)).mergeDistinct(Arrays.asList(1, 4, 5, 6)));
        new DistinctFilterTest().testCall();
    }

    @Test
    public void testSort() throws Exception {
        assertIterableEquals(Arrays.asList(1, 2, 3), Stream.stream(Arrays.asList(3, 2, 1)).sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return lhs < rhs ? -1 : (lhs.equals(rhs) ? 0 : 1);
            }
        }));
        new SortTest().testIterator();
    }

    @Test
    public void testReverse() throws Exception {
        assertIterableEquals(Arrays.asList(1, 2, 3), Stream.stream(Arrays.asList(3, 2, 1)).reverse());
        new ReverseTest().testIterator();
    }

    @Test
    public void testCollect() throws Exception {
        final ArrayList<Integer> target = new ArrayList<>();
        ArrayList<Integer> result = Stream.stream(Arrays.asList(1, 2, 3)).collect(new SolidFunc1<Iterable<Integer>, ArrayList<Integer>>() {
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
}
