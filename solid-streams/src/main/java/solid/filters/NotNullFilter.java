package solid.filters;

import solid.functions.SolidFunc1;

public class NotNullFilter<T> implements SolidFunc1<T, Boolean> {
    @Override
    public Boolean call(T value) {
        return value != null;
    }
}
