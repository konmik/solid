package solid.converters;

import java.util.Iterator;

import solid.stream.Stream;

public class Integers extends Stream<Integer> {

    private int[] integers;

    public Integers(int[] integers) {
        this.integers = integers;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {

            int i;

            @Override
            public boolean hasNext() {
                return i < integers.length;
            }

            @Override
            public Integer next() {
                return integers[i++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
