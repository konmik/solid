package solid.stream;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import solid.converters.Accumulate;
import solid.converters.Fold;
import solid.converters.Reduce;
import solid.converters.ToFirst;
import solid.converters.ToLast;
import solid.converters.ToList;
import solid.filters.DistinctFilter;
import solid.filters.NotEqualTo;
import solid.filters.NotIn;
import solid.functions.SolidFunc1;
import solid.functions.SolidFunc2;

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
    public static <T> Stream<T> stream(Iterable<? extends T> source) {
        return new Copy<>(source);
    }

    /**
     * Returns a stream with just one given element.
     *
     * @param value the element value.
     * @param <T>   the type of the stream.
     * @return a stream with just one given element.
     */
    public static <T> Stream<T> of(T value) {
        return new Copy<>(Collections.singleton(value));
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

    /**
     * Converts a {@link Stream} into a {@link List}.
     *
     * @return a {@link List} containing all {@link Stream} items.
     */
    public List<T> toList() {
        return collect(ToList.<T>toList());
    }

    /**
     * Converts a {@link Stream} into a {@link List}, providing the possibility to set a starting capacity
     * of the returned list.
     * <p/>
     * Use this method instead of {@link #toList()} for better performance on
     * streams that can have more than 12 items.
     *
     * @param initialCapacity initial capacity of the returning list.
     * @return a {@link List} containing all {@link Stream} items.
     */
    public List<T> toList(int initialCapacity) {
        return collect(ToList.<T>toList(initialCapacity));
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
    public <R> R fold(R initialValue, SolidFunc2<R, T, R> operation) {
        return new Fold<>(initialValue, operation).call(this);
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
    public T reduce(SolidFunc2<T, T, T> operation) {
        return new Reduce<>(operation).call(this);
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
        return new Map<>(this, func);
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
        return new FlatMap<>(this, func);
    }

    /**
     * Returns a new stream that contains all items of the current stream for which a given function returned {@link Boolean#TRUE}.
     *
     * @param func a function to call for each item.
     * @return a new stream that contains all items of the current stream for which a given function returned {@link Boolean#TRUE}.
     */
    public Stream<T> filter(SolidFunc1<T, Boolean> func) {
        return new Filter<>(this, func);
    }

    /**
     * Returns a new stream that contains all items of the current stream except of a given item.
     *
     * @param value a value to filter out.
     * @return a new stream that contains all items of the current stream except of a given item.
     */
    public Stream<T> without(T value) {
        return new Filter<>(this, new NotEqualTo<>(value));
    }

    /**
     * Adds a value at the end of the current stream.
     *
     * @param value a value to add.
     * @return a new stream that contains all items of the current stream and one additional given item at the end.
     */
    public Stream<T> with(T value) {
        return new Merge<>(this, new Single<>(value));
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
        return new Filter<>(this, new NotIn<>(from));
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
        return new Filter<>(this, new DistinctFilter<>());
    }

    /**
     * Merges an iterable stream with another, applying {@link #distinct()} the same
     * time. This operation is slightly faster and less memory consuming than
     * merging two streams and calling {@link #distinct()} later.
     * <p/>
     * This operator keeps a list of all items that has been passed to
     * compare it against next items.
     *
     * @param with a stream to add at the end of current stream.
     * @return the combined stream.
     */
    public Stream<T> mergeDistinct(Iterable<? extends T> with) {
        return new MergeDistinct<>(this, with);
    }

    /**
     * Returns a new stream that contains all items of the current stream in sorted order.
     * The operator creates a list of all items internally.
     *
     * @param comparator a comparator to apply.
     * @return a new stream that contains all items of the current stream in sorted order.
     */
    public Stream<T> sort(Comparator<T> comparator) {
        return new Sort<>(this, comparator);
    }

    /**
     * Returns a new stream that contains all items of the current stream in reverse order.
     * The operator creates a list of all items internally.
     *
     * @return a new stream that emits all items of the current stream in reverse order.
     */
    public Stream<T> reverse() {
        return new Reverse<>(this);
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
}
