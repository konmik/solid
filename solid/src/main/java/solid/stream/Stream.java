package solid.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import solid.collections.SolidList;
import solid.converters.ToList;
import solid.converters.ToSolidList;
import solid.filters.DistinctFilter;
import solid.filters.NotEqualTo;
import solid.functions.SolidFunc1;

/**
 * This stream implementation is about 7 times faster than RxJava and requires less code to use.
 * It does not support multithreading, callbacks and error handling. This is why.
 *
 * @param <T>
 */
public abstract class Stream<T> implements Iterable<T> {

    /**
     * Converts a non-primitive array into a {@link Stream}.
     *
     * @param array array to convert.
     * @param <T>   a type of array items
     * @return a {@link Stream} that represents source array's elements
     */
    public static <T> Stream<T> stream(T[] array) {
        return new Copy<>(Arrays.asList(array));
    }

    /**
     * Converts a source {@link Iterable} into a {@link Stream}.
     *
     * @param source a source {@link Iterable} to convert.
     * @param <T>    a type of stream items
     * @return a {@link Stream} that represents source {@link Iterable} elements
     */
    public static <T> Stream<T> stream(Iterable<T> source) {
        return new Copy<>(source);
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
     * Converts a {@link Stream} into a {@link SolidList} ({@link android.os.Parcelable} and Immutable).
     *
     * @return a {@link SolidList} containing all {@link Stream} items.
     */
    public SolidList<T> toSolidList() {
        return collect(ToSolidList.<T>toSolidList());
    }

    /**
     * Returns a new stream that is created by a given factory.
     * The factory accepts the current stream as an argument.
     *
     * @param factory a method that produces the new stream.
     * @param <R>     a type of items new stream returns.
     * @return a constructed stream.
     */
    public <R> Stream<R> lift(SolidFunc1<Stream<T>, Stream<R>> factory) {
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
     * Adds items from another stream at the end of the current stream.
     *
     * @param with an {@link Iterable} that should be used to emit items after items in the current stream ran out.
     * @return a new stream that contains items from both streams.
     */
    public Stream<T> merge(Iterable<T> with) {
        return new Merge<>(this, with);
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
     * Returns a new stream that filters out duplicate items off the current stream.
     * <p/>
     * This operator keeps a list of all items that has been passed to
     * compare it against next items.
     *
     * @return a new stream that filters out duplicate items off the current stream.
     */
    public Stream<T> distinct() {
        return new Filter<>(this, new DistinctFilter<T>());
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
    public Stream<T> mergeDistinct(Iterable<T> with) {
        DistinctFilter<T> distinctFilter = new DistinctFilter<>();
        return new Merge<>(new Filter<>(this, distinctFilter), (new Filter<>(with, distinctFilter)));
    }

    /**
     * Returns a new stream that contains all items of the current stream in sorted order.
     * The operator creates a list of all items internally.
     *
     * @param comparator a comparator to apply.
     * @return
     */
    public Stream<T> sort(Comparator<T> comparator) {
        return new Sort<>(this, comparator);
    }

    /**
     * Returns a new stream that emits all items of the current stream in reverse order.
     * The operator creates a list of all items internally.
     *
     * @return a new stream that emits all items of the current stream in reverse order.
     */
    public Stream<T> reverse() {
        return new Reverse<>(this);
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

    protected static abstract class ReadOnlyIterator<T> implements Iterator<T> {
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
