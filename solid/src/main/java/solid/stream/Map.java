package solid.stream;

import java.util.Iterator;

import solid.functions.SolidFunc1;

public class Map<T, R> extends Stream<R> {

    private Iterable<T> source;
    private SolidFunc1<T, R> func;

    public Map(Iterable<T> source, SolidFunc1<T, R> func) {
        this.source = source;
        this.func = func;
    }

    @Override
    public Iterator<R> iterator() {
        return new ReadOnlyIterator<R>() {
            Iterator<T> iterator = source.iterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public R next() {
                return func.call(iterator.next());
            }
        };
    }
}
