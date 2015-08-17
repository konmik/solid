package solid.filters;

import solid.collections.SolidList;
import solid.functions.SolidFunc1;

public class NotIn<T> implements SolidFunc1<T, Boolean> {

    private SolidList<? extends T> from;

    public NotIn(Iterable<? extends T> from) {
        this.from = new SolidList<>(from);
    }

    @Override
    public Boolean call(T value) {
        return !from.contains(value);
    }
}
