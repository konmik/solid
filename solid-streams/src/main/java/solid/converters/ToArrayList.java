package solid.converters;

import java.util.ArrayList;
import java.util.List;

import solid.functions.SolidFunc1;

public class ToArrayList<T> implements SolidFunc1<Iterable<? extends T>, ArrayList<T>> {

    private static final ToArrayList TO_ARRAY_LIST = new ToArrayList();

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(SolidFunc1)}
     * to convert a stream into a {@link ArrayList}.
     *
     * @param <T> a type of {@link ArrayList} items.
     * @return a method that converts an iterable into {@link ArrayList}.
     */
    public static <T> ToArrayList<T> toArrayList() {
        //noinspection unchecked
        return TO_ARRAY_LIST;
    }

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(SolidFunc1)}
     * to convert a stream into a {@link List}.
     * <p/>
     * Use this method instead of {@link #toArrayList()} for better performance on
     * streams that can have more than 12 items.
     *
     * @param <T>             a type of {@link List} items.
     * @param initialCapacity initial capacity of the list.
     * @return a method that converts an iterable into {@link List}.
     */
    public static <T> ToArrayList<T> toArrayList(int initialCapacity) {
        return new ToArrayList<>(initialCapacity);
    }

    private int initialCapacity;

    public ToArrayList() {
    }

    public ToArrayList(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    @Override
    public ArrayList<T> call(Iterable<? extends T> iterable) {
        ArrayList<T> list = new ArrayList<>(initialCapacity);
        for (T value : iterable)
            list.add(value);
        return list;
    }
}
