package solid.collectors;

import java.util.ArrayList;
import java.util.List;

import solid.collections.SolidSet;
import solid.functions.Func1;

public class ToSolidSet {

    private static final Func1 TO_SOLID_SET = toSolidSet(0);

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(Func1)}
     * to convert a stream into a {@link ArrayList}.
     *
     * @param <T> a type of {@link ArrayList} items.
     * @return a method that converts an iterable into {@link ArrayList}.
     */
    public static <T> Func1<Iterable<T>, SolidSet<T>> toSolidSet() {
        return TO_SOLID_SET;
    }

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(Func1)}
     * to convert a stream into a {@link List}.
     * <p/>
     * Use this method instead of {@link #toSolidSet()} for better performance on
     * streams that can have more than 12 items.
     *
     * @param <T>             a type of {@link List} items.
     * @param initialCapacity initial capacity of the list.
     * @return a method that converts an iterable into {@link List}.
     */
    public static <T> Func1<Iterable<T>, SolidSet<T>> toSolidSet(final int initialCapacity) {
        return new Func1<Iterable<T>, SolidSet<T>>() {
            @Override
            public SolidSet<T> call(Iterable<T> iterable) {
                return new SolidSet<>(iterable, initialCapacity);
            }
        };
    }
}
