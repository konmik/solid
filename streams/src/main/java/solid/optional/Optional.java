package solid.optional;

import solid.functions.Action1;
import solid.functions.Func0;
import solid.functions.Func1;
import solid.stream.Stream;

/**
 * Optional is a wrapper around a value that can be empty (null).
 * It provides utility methods to handle different cases.
 */
public class Optional<T> {

    private static final Optional<?> EMPTY = new Optional<>(null);

    private final T value;

    private Optional(T value) {
        this.value = value;
    }

    /**
     * Returns an empty optional.
     */
    public static <T> Optional<T> empty() {
        return (Optional<T>) EMPTY;
    }

    /**
     * Returns an optional for a given value
     */
    public static <T> Optional<T> of(T value) {
        return value == null ? (Optional<T>) EMPTY : new Optional<>(value);
    }

    /**
     * Returns the optional value. Thrown {@link NullPointerException} if the value does not exist.
     */
    public T get() {
        if (value == null)
            throw new NullPointerException();
        return value;
    }

    /**
     * Returns true if the optional value does exist.
     */
    public boolean isPresent() {
        return value != null;
    }

    /**
     * Calls a given action if the optional value does exist.
     */
    public void ifPresent(Action1<T> action) {
        if (value != null)
            action.call(value);
    }

    /**
     * Returns the current value or default value if optional does not exist.
     */
    public T or(T default1) {
        return value != null ? value : default1;
    }

    /**
     * Returns value of uses a given factory if the value does not exist.
     */
    public T or(Func0<T> func1) {
        return value != null ? value : func1.call();
    }

    /**
     * Returns the value without null checking it.
     */
    public T orNull() {
        return value;
    }

    /**
     * Transforms the value if exists, returns empty Optional otherwise.
     */
    public <R> Optional<R> map(Func1<T, R> func1) {
        return value == null ? Optional.<R>empty() : Optional.of(func1.call(value));
    }

    /**
     * If a value is present return a sequential {@link Stream} containing only
     * that value, otherwise return an empty {@code Stream}.
     *
     * @return the optional value as a {@code Stream}
     */
    public Stream<T> stream() {
        if (!isPresent()) {
            return Stream.of();
        } else {
            if (value instanceof Iterable) {
                return Stream.stream((Iterable<T>) value);
            } else {
                return Stream.of(value);
            }
        }
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
