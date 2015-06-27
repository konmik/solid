package solid.converters;

import java.util.ArrayList;
import java.util.List;

import solid.functions.SolidFunc1;

public class ToList<T> implements SolidFunc1<Iterable<T>, List<T>> {

    private static final ToList TO_LIST = new ToList();

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(SolidFunc1)}
     * to convert a stream into a {@link List}.
     *
     * @param <T> a type of {@link List} items.
     * @return a method that converts an iterable into {@link List}.
     */
    public static <T> ToList<T> toList() {
        //noinspection unchecked
        return TO_LIST;
    }

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(SolidFunc1)}
     * to convert a stream into a {@link List}.
     * <p/>
     * Use this method instead of {@link #toList()} for better performance on
     * streams that can have more than 12 items.
     *
     * @param <T>             a type of {@link List} items.
     * @param initialCapacity initial capacity of the list.
     * @return a method that converts an iterable into {@link List}.
     */
    public static <T> ToList<T> toList(int initialCapacity) {
        return new ToList<>(initialCapacity);
    }

    private int initialCapacity;

    public ToList() {
    }

    public ToList(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    @Override
    public List<T> call(Iterable<T> iterable) {
        ArrayList<T> list = new ArrayList<>(initialCapacity);
        for (T value : iterable)
            list.add(value);
        return list;
    }
}
