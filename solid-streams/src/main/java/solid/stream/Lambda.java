package solid.stream;

import java.util.Iterator;

import solid.functions.SolidFunc0;

public class Lambda<T> extends Stream<T> {

    private final SolidFunc0<Iterator<T>> factory;

    public Lambda(SolidFunc0<Iterator<T>> factory) {
        this.factory = factory;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private final Iterator<T> call = factory.call();

            @Override
            public boolean hasNext() {
                return call.hasNext();
            }

            @Override
            public T next() {
                return call.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
