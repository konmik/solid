package solid.optional;

import solid.functions.SolidAction1;
import solid.functions.SolidFunc0;

public class Optional<T> {

    private static final Optional<?> EMPTY = new Optional<>(null);

    private final T value;

    private Optional(T value) {
        this.value = value;
    }

    public static <T> Optional<T> empty() {
        return (Optional<T>) EMPTY;
    }

    public static <T> Optional<T> of(T value) {
        return value == null ? (Optional<T>) EMPTY : new Optional<>(value);
    }

    public T get() {
        if (value == null)
            throw new NullPointerException();
        return value;
    }

    public boolean isPresent() {
        return value != null;
    }

    public void ifPresent(SolidAction1<T> action) {
        if (value != null)
            action.call(value);
    }

    public T or(T value) {
        return this.value != null ? this.value : value;
    }

    public T or(SolidFunc0<T> func1) {
        return value != null ? value : func1.call();
    }

    public T orNull() {
        return value;
    }

    public <W extends Throwable> T orThrow(SolidFunc0<? extends W> throwableFactory) throws W {
        if (value != null)
            return value;
        throw throwableFactory.call();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Optional<?> optional = (Optional<?>) o;

        return !(value != null ? !value.equals(optional.value) : optional.value != null);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Optional{" +
            "value=" + value +
            '}';
    }
}
