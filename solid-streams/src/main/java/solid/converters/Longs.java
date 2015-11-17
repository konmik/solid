package solid.converters;

import java.util.Iterator;

import solid.stream.ReadOnlyIterator;
import solid.stream.Stream;

public class Longs extends Stream<Long> {

    private long[] longs;

    /**
     * Creates a new stream of {@link Long} type that contains all items of a given array.
     *
     * @param longs an array to get items from.
     * @return a new stream of {@link Long} type that contains all items of a given array.
     */
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
