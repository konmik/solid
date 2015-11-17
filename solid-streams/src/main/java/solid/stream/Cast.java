package solid.stream;

import java.util.Iterator;

/**
 * Represents a stream that contains all values of the original stream that has been casted to a given class type.
 * @param <T> a type of the stream items.
 */
public class Cast<T> extends Stream<T> {

    private Iterable iterable;
    private Class<T> cls;

    public Cast(Iterable iterable, Class<T> cls) {
        this.iterable = iterable;
        this.cls = cls;
    }

    @Override
    public Iterator<T> iterator() {
        return new ReadOnlyIterator<T>() {

            Iterator iterator = iterable.iterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public T next() {
                return cls.cast(iterator.next());
            }
        };
    }
}
