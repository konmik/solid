package solid.stream;

import java.util.Iterator;

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
        return new Stream<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                return new ReadOnlyIterator<Integer>() {

                    int value = from;

                    @Override
                    public boolean hasNext() {
                        return value < to;
                    }

                    @Override
                    public Integer next() {
                        final int v = value;
                        value += step;
                        return v;
                    }
                };
            }
        };
    }

    /**
     * {@link #range(int, int, int)} with 1 as the step parameter.
     */
    public static Stream<Integer> range(final int from, final int to) {
        return range(from, to, 1);
    }
}
