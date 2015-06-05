package solid.stream;

import java.util.Iterator;

public class Single<T> extends Stream<T> {

    private T value;

    public Single(T value) {
        this.value = value;
    }

    @Override
    public Iterator<T> iterator() {
        return new ReadOnlyIterator<T>() {
            boolean hasNext = true;

            @Override
            public boolean hasNext() {
                return hasNext;
            }

            @Override
            public T next() {
                hasNext = false;
                return value;
            }
        };
    }
}
