package solid.converters;

import java.util.ArrayList;
import java.util.Collection;
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

    @Override
    public List<T> call(Iterable<T> iterable) {
        ArrayList<T> list = new ArrayList<>(iterable instanceof Collection ? ((Collection)iterable).size() : 0);
        for (T value : iterable)
            list.add(value);
        return list;
    }
}
