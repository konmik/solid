package solid.converters;

import java.util.Iterator;

import solid.functions.SolidFunc1;

public class ToFirst<T> implements SolidFunc1<Iterable<T>, T> {

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(SolidFunc1)}
     * to convert an iterable stream into one first item of that stream.
     *
     * @param defaultValue a value to return if the stream has no items
     * @param <T>          a type of value to return
     * @return a method that converts an iterable stream into one first item of that stream.
     */
    public static <T> SolidFunc1<Iterable<T>, T> toFist(T defaultValue) {
        return new ToFirst<>(defaultValue);
    }

    private T defaultValue;

    public ToFirst(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public T call(Iterable<T> value) {
        Iterator<T> iterator = value.iterator();
        return iterator.hasNext() ? iterator.next() : defaultValue;
    }
}
