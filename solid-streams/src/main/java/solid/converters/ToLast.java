package solid.converters;

import java.util.Iterator;

import solid.functions.SolidFunc1;

public class ToLast<T> implements SolidFunc1<Iterable<T>, T> {

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(SolidFunc1)}
     * to convert an iterable stream into one last item of that stream.
     *
     * @param defaultValue a value to return if the stream has no items.
     * @param <T>          a type of value to return.
     * @return a method that converts an iterable stream into one last item of the stream.
     */
    public static <T> SolidFunc1<Iterable<T>, T> toLast(T defaultValue) {
        return new ToLast<>(defaultValue);
    }

    private T defaultValue;

    public ToLast(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public T call(Iterable<T> iterable) {
        Iterator<T> iterator = iterable.iterator();
        T value = defaultValue;
        while (iterator.hasNext())
            value = iterator.next();
        return value;
    }
}
