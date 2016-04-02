package solid.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import solid.collections.Grouped;
import solid.collections.Indexed;
import solid.functions.Action1;
import solid.functions.Func1;
import solid.functions.Func2;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static solid.stream.Stream.of;
import static solid.stream.Stream.stream;
import static test_utils.AssertIterableEquals.assertGroupedEquals;
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

    @Test(expected = UnsupportedOperationException.class)
    public void testEmptyThrows() throws Exception {
        Stream.of().iterator().next();
    }

    @Test
    public void testLift() throws Exception {
        assertIterableEquals(asList(1, 3, 6), stream(asList(1, 2, 3))
            .compose(new Func1<Stream<Integer>, Stream<Integer>>() {
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
        assertIterableEquals(asList("1", "2", "3"), of(1, 2, 3).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {return integer.toString();}
        }));
        assertIterableEquals(Arrays.<Integer>asList(null, null), of(null, null).map(new Func1<Object, Integer>() {
            @Override
            public Integer call(Object value) {return null;}
        }));
        assertIterableEquals(emptyList(), of().map(new Func1<Object, Object>() {
            @Override
            public Object call(Object value) {return null;}
        }));
    }

    @Test
    public void testFlatMap() throws Exception {
        assertIterableEquals(asList("2", "3", "4", "3", "4", "5", "4", "5", "6"), of(1, 2, 3).flatMap(new Func1<Integer, Iterable<String>>() {
            @Override
            public Iterable<String> call(Integer value) {return asList("" + (value + 1), "" + (value + 2), "" + (value + 3));}
        }));
        assertIterableEquals(Arrays.asList(null, null, null, null), of(null, null).flatMap(new Func1<Object, Iterable<Object>>() {
            @Override
            public Iterable<Object> call(Object value) {return Arrays.asList(null, null);}
        }));
        assertIterableEquals(emptyList(), of().flatMap(new Func1<Object, Iterable<Object>>() {
            @Override
            public Iterable<Object> call(Object value) {return null;}
        }));

        HashMap<Integer, List<String>> map = new HashMap<Integer, List<String>>() {{
            put(0, asList("1", "2"));
            put(1, Collections.<String>emptyList());
            put(2, Collections.<String>emptyList());
            put(3, Collections.<String>emptyList());
            put(4, asList("3"));
        }};
        assertIterableEquals(asList("1", "2", "3"), stream(map.entrySet()).flatMap(new Func1<Map.Entry<Integer, List<String>>, Iterable<String>>() {
            @Override
            public Iterable<String> call(Map.Entry<Integer, List<String>> value) {
                return value.getValue();
            }
        }));

        HashMap<Integer, List<String>> map2 = new HashMap<Integer, List<String>>() {{
            put(1, Collections.<String>emptyList());
            put(2, Collections.<String>emptyList());
            put(3, Collections.<String>emptyList());
            put(0, asList("1", "2"));
            put(4, asList("3"));
        }};
        assertIterableEquals(asList("1", "2", "3"), stream(map2.entrySet()).flatMap(new Func1<Map.Entry<Integer, List<String>>, Iterable<String>>() {
            @Override
            public Iterable<String> call(Map.Entry<Integer, List<String>> value) {
                return value.getValue();
            }
        }));
    }

    @Test
    public void testFilter() throws Exception {
        assertIterableEquals(asList(1, 2, 3), stream(asList(1, 2, 3, 4)).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer value) {return value != 4;}
        }));
        List<Integer> list123 = asList(1, 2, 3);
        assertIterableEquals(list123, of(1, 2, 3, 4).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer value) {return value != 4;}
        }));
        assertIterableEquals(list123, of(4, 1, 2, 3).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer value) {return value != 4;}
        }));
        assertIterableEquals(list123, of(1, 2, 4, 3).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer value) {return value != 4;}
        }));
        assertIterableEquals(list123, of(1, 2, 3).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer value) {return true;}
        }));
        assertIterableEquals(Collections.<Integer>emptyList(), of(1, 2, 4, 3).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer value) {return false;}
        }));
        assertIterableEquals(Collections.<Integer>emptyList(), Stream.<Integer>of().filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer value) {return true;}
        }));
        assertIterableEquals(Collections.<Integer>emptyList(), Stream.<Integer>of(null, null).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer value) {return false;}
        }));
    }

    @Test
    public void testWith() throws Exception {
        assertIterableEquals(asList(1, 2, 3), stream(asList(1, 2)).merge(3));
        assertIterableEquals(asList(1, 2, null), stream(asList(1, 2)).merge((Integer) null));
    }

    @Test
    public void testWithout() throws Exception {
        assertIterableEquals(asList(1, 2, 3), stream(asList(1, 2, 3, 4)).separate(4));
        assertIterableEquals(asList(1, 2, 3), stream(asList(1, 2, 3, null)).separate((Integer) null));
    }

    @Test
    public void testMerge() throws Exception {
        assertIterableEquals(asList(1, 2, 3, 4), of(1, 2).merge(of(3, 4)));
        assertIterableEquals(asList(1, 2, 3, 4, 5, 6), of(1, 2, 3).merge(of(4, 5, 6)));
        assertIterableEquals(asList(1, 2, 3), of(1, 2, 3).merge(Stream.<Integer>of()));
        assertIterableEquals(asList(4, 5, 6), Stream.<Integer>of().merge(Stream.of(4, 5, 6)));
        assertIterableEquals(Collections.emptyList(), of().merge(of()));
        assertIterableEquals(asList(null, null, null, null), of(null, null).merge(of(null, null)));
    }

    @Test
    public void testSeparate() throws Exception {
        assertIterableEquals(asList(1, 2, 3), stream(asList(0, 1, 4, 5, 6, 2, 3, null)).separate(asList(0, 4, 5, 6, null)));
    }

    @Test
    public void testTake() throws Exception {
        assertIterableEquals(asList(1, 2), of(1, 2, 3).take(2));
        assertIterableEquals(asList(1, 2), of(1, 2).take(10));
        assertIterableEquals(asList(null, null), of(null, null).take(3));
        assertIterableEquals(emptyList(), of().take(10));
        assertIterableEquals(emptyList(), of().take(0));
    }

    @Test
    public void testSkip() throws Exception {
        assertIterableEquals(asList(3, 4, 5), of(1, 2, 3, 4, 5).skip(2));
        assertIterableEquals(asList(3, 4, 5), of(3, 4, 5).skip(0));
        assertIterableEquals(Collections.<Integer>emptyList(), of(1, 2, 3, 4, 5).skip(5));
        assertIterableEquals(Collections.<Integer>emptyList(), of(1, 2, 3).skip(5));
        assertIterableEquals(Collections.emptyList(), of().skip(5));
        assertIterableEquals(asList(null, null), of(null, null, null).skip(1));
    }

    @Test
    public void testDistinct() throws Exception {
        assertIterableEquals(asList(1, 2, 3), stream(asList(1, 2, 3, 3, 3)).distinct());
        assertIterableEquals(singletonList(null), stream(asList(null, null)).distinct());
        assertIterableEquals(emptyList(), stream(asList()).distinct());
    }

    @Test
    public void testSort() throws Exception {
        assertIterableEquals(asList(1, 2, 3), stream(asList(3, 2, 1)).sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {return lhs < rhs ? -1 : (lhs.equals(rhs) ? 0 : 1);}
        }));
        assertIterableEquals(asList(1, 2, 3), of(3, 2, 1).sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {return lhs < rhs ? -1 : (lhs.equals(rhs) ? 0 : 1);}
        }));
        assertIterableEquals(asList(null, null), of(null, null).sort(new Comparator<Object>() {
            @Override
            public int compare(Object lhs, Object rhs) {return 0;}
        }));
        assertIterableEquals(emptyList(), of().sort(new Comparator<Object>() {
            @Override
            public int compare(Object lhs, Object rhs) {return 0;}
        }));
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
        ArrayList<Integer> result = stream(asList(1, 2, 3)).collect(new Func1<Iterable<Integer>, ArrayList<Integer>>() {
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
        assertEquals(null, Stream.of().reduce(null, new Func2<Object, Object, Object>() {
            @Override
            public Object call(Object it, Object that) {return null;}
        }));
        assertEquals(null, Stream.of(null, null).reduce(null, new Func2<Object, Object, Object>() {
            @Override
            public Object call(Object value1, Object value2) {return null;}
        }));
        assertEquals((Integer) 10, Stream.of(2, 3, 4).reduce(1, new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer value1, Integer value2) {return value1 + value2;}
        }));
    }

    @Test
    public void testReduce() throws Exception {

        assertFalse(Stream.of().reduce(null).isPresent());

        assertEquals((Integer) 9, Stream.of(9).reduce(null).get());
        assertFalse(null, Stream.of(null, null, null).reduce(new Func2<Object, Object, Object>() {
            @Override
            public Object call(Object value1, Object value2) {return null;}
        }).isPresent());
        assertEquals((Integer) 9, Stream.of(2, 3, 4).reduce(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer value1, Integer value2) {return value1 + value2;}
        }).get());
    }

    @Test
    public void testFirst() throws Exception {
        assertEquals((Long) 1L, of(1L).first().get());
        assertEquals((Long) 1L, of(1L, 2L, 3L).first().get());
        assertFalse(of().first().isPresent());
        assertFalse(of(null, 2L, 3L).first().isPresent());
    }

    @Test
    public void testLast() throws Exception {
        assertEquals(1, (int) of(1).last().get());
        assertEquals(3, (int) of(1, 2, 3).last().get());
        assertFalse(of().last().isPresent());
        assertEquals(3, (int) of(1, null, 3).last().get());
        assertFalse(of(1, 1, null).last().isPresent());
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

    @Test
    public void testGroupBy2() {
        assertGroupedEquals(of(new Grouped<>(0, of(1, 3)), new Grouped<>(10, of(2, 3))),
            of(1, 12, 3, 13)
                .groupBy(
                    new Func1<Integer, Integer>() {
                        @Override
                        public Integer call(Integer value) {
                            return value - value % 10;
                        }
                    },
                    new Func1<Integer, Integer>() {
                        @Override
                        public Integer call(Integer value) {
                            return value % 10;
                        }
                    }));
        assertGroupedEquals(Stream.<Grouped<Object, Object>>of(), Stream.of().groupBy(null, null));
        assertGroupedEquals(Stream.of(new Grouped<>(null, of((Object) null))), Stream.of((Object) null).groupBy(new Func1<Object, Object>() {
            @Override
            public Object call(Object value) {
                return null;
            }
        }, new Func1<Object, Object>() {
            @Override
            public Object call(Object value) {
                return null;
            }
        }));
    }

    @Test
    public void testGroupBy1() throws Exception {
        assertGroupedEquals(of(new Grouped<>(0, of(1, 3)), new Grouped<>(10, of(12, 13))),
            of(1, 12, 3, 13)
                .groupBy(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer value) {
                        return value - value % 10;
                    }
                }));
    }

    @Test
    public void testIndex() throws Exception {
        assertIterableEquals(of(new Indexed<>(0, 1), new Indexed<>(1, 12), new Indexed<>(2, 3), new Indexed<>(3, 13)),
            of(1, 12, 3, 13).index());
        assertIterableEquals(of(new Indexed<>(0, 1), new Indexed<Integer>(1, null)),
            of(1, null).index());
        assertIterableEquals(Stream.<Indexed<Object>>of(), of().index());
    }

    @Test
    public void testForEach() throws Exception {

        Stream<Integer> stream = of(1, 12, 3, 13);
        final ArrayList<Integer> collected = new ArrayList<>();
        stream.forEach(new Action1<Integer>() {
            @Override
            public void call(Integer value) {
                collected.add(value);
            }
        });
        assertIterableEquals(stream, collected);

        final AtomicBoolean called = new AtomicBoolean();
        of().forEach(new Action1<Object>() {
            @Override
            public void call(Object value) {
                called.set(true);
            }
        });
        assertFalse(called.get());

        final ArrayList<Integer> collected2 = new ArrayList<>();
        Stream<Integer> stream2 = of(null, 3);
        stream2.forEach(new Action1<Integer>() {
            @Override
            public void call(Integer value) {
                collected2.add(value);
            }
        });
        assertIterableEquals(stream2, collected2);
    }

    @Test
    public void testOnNext() throws Exception {
        final ArrayList<Integer> collected = new ArrayList<>();
        of(1, 2, 3)
            .filter(new Func1<Integer, Boolean>() {
                @Override
                public Boolean call(Integer value) {
                    return value > 1;
                }
            })
            .onNext(new Action1<Integer>() {
                @Override
                public void call(Integer value) {
                    collected.add(value);
                }
            })
            .filter(new Func1<Integer, Boolean>() {
                @Override
                public Boolean call(Integer value) {
                    return value < 3;
                }
            })
            .last();
        assertIterableEquals(of(2, 3), collected);
    }
}
