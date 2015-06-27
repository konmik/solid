package solid.converters;

import android.util.SparseArray;

import solid.functions.SolidFunc1;

public class ToSparseArray<T> implements SolidFunc1<Iterable<T>, SparseArray<T>> {

    public static <T> ToSparseArray<T> toSparseArray(SolidFunc1<T, Integer> itemToKey, int initialCapacity) {
        return new ToSparseArray<>(itemToKey, initialCapacity);
    }

    public static <T> ToSparseArray<T> toSparseArray(SolidFunc1<T, Integer> itemToKey) {
        return new ToSparseArray<>(itemToKey);
    }

    private SolidFunc1<T, Integer> getItemKey;
    private int initialCapacity;

    public ToSparseArray(SolidFunc1<T, Integer> itemToKey) {
        this(itemToKey, 10);
    }

    public ToSparseArray(SolidFunc1<T, Integer> itemToKey, int initialCapacity) {
        this.getItemKey = itemToKey;
        this.initialCapacity = initialCapacity;
    }

    @Override
    public SparseArray<T> call(Iterable<T> iterable) {
        SparseArray<T> array = new SparseArray<>(initialCapacity);
        for (T value : iterable)
            array.put(getItemKey.call(value), value);
        return array;
    }
}
