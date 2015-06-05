package solid.converters;

import java.util.Iterator;

import solid.stream.Stream;

public class Bytes extends Stream<Byte> {

    private byte[] bytes;

    public Bytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public Iterator<Byte> iterator() {
        return new Iterator<Byte>() {

            int i;

            @Override
            public boolean hasNext() {
                return i < bytes.length;
            }

            @Override
            public Byte next() {
                return bytes[i++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
