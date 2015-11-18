package solid.converters;

import android.util.SparseArray;

import solid.functions.Func1;

public class ToSparseArray {

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(Func1)}
     * to convert a stream into a {@link SparseArray}.
     *
     * @param <T>       a type of stream items.
     * @param itemToKey a method that should return a key for an item.
     * @return a method that converts an iterable into a {@link SparseArray}.
     */
    public static <T> Func1<Iterable<T>, SparseArray<T>> toSparseArray(Func1<T, Integer> itemToKey) {
        return toSparseArray(itemToKey, 10);
    }

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(Func1)}
     * to convert a stream into a {@link SparseArray}.
     * <p/>
     * Use this method instead of {@link #toSparseArray(Func1)}} for better performance on
     * streams that can have more than 10 items.
     *
     * @param <T>             a type of stream items.
     * @param itemToKey       a method that should return a key for an item.
     * @param initialCapacity initial capacity on the sparse array.
     * @return a method that converts an iterable into a {@link SparseArray}.
     */
    public static <T> Func1<Iterable<T>, SparseArray<T>> toSparseArray(Func1<T, Integer> itemToKey, int initialCapacity) {
        return iterable -> {
            SparseArray<T> array = new SparseArray<>(initialCapacity);
            for (T value : iterable)
                array.put(itemToKey.call(value), value);
            return array;
        };
    }
}
