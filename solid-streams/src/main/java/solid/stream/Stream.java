package solid.stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import solid.converters.Accumulate;
import solid.converters.ToArrayList;
import solid.converters.ToFirst;
import solid.converters.ToLast;
import solid.functions.SolidFunc0;
import solid.functions.SolidFunc1;
import solid.functions.SolidFunc2;
import solid.optional.Optional;

/**
 * This is a base stream class for implementation of iterable streams.
 * It provides shortcuts for calling operators in a chaining manner.
 *
 * @param <T> the type of object returned by the iterator.
 */
public abstract class Stream<T> implements Iterable<T> {

    /**
     * Converts a non-primitive array into a {@link Stream}.
     *
     * @param array array to convert.
     * @param <T>   a type of array items.
     * @return a {@link Stream} that represents source array's elements.
     */
    public static <T> Stream<T> stream(T[] array) {
        return new CopyArray<>(array);
    }

    /**
     * Converts a source {@link Iterable} into a {@link Stream}.
     *
     * @param source a source {@link Iterable} to convert.
     * @param <T>    a type of stream items
     * @return a {@link Stream} that represents source {@link Iterable} elements
     */
    public static <T> Stream<T> stream(Iterable<T> source) {
        return from(source::iterator);
    }

    /**
     * Returns a stream with just one given element.
     *
     * @param value the element value.
     * @param <T>   the type of the stream.
     * @return a stream with just one given element.
     */
    public static <T> Stream<T> of(T value) {
        return from(() -> new ReadOnlyIterator<T>() {

            boolean has = true;

            @Override
            public boolean hasNext() {
                return has;
            }

            @Override
            public T next() {
                has = false;
                return value;
            }
        });
    }

    /**
     * Returns a stream of given values.
     *
     * @param values stream values.
     * @param <T>    the type of the stream.
     * @return a stream of given values.
     */
    public static <T> Stream<T> of(T... values) {
        return stream(values);
    }

    public static <T> Stream<T> from(SolidFunc0<Iterator<T>> func) {
        return new Stream<T>() {
            @Override
            public Iterator<T> iterator() {
                return func.call();
            }
        };
    }

    public static <T> Stream<T> of() {
        return from(() -> EMPTY_ITERATOR);
    }

    /**
     * Converts the current stream into any value with a given method.
     *
     * @param collector a method that should be used to return value.
     * @param <R>       a type of value to return.
     * @return a value that has been returned by the given collecting method.
     */
    public <R> R collect(SolidFunc1<Iterable<T>, R> collector) {
        return collector.call(this);
    }

    /**
     * Returns a value that has been received by applying an accumulating function to each item of the current stream.
     * An initial value should be provided.
     *
     * @param <R>       a type of the returning and initial values.
     * @param operation a function to apply to the each stream item.
     * @return a value that has been received by applying an accumulating function to each item of the current stream.
     */
    public <R> R fold(R initial, SolidFunc2<R, T, R> operation) {
        R value = initial;
        for (T anIt : this)
            value = operation.call(value, anIt);
        return value;
    }

    /**
     * Returns a value that has been received by applying an accumulating function to each item of the current stream.
     * An initial value is taken from the first value.
     *
     * If the stream is empty an {@link UnsupportedOperationException} will be thrown.
     *
     * @param operation a function to apply to the each (except the first one) stream item.
     * @return a value that has been received by applying an accumulating function to each item of the current stream.
     */
    public Optional<T> reduce(SolidFunc2<T, T, T> operation) {
        Iterator<T> iterator = iterator();
        if (!iterator.hasNext())
            return Optional.empty();

        T result = iterator.next();
        while (iterator.hasNext())
            result = operation.call(result, iterator.next());
        return Optional.of(result);
    }

    /**
     * Returns a value that has been received by applying an accumulating function to each item of the current stream,
     * starting from a given initial value. The initial value can have a different type from stream values.
     *
     * @param accumulator a function to apply to the each stream item.
     * @return a value that has been received by applying an accumulating function to each item of the current stream.
     */
    public <R> R accumulate(R initial, SolidFunc2<R, T, R> accumulator) {
        return new Accumulate<>(initial, accumulator).call(this);
    }

    /**
     * Convert an iterable stream into one first item of the stream.
     *
     * @param defaultValue a value to return if the stream has no items.
     * @return the first item of the stream.
     */
    public T first(T defaultValue) {
        return collect(new ToFirst<>(defaultValue));
    }

    /**
     * Convert an iterable stream into one last item of the stream.
     *
     * @param defaultValue a value to return if the stream has no items.
     * @return the last item of the stream.
     */
    public T last(T defaultValue) {
        return collect(new ToLast<>(defaultValue));
    }

    /**
     * Returns a new stream that is created by a given factory.
     * The factory accepts the current stream as an argument.
     *
     * @param factory a method that produces the new stream.
     * @param <R>     a type of items new stream returns.
     * @return a constructed stream.
     */
    public <R> Stream<R> compose(SolidFunc1<Stream<T>, Stream<R>> factory) {
        return factory.call(this);
    }

    /**
     * Returns a new stream that contains items that has been returned by a given function for each item in the current stream.
     *
     * @param func a function that takes an item of the current stream and returns a corresponding value for the new stream.
     * @param <R>  a type of items new stream returns.
     * @return a new stream that contains items that has been returned by a given function for each item in the current stream.
     */
    public <R> Stream<R> map(SolidFunc1<T, R> func) {
        return from(() -> new ReadOnlyIterator<R>() {

            Iterator<T> iterator = iterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public R next() {
                return func.call(iterator.next());
            }
        });
    }

    /**
     * Returns a new stream that contains items that has been returned a given function for each item in the current stream.
     * The difference from {@link #map(SolidFunc1)} is that a given function can return more than one item for
     * each item of the current list.
     *
     * @param func a function that takes an item of the current stream and returns a stream of values for the new stream.
     * @param <R>  a type of items new stream returns.
     * @return a new stream that contains items that has been returned by a given function for each item in the current stream.
     */
    public <R> Stream<R> flatMap(SolidFunc1<T, Iterable<R>> func) {
        return from(() -> new ReadOnlyIterator<R>() {

            Iterator<T> iterator = iterator();
            Iterator<R> next;

            @Override
            public boolean hasNext() {
                if (next == null || !next.hasNext()) {
                    if (iterator.hasNext())
                        next = func.call(iterator.next()).iterator();
                }

                return next != null && next.hasNext();
            }

            @Override
            public R next() {
                return next.next();
            }
        });
    }

    /**
     * Returns a new stream that contains all items of the current stream for which a given function returned {@link Boolean#TRUE}.
     *
     * @param func a function to call for each item.
     * @return a new stream that contains all items of the current stream for which a given function returned {@link Boolean#TRUE}.
     */
    public Stream<T> filter(SolidFunc1<T, Boolean> func) {
        return from(() -> new ReadOnlyIterator<T>() {
            Iterator<? extends T> iterator = iterator();
            T next;
            boolean hasNext;

            private void process() {
                while (!hasNext && iterator.hasNext()) {
                    T n = iterator.next();
                    if (func.call(n)) {
                        next = n;
                        hasNext = true;
                    }
                }
            }

            @Override
            public boolean hasNext() {
                process();
                return hasNext;
            }

            @Override
            public T next() {
                process();
                hasNext = false;
                return next;
            }
        });
    }

    /**
     * Returns a new stream that contains all items of the current stream except of a given item.
     *
     * @param value a value to filter out.
     * @return a new stream that contains all items of the current stream except of a given item.
     */
    public Stream<T> without(T value) {
        return filter(it -> ((it == null) ? (value != null) : !it.equals(value)));
    }

    /**
     * Adds a value at the end of the current stream.
     *
     * @param value a value to add.
     * @return a new stream that contains all items of the current stream and one additional given item at the end.
     */
    public Stream<T> with(T value) {
        return new Merge<>(this, of(value));
    }

    /**
     * Adds items from another stream to the end of the current stream.
     *
     * @param with an {@link Iterable} that should be used to emit items after items in the current stream ran out.
     * @return a new stream that contains items from both streams.
     */
    public Stream<T> merge(Iterable<? extends T> with) {
        return new Merge<>(this, with);
    }

    /**
     * Returns a stream that includes only that items of the current stream that do not
     * exist in a given stream.
     *
     * @param from a stream of values that should be separated from the current stream.
     * @return a stream that includes only that items of the current stream that do not
     * exist in a given stream.
     */
    public Stream<T> separate(Iterable<? extends T> from) {
        ArrayList<T> list = ToArrayList.<T>toArrayList().call(from);
        return filter(it -> !list.contains(it));
    }

    /**
     * Creates a new stream that contains only the first given amount of items of the current stream.
     *
     * @param count a number of items to take.
     * @return a new stream that contains only the first given amount of items of the current stream.
     */
    public Stream<T> take(int count) {
        return new Take<>(this, count);
    }

    /**
     * Creates a new stream that contains elements of the current stream with a given number of them skipped from the beginning.
     *
     * @param count a number items to skip.
     * @return a new stream that contains elements of the current stream with a given number of them skipped from the beginning.
     */
    public Stream<T> skip(int count) {
        return new Skip<>(this, count);
    }

    /**
     * Returns a new stream that filters out duplicate items off the current stream.
     * <p/>
     * This operator keeps a list of all items that has been passed to
     * compare it against next items.
     *
     * @return a new stream that filters out duplicate items off the current stream.
     */
    public Stream<T> distinct() {
        ArrayList<T> passed = new ArrayList<>();
        return filter(value -> {
            if (passed.contains(value))
                return false;
            passed.add(value);
            return true;
        });
    }

    /**
     * Returns a new stream that contains all items of the current stream in sorted order.
     * The operator creates a list of all items internally.
     *
     * @param comparator a comparator to apply.
     * @return a new stream that contains all items of the current stream in sorted order.
     */
    public Stream<T> sorted(Comparator<T> comparator) {
        return Stream.from(() -> {
            ArrayList<T> array = ToArrayList.<T>toArrayList().call(this);
            Collections.sort(array, comparator);
            return array.iterator();
        });
    }

    /**
     * Returns a new stream that contains all items of the current stream in reverse order.
     * The operator creates a list of all items internally.
     *
     * @return a new stream that emits all items of the current stream in reverse order.
     */
    public Stream<T> reverse() {
        return Stream.from(() -> {
            ArrayList<T> array = ToArrayList.<T>toArrayList().call(this);
            Collections.reverse(array);
            return array.iterator();
        });
    }

    /**
     * Returns a stream that contains all values of the original stream that has been casted to a given class type.
     *
     * @param c   a class to cast into
     * @param <R> a type of the class
     * @return a stream that contains all values of the original stream that has been casted to a given class type.
     */
    public <R> Stream<R> cast(Class<R> c) {
        return map(c::cast);
    }

    private static ReadOnlyIterator EMPTY_ITERATOR = new ReadOnlyIterator() {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Object next() {
            throw new IllegalStateException("Can't get a value from an empty iterator.");
        }
    };
}
