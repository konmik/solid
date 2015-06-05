package solid.stream;

import java.util.Iterator;

import solid.functions.SolidFunc1;

public class Filter<T> extends Stream<T> {

    private Iterable<T> source;
    private SolidFunc1<T, Boolean> func;

    public Filter(Iterable<T> source, SolidFunc1<T, Boolean> func) {
        this.source = source;
        this.func = func;
    }

    @Override
    public Iterator<T> iterator() {
        return new ReadOnlyIterator<T>() {
            Iterator<T> iterator = source.iterator();
            T next;
            boolean hasNext;

            private void process() {
                while (!hasNext && iterator.hasNext()) {
                    T n = iterator.next();
                    if (func.call(n)) {
                        next = n;
                        hasNext = true;
                    }
                }
            }

            @Override
            public boolean hasNext() {
                process();
                return hasNext;
            }

            @Override
            public T next() {
                process();
                hasNext = false;
                return next;
            }
        };
    }
}
