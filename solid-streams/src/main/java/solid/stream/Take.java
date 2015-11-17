package solid.stream;

import java.util.Iterator;

public class Take<T> extends Stream<T> {

    private Iterable<T> iterable;
    private int count;

    public Take(Iterable<T> iterable, int count) {
        this.iterable = iterable;
        this.count = count;
    }

    @Override
    public Iterator<T> iterator() {
        return new ReadOnlyIterator<T>() {

            Iterator<T> iterator = iterable.iterator();
            int left = count;

            @Override
            public boolean hasNext() {
                return left > 0 && iterator.hasNext();
            }

            @Override
            public T next() {
                left--;
                return iterator.next();
            }
        };
    }
}
