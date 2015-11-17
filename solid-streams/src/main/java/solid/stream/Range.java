package solid.stream;

import java.util.Iterator;

public class Range extends Stream<Integer> {

    private int from;
    private int count;

    /**
     * Creates a stream that contains a given number of integers starting from a given number.
     *
     * @param from  a staring value
     * @param count a number of values
     * @return a stream that contains a given number of integers starting from a given number.
     */
    public static Stream<Integer> range(int from, int count) {
        return new Range(from, count);
    }

    public Range(int from, int count) {
        this.from = from;
        this.count = count;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new ReadOnlyIterator<Integer>() {

            int value = from;
            int to = from + count;

            @Override
            public boolean hasNext() {
                return value < to;
            }

            @Override
            public Integer next() {
                return value++;
            }
        };
    }
}
