package solid.converters;

import java.util.List;

import solid.collections.SolidList;
import solid.functions.SolidFunc1;

public class ToSolidList<T> implements SolidFunc1<Iterable<T>, SolidList<T>> {

    private static final ToSolidList TO_SOLID_LIST = new ToSolidList<>();

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(SolidFunc1)}
     * to convert a stream into a {@link SolidList}.
     *
     * @param <T> a type of {@link SolidList} items.
     * @return a method that converts an iterable into a {@link SolidList}.
     */
    public static <T> SolidFunc1<Iterable<T>, SolidList<T>> toSolidList() {
        //noinspection unchecked
        return TO_SOLID_LIST;
    }

    @Override
    public SolidList<T> call(Iterable<T> iterable) {
        return new SolidList<>(iterable);
    }
}
