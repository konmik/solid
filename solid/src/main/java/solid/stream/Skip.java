package solid.stream;

import java.util.Iterator;

public class Skip<T> extends Stream<T> {

    private Iterable<T> iterable;
    private int count;

    public Skip(Iterable<T> iterable, int count) {
        this.iterable = iterable;
        this.count = count;
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<T> iterator = iterable.iterator();
        for (int skip = count; skip > 0 && iterator.hasNext(); skip--)
            iterator.next();
        return iterator;
    }
}
