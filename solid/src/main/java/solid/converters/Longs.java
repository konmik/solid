package solid.converters;

import java.util.Iterator;

import solid.stream.Stream;

public class Longs extends Stream<Long> {

    private long[] longs;

    public Longs(long[] longs) {
        this.longs = longs;
    }

    @Override
    public Iterator<Long> iterator() {
        return new Iterator<Long>() {

            int i;

            @Override
            public boolean hasNext() {
                return i < longs.length;
            }

            @Override
            public Long next() {
                return longs[i++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
