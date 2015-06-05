package solid.stream;

import java.util.Iterator;

public class Copy<T> extends Stream<T> {

    private Iterable<T> iterable;

    public Copy(Iterable<T> iterable) {
        this.iterable = iterable;
    }

    @Override
    public Iterator<T> iterator() {
        return new ReadOnlyIterator<T>() {

            Iterator<T> iterator = iterable.iterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public T next() {
                return iterator.next();
            }
        };
    }
}
