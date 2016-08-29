package solid.stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import solid.collections.Grouped;
import solid.collections.Indexed;
import solid.collectors.ToArrayList;
import solid.functions.Action1;
import solid.functions.Func1;
import solid.functions.Func2;
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
    public static <T> Stream<T> stream(final T[] array) {
        return new FixedSizeStream<>(array.length, new Func1<Integer, T>() {
            @Override
            public T call(Integer index) {
                return array[index];
            }
        });
    }

    /**
     * Converts a source {@link Iterable} into a {@link Stream}.
     *
     * @param source a source {@link Iterable} to convert.
     * @param <T>    a type of stream items
     * @return a {@link Stream} that represents source {@link Iterable} elements
     */
    public static <T> Stream<T> stream(final Iterable<T> source) {
        return new Stream<T>() {
            @Override
            public Iterator<T> iterator() {
                return source.iterator();
            }
        };
    }

    /**
     * Returns a stream with just one given element.
     *
     * @param value the element value.
     * @param <T>   the type of the stream.
     * @return a stream with just one given element.
     */
    public static <T> Stream<T> of(final T value) {
        return new Stream<T>() {
            @Override
            public Iterator<T> iterator() {
                return new ReadOnlyIterator<T>() {

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
                };
            }
        };
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
     * Returns an empty stream.
     *
     * @param <T> the type of the stream.
     * @return an empty stream.
     */
    public static <T> Stream<T> of() {
        return EMPTY;
    }

    /**
     * Converts the current stream into any value with a given method.
     *
     * @param collector a method that should be used to return value.
     * @param <R>       a type of value to return.
     * @return a value that has been returned by the given collecting method.
     */
    public <R> R collect(Func1<Iterable<T>, R> collector) {
        return collector.call(this);
    }

    /**
     * Returns a value that has been received by applying an accumulating function to each item of the current stream.
     * An initial value should be provided.
     *
     * @param <R>         a type of the returning and initial values.
     * @param accumulator a function to apply to each stream item.
     * @return a value that has been received by applying an accumulating function to each item of the current stream.
     */
    public <R> R reduce(R initial, Func2<R, T, R> accumulator) {
        R value = initial;
        for (T anIt : this)
            value = accumulator.call(value, anIt);
        return value;
    }

    /**
     * Returns a value that has been received by applying an accumulating function to each item of the current stream.
     * An initial value is taken from the first value.
     *
     * If the stream is empty an {@link UnsupportedOperationException} will be thrown.
     *
     * @param accumulator a function to apply to each (except the first one) stream item.
     * @return a value that has been received by applying an accumulating function to each item of the current stream.
     */
    public Optional<T> reduce(Func2<T, T, T> accumulator) {
        Iterator<T> iterator = iterator();
        if (!iterator.hasNext())
            return Optional.empty();

        T result = iterator.next();
        while (iterator.hasNext())
            result = accumulator.call(result, iterator.next());
        return Optional.of(result);
    }

    /**
     * Convert an iterable stream into one first item of the stream.
     *
     * @return the first item of the stream.
     */
    public Optional<T> first() {
        Iterator<T> iterator = iterator();
        return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.<T>empty();
    }

    /**
     * Convert an iterable stream into one last item of the stream.
     *
     * @return the last item of the stream.
     */
    public Optional<T> last() {
        Iterator<T> iterator = iterator();
        T value = null;
        while (iterator.hasNext())
            value = iterator.next();
        return Optional.of(value);
    }

    /**
     * Returns a new stream that is created by a given factory.
     * The factory accepts the current stream as an argument.
     *
     * @param factory a method that produces the new stream.
     * @param <R>     a type of items new stream returns.
     * @return a constructed stream.
     */
    public <R> Stream<R> compose(Func1<Stream<T>, Stream<R>> factory) {
        return factory.call(this);
    }

    /**
     * Returns a new stream that contains items that has been returned by a given function for each item in the current stream.
     *
     * @param func a function that takes an item of the current stream and returns a corresponding value for the new stream.
     * @param <R>  a type of items new stream returns.
     * @return a new stream that contains items that has been returned by a given function for each item in the current stream.
     */
    public <R> Stream<R> map(final Func1<? super T, ? extends R> func) {
        return new Stream<R>() {
            @Override
            public Iterator<R> iterator() {
                return new ReadOnlyIterator<R>() {

                    Iterator<T> iterator = Stream.this.iterator();

                    @Override
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }

                    @Override
                    public R next() {
                        return func.call(iterator.next());
                    }
                };
            }
        };
    }

    /**
     * Returns a new stream that contains items that has been returned a given function for each item in the current stream.
     * The difference from {@link #map(Func1)} is that a given function can return more than one item for
     * each item of the current list.
     *
     * @param func a function that takes an item of the current stream and returns a stream of values for the new stream.
     * @param <R>  a type of items new stream returns.
     * @return a new stream that contains items that has been returned by a given function for each item in the current stream.
     */
    public <R> Stream<R> flatMap(final Func1<? super T, ? extends Iterable<? extends R>> func) {
        return new Stream<R>() {
            @Override
            public Iterator<R> iterator() {
                return new ReadOnlyIterator<R>() {

                    Iterator<T> iterator = Stream.this.iterator();
                    Iterator<? extends R> next;

                    @Override
                    public boolean hasNext() {
                        while ((next == null || !next.hasNext()) && iterator.hasNext()) {
                            next = func.call(iterator.next()).iterator();
                        }

                        return next != null && next.hasNext();
                    }

                    @Override
                    public R next() {
                        return next.next();
                    }
                };
            }
        };
    }

    /**
     * Returns a new stream that contains all items of the current stream for which a given function returned {@link Boolean#TRUE}.
     *
     * @param func a function to call for each item.
     * @return a new stream that contains all items of the current stream for which a given function returned {@link Boolean#TRUE}.
     */
    public Stream<T> filter(final Func1<? super T, Boolean> func) {
        return new Stream<T>() {
            @Override
            public Iterator<T> iterator() {
                return new ReadOnlyIterator<T>() {

                    Iterator<? extends T> iterator = Stream.this.iterator();
                    T next;
                    boolean hasNext;

                    private void process() {
                        while (!hasNext && iterator.hasNext()) {
                            final T n = iterator.next();
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
                };
            }
        };
    }

    /**
     * Returns a new stream that contains all items of the current stream with addition of a given item.
     *
     * @param value a value to add.
     * @return a new stream that contains all items of the current stream with addition of a given item.
     */
    public Stream<T> merge(final T value) {
        return new Stream<T>() {
            @Override
            public Iterator<T> iterator() {
                return new ReadOnlyIterator<T>() {

                    Iterator<T> iterator = Stream.this.iterator();
                    boolean completed;

                    @Override
                    public boolean hasNext() {
                        return iterator.hasNext() || !completed;
                    }

                    @Override
                    public T next() {
                        if (iterator.hasNext())
                            return iterator.next();
                        completed = true;
                        return value;
                    }
                };
            }
        };
    }

    /**
     * Returns a new stream that contains all items of the current stream except of a given item.
     *
     * @param value a value to filter out.
     * @return a new stream that contains all items of the current stream except of a given item.
     */
    public Stream<T> separate(final T value) {
        return filter(new Func1<T, Boolean>() {
            @Override
            public Boolean call(T it) {
                return ((it == null) ? (value != null) : !it.equals(value));
            }
        });
    }

    /**
     * Adds items from another stream to the end of the current stream.
     *
     * @param with an {@link Iterable} that should be used to emit items after items in the current stream ran out.
     * @return a new stream that contains items from both streams.
     */
    public Stream<T> merge(final Iterable<? extends T> with) {
        return new Stream<T>() {
            @Override
            public Iterator<T> iterator() {
                return new ReadOnlyIterator<T>() {

                    Iterator<T> iterator = Stream.this.iterator();
                    Iterator<? extends T> withIterator = with.iterator();

                    @Override
                    public boolean hasNext() {
                        return iterator.hasNext() || withIterator.hasNext();
                    }

                    @Override
                    public T next() {
                        return iterator.hasNext() ? iterator.next() : withIterator.next();
                    }
                };
            }
        };
    }

    /**
     * Returns a new stream that contains items that has been received by sequentially combining items of the streams into pairs
     * and then applying given function to each pair of items.
     *
     * @param with a {@link Stream} to zip current stream with.
     * @param func a function that takes an item of the current stream and an item of another stream
     *             and returns a corresponding value for the new stream.
     * @param <S>  a type of a stream to zip current stream with.
     * @param <R>  a type of items new stream returns.
     * @return a new stream that contains items that has been received by sequentially combining items of the streams into pairs
     * and then applying given function to each pair of items.
     */
    public <S, R> Stream<R> zipWith(final Stream<? extends S> with, final Func2<? super T, ? super S, ? extends R> func) {
        return new Stream<R>() {
            @Override
            public Iterator<R> iterator() {
                return new ReadOnlyIterator<R>() {

                    Iterator<T> iterator = Stream.this.iterator();
                    Iterator<? extends S> withIterator = with.iterator();

                    @Override
                    public boolean hasNext() {
                        return iterator.hasNext() && withIterator.hasNext();
                    }

                    @Override
                    public R next() {
                        return func.call(iterator.next(), withIterator.next());
                    }
                };
            }
        };
    }

    /**
     * Returns a stream that includes only that items of the current stream that do not
     * exist in a given stream.
     *
     * @param from a stream of values that should be separated from the current stream.
     * @return a stream that includes only that items of the current stream that do not
     * exist in a given stream.
     */
    public Stream<T> separate(Iterable<T> from) {
        final ArrayList<T> list = ToArrayList.<T>toArrayList().call(from);
        return filter(new Func1<T, Boolean>() {
            @Override
            public Boolean call(T it) {return !list.contains(it);}
        });
    }

    /**
     * Creates a new stream that contains only the first given amount of items of the current stream.
     *
     * @param count a number of items to take.
     * @return a new stream that contains only the first given amount of items of the current stream.
     */
    public Stream<T> take(final int count) {
        return new Stream<T>() {
            @Override
            public Iterator<T> iterator() {
                return new ReadOnlyIterator<T>() {

                    Iterator<T> iterator = Stream.this.iterator();
                    int left = count;

                    @Override
                    public boolean hasNext() {
                        return left > 0 && iterator.hasNext();
                    }

                    @Override
                    public T next() {
                        left--;
                        return iterator.next();
                    }
                };
            }
        };
    }

    /**
     * Creates a new stream that contains elements of the current stream with a given number of them skipped from the beginning.
     *
     * @param count a number items to skip.
     * @return a new stream that contains elements of the current stream with a given number of them skipped from the beginning.
     */
    public Stream<T> skip(final int count) {
        return new Stream<T>() {
            @Override
            public Iterator<T> iterator() {
                Iterator<T> iterator = Stream.this.iterator();
                for (int skip = count; skip > 0 && iterator.hasNext(); skip--)
                    iterator.next();
                return iterator;
            }
        };
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
        final ArrayList<T> passed = new ArrayList<>();
        return filter(new Func1<T, Boolean>() {
            @Override
            public Boolean call(T value) {
                if (passed.contains(value))
                    return false;
                passed.add(value);
                return true;
            }
        });
    }

    /**
     * Returns a new stream that contains all items of the current stream in sorted order.
     * The operator creates a list of all items internally.
     *
     * @param comparator a comparator to apply.
     * @return a new stream that contains all items of the current stream in sorted order.
     */
    public Stream<T> sort(final Comparator<T> comparator) {
        return new Stream<T>() {
            @Override
            public Iterator<T> iterator() {
                final ArrayList<T> array = ToArrayList.<T>toArrayList().call(Stream.this);
                Collections.sort(array, comparator);
                return array.iterator();
            }
        };
    }

    /**
     * Returns a new stream that contains all items of the current stream in reverse order.
     * The operator creates a list of all items internally.
     *
     * @return a new stream that emits all items of the current stream in reverse order.
     */
    public Stream<T> reverse() {
        return new Stream<T>() {
            @Override
            public Iterator<T> iterator() {
                final ArrayList<T> array = ToArrayList.<T>toArrayList().call(Stream.this);
                Collections.reverse(array);
                return array.iterator();
            }
        };
    }

    /**
     * Returns a stream that contains all values of the original stream that has been casted to a given class type.
     *
     * @param c   a class to cast into
     * @param <R> a type of the class
     * @return a stream that contains all values of the original stream that has been casted to a given class type.
     */
    public <R> Stream<R> cast(final Class<R> c) {
        return map(new Func1<T, R>() {
            @Override
            public R call(T obj) {return c.cast(obj);}
        });
    }

    /**
     * Returns true if all of stream items satisfy a given condition.
     *
     * @param predicate a condition to test.
     * @return true if all of stream items satisfy a given condition.
     */
    public boolean every(Func1<? super T, Boolean> predicate) {
        for (T item : this) {
            if (!predicate.call(item))
                return false;
        }
        return true;
    }

    /**
     * Returns true if any of stream items satisfy a given condition.
     *
     * @param predicate a condition to test.
     * @return true if any of stream items satisfy a given condition.
     */
    public boolean any(Func1<? super T, Boolean> predicate) {
        for (T item : this) {
            if (predicate.call(item))
                return true;
        }
        return false;
    }

    /**
     * Returns a new stream of {@link Grouped} that is composed from keys and values that has been
     * extracted from each source stream item.
     *
     * @param groupSelector a function that extracts a key from a given item.
     * @param valueSelector a function that extracts a value from a given item.
     * @param <K>           a type of key value.
     * @return a new stream of {@link Grouped} that is grouped by a key extracted from each source stream item.
     */
    public <K, V> Stream<Grouped<K, V>> groupBy(final Func1<? super T, ? extends K> groupSelector, final Func1<? super T, ? extends V> valueSelector) {
        return new Stream<Grouped<K, V>>() {
            @Override
            public Iterator<Grouped<K, V>> iterator() {
                List<K> keys = new ArrayList<>();
                final Map<K, ArrayList<V>> map = new HashMap<>();
                for (T item : Stream.this) {
                    K key = groupSelector.call(item);
                    ArrayList<V> list = map.get(key);
                    if (list == null) {
                        keys.add(key);
                        map.put(key, list = new ArrayList<>());
                    }
                    list.add(valueSelector.call(item));
                }
                return stream(keys)
                    .map(new Func1<K, Grouped<K, V>>() {
                        @Override
                        public Grouped<K, V> call(K key) {
                            return new Grouped<>(key, stream(map.get(key)));
                        }
                    })
                    .iterator();
            }
        };
    }

    /**
     * Returns a new stream of {@link Grouped} that is composed from keys that has been
     * extracted from each source stream item.
     *
     * @param groupSelector a function that extracts a key from a given item.
     * @param <K>           a type of key value.
     * @return a new stream of {@link Grouped} that is grouped by a key extracted from each source stream item.
     */
    public <K> Stream<Grouped<K, T>> groupBy(final Func1<? super T, ? extends K> groupSelector) {
        return groupBy(groupSelector, new Func1<T, T>() {
            @Override
            public T call(T value) {
                return value;
            }
        });
    }

    /**
     * Returns a new stream of {@link Indexed} that where each item's index is equal to its sequence number.
     *
     * @return a new stream of {@link Indexed} that where each item's index is equal to its sequence number.
     */
    public Stream<Indexed<T>> index() {
        return map(new Func1<T, Indexed<T>>() {

            int i;

            @Override
            public Indexed<T> call(T value) {
                return new Indexed<>(i++, value);
            }
        });
    }

    /**
     * Executes an action for each item in the stream.
     *
     * @param action an action to execute for each item in the stream.
     */
    public void forEach(Action1<? super T> action) {
        for (T value : this)
            action.call(value);
    }

    /**
     * Executes an action for each item in the stream.
     *
     * @param action an action to execute for each item in the stream.
     */
    public Stream<T> onNext(final Action1<? super T> action) {
        return new Stream<T>() {
            @Override
            public Iterator<T> iterator() {
                return new ReadOnlyIterator<T>() {

                    Iterator<T> iterator = Stream.this.iterator();

                    @Override
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }

                    @Override
                    public T next() {
                        final T next = iterator.next();
                        action.call(next);
                        return next;
                    }
                };
            }
        };
    }

    private static final ReadOnlyIterator EMPTY_ITERATOR = new ReadOnlyIterator() {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Object next() {
            throw new UnsupportedOperationException();
        }
    };

    private static final Stream EMPTY = new Stream() {
        @Override
        public Iterator iterator() {
            return EMPTY_ITERATOR;
        }
    };
}
