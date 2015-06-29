package solid.stream;

import java.util.Iterator;

public class Empty<T> extends Stream<T> {

    public static <T> Empty<T> empty() {
        //noinspection unchecked
        return EMPTY;
    }

    private static final Empty EMPTY = new Empty();

    private static Iterator iterator = new ReadOnlyIterator() {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Object next() {
            throw new UnsupportedOperationException();
        }
    };

    @Override
    public Iterator<T> iterator() {
        //noinspection unchecked
        return iterator;
    }
}
