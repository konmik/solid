package solid.converters;

import android.util.SparseArray;

import solid.functions.SolidFunc1;

public class ToSparseArray<T> implements SolidFunc1<Iterable<T>, SparseArray<T>> {

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(SolidFunc1)}
     * to convert a stream into a {@link SparseArray}.
     *
     * @param <T>       a type of stream items.
     * @param itemToKey a method that should return a key for an item.
     * @return a method that converts an iterable into a {@link SparseArray}.
     */
    public static <T> ToSparseArray<T> toSparseArray(SolidFunc1<T, Integer> itemToKey) {
        return new ToSparseArray<>(itemToKey);
    }

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(SolidFunc1)}
     * to convert a stream into a {@link SparseArray}.
     * <p/>
     * Use this method instead of {@link #toSparseArray(SolidFunc1)}} for better performance on
     * streams that can have more than 10 items.
     *
     * @param <T>             a type of stream items.
     * @param itemToKey       a method that should return a key for an item.
     * @param initialCapacity initial capacity on the sparse array.
     * @return a method that converts an iterable into a {@link SparseArray}.
     */
    public static <T> ToSparseArray<T> toSparseArray(SolidFunc1<T, Integer> itemToKey, int initialCapacity) {
        return new ToSparseArray<>(itemToKey, initialCapacity);
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
