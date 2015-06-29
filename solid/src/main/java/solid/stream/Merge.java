package solid.stream;

import java.util.Iterator;

public class Merge<T> extends Stream<T> {

    private Iterable<? extends T> source;
    private Iterable<? extends T> with;

    public Merge(Iterable<? extends T> source, Iterable<? extends T> with) {
        this.source = source;
        this.with = with;
    }

    @Override
    public Iterator<T> iterator() {
        return new ReadOnlyIterator<T>() {

            Iterator<? extends T> sourceI = source.iterator();
            Iterator<? extends T> withI = with.iterator();

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
