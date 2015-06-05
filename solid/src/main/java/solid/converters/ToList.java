package solid.converters;

import java.util.ArrayList;
import java.util.List;

import solid.functions.SolidFunc1;

public class ToList<T> implements SolidFunc1<Iterable<T>, List<T>> {

    private static final ToList TO_LIST = new ToList();

    public static <T> ToList<T> toList() {
        //noinspection unchecked
        return TO_LIST;
    }

    @Override
    public List<T> call(Iterable<T> iterable) {
        ArrayList<T> list = new ArrayList<>();
        for (T value : iterable)
            list.add(value);
        return list;
    }
}
