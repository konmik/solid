package solid.converters;

import solid.collections.SolidList;
import solid.functions.SolidFunc1;

public class ToSolidList<T> implements SolidFunc1<Iterable<T>, SolidList<T>> {

    private static final ToSolidList TO_SOLID_LIST = new ToSolidList<>();

    public static <T> SolidFunc1<Iterable<T>, SolidList<T>> toSolidList() {
        //noinspection unchecked
        return TO_SOLID_LIST;
    }

    @Override
    public SolidList<T> call(Iterable<T> iterable) {
        return new SolidList<>(iterable);
    }
}
