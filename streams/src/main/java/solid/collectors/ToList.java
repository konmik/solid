package solid.collectors;

import java.util.List;

import solid.functions.Func1;

import static solid.collectors.ToArrayList.toArrayList;

public class ToList {

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(Func1)}
     * to convert a stream into a {@link List}.
     *
     * @param <T> a type of {@link List} items.
     * @return a method that converts an iterable into {@link List}.
     */
    public static <T> Func1<Iterable<T>, List<T>> toList() {
        return (Func1) toArrayList();
    }

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(Func1)}
     * to convert a stream into a {@link List}.
     * <p/>
     * Use this method instead of {@link #toList()} for better performance on
     * streams that can have more than 12 items.
     *
     * @param <T>             a type of {@link List} items.
     * @param initialCapacity initial capacity of the list.
     * @return a method that converts an iterable into {@link List}.
     */
    public static <T> Func1<Iterable<T>, List<T>> toList(int initialCapacity) {
        return (Func1) toArrayList(initialCapacity);
    }
}
