package solid.stream;

import java.util.Iterator;

import solid.functions.SolidFunc1;

public class FlatMap<T, R> extends Stream<R> {

    private Iterable<T> source;
    private SolidFunc1<T, Iterable<R>> func;

    public FlatMap(Iterable<T> source, SolidFunc1<T, Iterable<R>> func) {
        this.source = source;
        this.func = func;
    }

    @Override
    public Iterator<R> iterator() {
        return new ReadOnlyIterator<R>() {

            Iterator<T> iterator = source.iterator();
            Iterator<R> next;

            @Override
            public boolean hasNext() {
                if (next == null || !next.hasNext()) {
                    if (iterator.hasNext())
                        next = func.call(iterator.next()).iterator();
                }

                return next != null && next.hasNext();
            }

            @Override
            public R next() {
                return next.next();
            }
        };
    }
}
