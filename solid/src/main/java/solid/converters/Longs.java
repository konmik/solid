package solid.converters;

import java.util.Iterator;

import solid.stream.Stream;

public class Longs extends Stream<Long> {

    private long[] longs;

    public static Stream<Long> longs(long[] longs) {
        return new Longs(longs);
    }

    public Longs(long[] longs) {
        this.longs = longs;
    }

    @Override
    public Iterator<Long> iterator() {
        return new ReadOnlyIterator<Long>() {

            int i;

            @Override
            public boolean hasNext() {
                return i < longs.length;
            }

            @Override
            public Long next() {
                return longs[i++];
            }
        };
    }
}
