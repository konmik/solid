package solid.stream;

import java.util.Iterator;

public class Empty<T> extends Stream<T> {

    @Override
    public Iterator<T> iterator() {
        return new ReadOnlyIterator<T>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public T next() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
