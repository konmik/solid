package solid.filters;

import java.util.ArrayList;

import solid.functions.SolidFunc1;

public class NotIn<T> implements SolidFunc1<T, Boolean> {

    private ArrayList<T> from;

    public NotIn(Iterable<? extends T> from) {
        this.from = new ArrayList<>();
        for (T t : from)
            this.from.add(t);
    }

    @Override
    public Boolean call(T value) {
        return !from.contains(value);
    }
}
