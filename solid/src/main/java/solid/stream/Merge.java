package solid.stream;

import java.util.Iterator;

public class Merge<T> extends Stream<T> {

    private Iterable<T> source;
    private Iterable<T> values;

    public Merge(Iterable<T> source, Iterable<T> values) {
        this.source = source;
        this.values = values;
    }

    @Override
    public Iterator<T> iterator() {
        return new ReadOnlyIterator<T>() {
            Iterator<T> sourceI = source.iterator();
            Iterator<T> valuesI = values.iterator();

            @Override
            public boolean hasNext() {
                return sourceI.hasNext() || valuesI.hasNext();
            }

            @Override
            public T next() {
                return sourceI.hasNext() ? sourceI.next() : valuesI.next();
            }
        };
    }
}
