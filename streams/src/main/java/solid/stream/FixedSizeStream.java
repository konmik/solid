package solid.stream;

import java.util.Iterator;

import solid.functions.Func1;

class FixedSizeStream<T> extends Stream<T> {

    private final int length;
    private final Func1<Integer, T> get;

    public FixedSizeStream(int length, Func1<Integer, T> get) {
        this.length = length;
        this.get = get;
    }

    @Override
    public Iterator<T> iterator() {
        return new ReadOnlyIterator<T>() {

            int index;

            @Override
            public boolean hasNext() {
                return index < length;
            }

            @Override
            public T next() {
                return get.call(index++);
            }
        };
    }
}
