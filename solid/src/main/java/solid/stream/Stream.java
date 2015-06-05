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

    public static <T> Stream<T> stream(T[] array) {
        return new Copy<>(Arrays.asList(array));
    }

    public static <T> Stream<T> stream(Iterable<T> source) {
        return new Copy<>(source);
    }

    public List<T> toList() {
        return collect(ToList.<T>toList());
    }

    public SolidList<T> toSolidList() {
        return collect(ToSolidList.<T>toSolidList());
    }

    public <R> Stream<R> map(SolidFunc1<T, R> func) {
        return new Map<>(this, func);
    }

    public Stream<T> filter(SolidFunc1<T, Boolean> func) {
        return new Filter<>(this, func);
    }

    public Stream<T> without(T value) {
        return new Filter<>(this, new NotEqualTo<>(value));
    }

    public Stream<T> with(T value) {
        return new Merge<>(this, new Single<>(value));
    }

    public Stream<T> merge(Iterable<T> with) {
        return new Merge<>(this, with);
    }

    public Stream<T> distinct() {
        return new Filter<>(this, new DistinctFilter<T>());
    }

    public Stream<T> mergeDistinct(Iterable<T> with) {
        DistinctFilter<T> distinctFilter = new DistinctFilter<>();
        return filter(distinctFilter).merge(new Filter<>(with, distinctFilter));
    }

    public Stream<T> sort(Comparator<T> comparator) {
        return new Sort<>(this, comparator);
    }

    public Stream<T> reverse() {
        return new Reverse<>(this);
    }

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
