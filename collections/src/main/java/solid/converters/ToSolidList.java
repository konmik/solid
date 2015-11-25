package solid.converters;

import java.util.ArrayList;
import java.util.List;

import solid.collections.SolidList;
import solid.functions.Func1;

public class ToSolidList {

    private static final Func1 TO_SOLID_LIST = toSolidList(0);

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(Func1)}
     * to convert a stream into a {@link ArrayList}.
     *
     * @param <T> a type of {@link ArrayList} items.
     * @return a method that converts an iterable into {@link ArrayList}.
     */
    public static <T> Func1<Iterable<T>, SolidList<T>> toSolidList() {
        return TO_SOLID_LIST;
    }

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(Func1)}
     * to convert a stream into a {@link List}.
     * <p/>
     * Use this method instead of {@link #toSolidList()} for better performance on
     * streams that can have more than 12 items.
     *
     * @param <T>             a type of {@link List} items.
     * @param initialCapacity initial capacity of the list.
     * @return a method that converts an iterable into {@link List}.
     */
    public static <T> Func1<Iterable<T>, SolidList<T>> toSolidList(final int initialCapacity) {
        return new Func1<Iterable<T>, SolidList<T>>() {
            @Override
            public SolidList<T> call(Iterable<T> iterable) {
                ArrayList<T> list = new ArrayList<>(initialCapacity);
                for (T value : iterable)
                    list.add(value);
                return new SolidList<>(list);
            }
        };
    }
}
