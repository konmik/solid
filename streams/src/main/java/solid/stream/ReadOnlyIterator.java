package solid.stream;

import java.util.Iterator;

abstract class ReadOnlyIterator<T> implements Iterator<T> {
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
