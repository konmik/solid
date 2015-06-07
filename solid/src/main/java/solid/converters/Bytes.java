package solid.converters;

import java.util.Iterator;

import solid.stream.ReadOnlyIterator;
import solid.stream.Stream;

public class Bytes extends Stream<Byte> {

    private byte[] bytes;

    /**
     * Creates a new stream of {@link Byte} type that contains all items of a given array.
     *
     * @param bytes an array to get items from.
     * @return a new stream of {@link Byte} type that contains all items of a given array.
     */
    public static Stream<Byte> bytes(byte[] bytes) {
        return new Bytes(bytes);
    }

    public Bytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public Iterator<Byte> iterator() {
        return new ReadOnlyIterator<Byte>() {

            int i;

            @Override
            public boolean hasNext() {
                return i < bytes.length;
            }

            @Override
            public Byte next() {
                return bytes[i++];
            }
        };
    }
}
