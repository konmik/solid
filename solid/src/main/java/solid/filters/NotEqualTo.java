package solid.filters;

import solid.functions.SolidFunc1;

public class NotEqualTo<T> implements SolidFunc1<T, Boolean> {

    private T value;

    public NotEqualTo(T value) {
        this.value = value;
    }

    @Override
    public Boolean call(T value2) {
        return value == null ? value2 != null : !value.equals(value2);
    }
}
