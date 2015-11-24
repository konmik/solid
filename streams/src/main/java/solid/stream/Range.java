package solid.stream;

import java.util.Iterator;

import solid.functions.Func0;
import solid.stream.ReadOnlyIterator;
import solid.stream.Stream;

public class Range {

    /**
     * Creates a stream that contains a given number of integers starting from a given number.
     *
     * @param from a staring value
     * @param to   an ending value, exclusive
     * @param step the value to add to for the next item
     * @return a stream that contains a given number of integers starting from a given number.
     */
    public static Stream<Integer> range(final int from, final int to, final int step) {
        return Stream.from(new Func0<Iterator<Integer>>() {
            @Override
            public Iterator<Integer> call() {
                return new ReadOnlyIterator<Integer>() {

                    int value = from;

                    @Override
                    public boolean hasNext() {
                        return value < to;
                    }

                    @Override
                    public Integer next() {
                        int v = value;
                        value += step;
                        return v;
                    }
                };
            }
        });
    }

    /**
     * {@link #range(int, int, int)} with 1 as the step parameter.
     */
    public static Stream<Integer> range(final int from, final int to) {
        return range(from, to, 1);
    }
}
