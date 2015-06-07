package solid.stream;

import java.util.Iterator;

public class Merge<T> extends Stream<T> {

    private Iterable<T> source;
    private Iterable<T> with;

    public Merge(Iterable<T> source, Iterable<T> with) {
        this.source = source;
        this.with = with;
    }

    @Override
    public Iterator<T> iterator() {
        return new ReadOnlyIterator<T>() {
            Iterator<T> sourceI = source.iterator();
            Iterator<T> withI = with.iterator();

            @Override
            public boolean hasNext() {
                return sourceI.hasNext() || withI.hasNext();
            }

            @Override
            public T next() {
                return sourceI.hasNext() ? sourceI.next() : withI.next();
            }
        };
    }
}
