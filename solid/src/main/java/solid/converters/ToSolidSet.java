package solid.converters;

import solid.collections.SolidSet;
import solid.functions.SolidFunc1;

public class ToSolidSet<T> implements SolidFunc1<Iterable<T>, SolidSet<T>> {

    private static final ToSolidSet TO_SOLID_SET = new ToSolidSet<>();

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(SolidFunc1)}
     * to convert a stream into {@link SolidSet}.
     *
     * @param <T> a type of {@link SolidSet} values.
     * @return a method that converts an iterable into {@link SolidSet}.
     */
    public static <T> SolidFunc1<Iterable<T>, SolidSet<T>> toSolidSet() {
        //noinspection unchecked
        return TO_SOLID_SET;
    }

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(SolidFunc1)}
     * to convert a stream into {@link SolidSet}.
     *
     * @param <T>             a type of {@link SolidSet} values.
     * @param initialCapacity initial capacity of the set.
     * @return a method that converts an iterable into {@link SolidSet}.
     */
    public static <T> SolidFunc1<Iterable<T>, SolidSet<T>> toSolidSet(int initialCapacity) {
        return new ToSolidSet<>(initialCapacity);
    }

    private int initialCapacity;

    public ToSolidSet() {
    }

    public ToSolidSet(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    @Override
    public SolidSet<T> call(Iterable<T> iterable) {
        return new SolidSet<>(iterable, initialCapacity);
    }
}
