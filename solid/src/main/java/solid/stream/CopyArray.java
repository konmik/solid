package solid.stream;

import java.util.Iterator;

public class CopyArray<T> extends Stream<T> {

    private T[] array;

    public CopyArray(T[] array) {
        this.array = array;
    }

    @Override
    public Iterator<T> iterator() {
        return new ReadOnlyIterator<T>() {

            int length = array.length;
            int index;

            @Override
            public boolean hasNext() {
                return index < length;
            }

            @Override
            public T next() {
                return array[index++];
            }
        };
    }
}
