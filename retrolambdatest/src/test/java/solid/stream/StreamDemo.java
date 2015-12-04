package solid.stream;

import org.junit.Test;

import java.util.ArrayList;

import solid.collections.Grouped;
import solid.collections.Indexed;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static solid.collectors.ToArrayList.toArrayList;
import static solid.collectors.ToArrays.toBytes;
import static solid.collectors.ToArrays.toDoubles;
import static solid.collectors.ToArrays.toFloats;
import static solid.collectors.ToArrays.toInts;
import static solid.collectors.ToArrays.toLongs;
import static solid.collectors.ToJoinedString.toJoinedString;
import static solid.collectors.ToList.toList;
import static solid.stream.Primitives.box;
import static solid.stream.Stream.of;
import static test_utils.AssertIterableEquals.assertGroupedEquals;
import static test_utils.AssertIterableEquals.assertIterableEquals;

public class StreamDemo {

    @Test
    public void construction() throws Exception {

        // Stream.of()
        // an empty stream

        assertIterableEquals(emptyList(),
            of());

        // Stream.of(value)
        // a stream of one value

        assertIterableEquals(singletonList(1),
            of(1));

        // Stream.of(values)
        // a stream of given values

        assertIterableEquals(asList(1, 2, 3),
            of(1, 2, 3));

        // Stream.stream(array)
        // a stream of array values

        assertIterableEquals(asList(1, 2, 3),
            Stream.stream(new Integer[]{1, 2, 3}));

        // Stream.stream(iterable)
        // a stream of iterable values

        assertIterableEquals(asList(1, 2, 3),
            Stream.stream(asList(1, 2, 3)));
    }

    @Test
    public void basic_operators() throws Exception {

        // Stream.first()
        // returns only the first value (as Optional)

        assertEquals((Integer) 1,
            of(1, 2, 3)
                .first()
                .get());

        // Stream.last()
        // returns only the last one value (as Optional)

        assertEquals((Integer) 3,
            of(1, 2, 3)
                .last()
                .get());

        // Stream.filter(condition)
        // skips all items that do not satisfy a given condition

        assertIterableEquals(asList(1, 2),
            of(1, 2, 3)
                .filter(it -> it < 3));

        // Stream.map
        // transforms each item

        assertIterableEquals(asList("1", "2", "3"),
            of(1, 2, 3)
                .map(it -> it.toString()));
    }

    @Test
    public void control_operators() throws Exception {

        // Stream.merge(item)
        // adds an item to the end

        assertIterableEquals(asList(1, 2, 3),
            of(1, 2)
                .merge(3));

        // Stream.merge(stream)
        // adds a stream to the end

        assertIterableEquals(asList(1, 2, 3),
            of(1)
                .merge(of(2, 3)));

        // Stream.separate(item)
        // removes an item

        assertIterableEquals(asList(1, 2, 3),
            of(1, 2, 3, 4)
                .separate(4));

        // Stream.separate(stream)
        // removes all items of a given stream

        assertIterableEquals(asList(1, 2, 3),
            of(1, 2, 3, 4, 5)
                .separate(of(4, 5)));

        // Stream.take(x)
        // takes only the first x items

        assertIterableEquals(asList(1, 2, 3),
            of(1, 2, 3, 4)
                .take(3));

        // Stream.skip(x)
        // skips first x items

        assertIterableEquals(asList(1, 2, 3),
            of(-1, 0, 1, 2, 3)
                .skip(2));

        // Stream.distinct()
        // skips duplicate items

        assertIterableEquals(asList(1, 2, 3),
            of(1, 1, 2, 3, 3)
                .distinct());

        // Stream.reverse()
        // reverses item order

        assertIterableEquals(asList(1, 2, 3),
            of(3, 2, 1)
                .reverse());

        // Stream.sort(comparator)
        // sorts items

        assertIterableEquals(asList(1, 2, 3),
            of(2, 1, 3)
                .sort(Integer::compareTo));
    }

    @Test
    public void advanced_operators() throws Exception {

        // Stream.reduce(accumulator)
        // accumulates values using an accumulating function

        assertEquals((Integer) 6,
            of(1, 2, 3)
                .reduce((x, y) -> x + y)
                .get());

        // Stream.reduce(initial value, accumulator)
        // accumulates values using an initial value and an accumulating function

        assertEquals((Integer) 16,
            of(1, 2, 3)
                .reduce(10, (x, y) -> x + y));

        // Stream.flatMap(stream generator)
        // replaces each item with set of items

        assertIterableEquals(asList(100, 1, 200, 2, 300, 3),
            of(1, 2, 3)
                .flatMap(it -> of(it * 100, it)));

        // Stream.groupBy(group selector)
        // groups items using a group selector

        assertGroupedEquals(asList(new Grouped<>(0, of(1, 3)), new Grouped<>(10, of(12, 13))),
            of(1, 12, 3, 13)
                .groupBy(value -> value - value % 10));

        // Stream.groupBy(group selector, value selector)
        // groups items using a group and a value selectors

        assertGroupedEquals(asList(new Grouped<>(0, of(1, 3)), new Grouped<>(10, of(2, 3))),
            of(1, 12, 3, 13)
                .groupBy(value -> value - value % 10, value -> value % 10));
    }

    @Test
    public void utility_operators() throws Exception {

        // Stream.index()
        // Indexes each value

        assertIterableEquals(asList(new Indexed<>(0, "1"), new Indexed<>(1, "2"), new Indexed<>(2, "3")),
            of("1", "2", "3")
                .index());
    }

    @Test
    public void side_effects() throws Exception {
        ArrayList<Integer> out = new ArrayList<>();
        ArrayList<Integer> out2 = new ArrayList<>();

        // Stream.forEach
        // feeds items to a consuming function

        of(1, 2, 3)
            .forEach(it -> out.add(it));
        assertIterableEquals(asList(1, 2, 3), out);

        // Stream.onNext
        // feeds items to a consuming functions during lazy evaluation

        of(1, 2, 3, 4, 5)
            .onNext(it -> out2.add(it))
            .take(3)
            .last();
        assertIterableEquals(asList(1, 2, 3), out2);
    }

    @Test
    public void collectors() throws Exception {

        // Stream.collect(custom collector)
        // transforms into another data structure

        assertEquals(asList(1, 2, 3), of(1, 2, 3).collect(it -> {
            ArrayList<Integer> list = new ArrayList<>();
            for (int i : it)
                list.add(i);
            return list;
        }));

        // toList()
        // collects into List

        assertEquals(asList(1, 2, 3),
            of(1, 2, 3)
                .collect(toList()));

        // toArrayList()
        // collects into ArrayList

        assertEquals(asList(1, 2, 3),
            of(1, 2, 3)
                .collect(toArrayList()));

        // toJoinedString()
        // joins strings
        assertEquals("123",
            of("1", "2", "3")
                .collect(toJoinedString()));

        // toJoinedString(delimiter)
        // joins strings with a delimiter

        assertEquals("1, 2, 3",
            of("1", "2", "3")
                .collect(toJoinedString(", ")));
    }

    @Test
    public void primitive_arrays() throws Exception {

        // Primitives.box(primitive array)
        // boxes primitive array items

        assertIterableEquals(asList(1, 2, 3),
            box(new int[]{1, 2, 3}));

        assertIterableEquals(asList(1L, 2L, 3L),
            box(new long[]{1, 2, 3}));

        assertIterableEquals(asList(1f, 2f, 3f),
            box(new float[]{1, 2, 3}));

        assertIterableEquals(asList(new Byte[]{1, 2, 3}),
            box(new byte[]{1, 2, 3}));

        assertIterableEquals(asList(1., 2., 3.),
            box(new double[]{1, 2, 3}));

        // ToArrays.toInts, toLongs, toFloats, toBytes, toDoubles
        // unboxes a primitive array

        assertArrayEquals(new int[]{1, 2, 3},
            of(1, 2, 3)
                .collect(toInts()));

        assertArrayEquals(new long[]{1L, 2L, 3L},
            of(1L, 2L, 3L)
                .collect(toLongs()));

        assertArrayEquals(new float[]{1, 2, 3},
            of(1f, 2f, 3f)
                .collect(toFloats()), 0);

        assertArrayEquals(new byte[]{1, 2, 3},
            of(new Byte[]{1, 2, 3})
                .collect(toBytes()));

        assertArrayEquals(new double[]{1, 2, 3},
            of(1., 2., 3.)
                .collect(toDoubles()),
            0);
    }
}
