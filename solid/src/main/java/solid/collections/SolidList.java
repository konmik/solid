package solid.collections;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import solid.stream.Stream;

/**
 * Represents an immutable parcelable list.
 * This is basically a decorator around ArrayList.
 *
 * @param <T> A type of data in the list.
 */
public class SolidList<T> extends Stream<T> implements List<T>, Parcelable {

    private static final SolidList<Object> EMPTY = new SolidList<>(new Object[0]);
    private static final ClassLoader CLASS_LOADER = SolidList.class.getClassLoader();

    private final ArrayList<T> array;

    /**
     * Creates and returns a new {@link SolidList} from an array.
     *
     * @param array a source of data for {@link SolidList}.
     */
    public SolidList(T[] array) {
        this.array = new ArrayList<>(Arrays.asList(array));
    }

    /**
     * Creates a new {@link SolidList} from an {@link Iterable}.
     *
     * @param iterable a source of data for the new {@link SolidList}.
     */
    public SolidList(Iterable<T> iterable) {
        this(iterable, 0);
    }

    /**
     * Creates a new {@link SolidList} from an {@link Iterable}. Provides the possibility
     * to define an initial capacity of the list for performance gain on lists that exceed 12 items.
     *
     * @param initialCapacity initial capacity of the list.
     * @param iterable        a source of data for the new {@link SolidList}.
     */
    public SolidList(Iterable<T> iterable, int initialCapacity) {
        array = new ArrayList<>(initialCapacity);
        for (T value : iterable)
            array.add(value);
    }

    /**
     * Creates a new {@link SolidList} from a {@link Collection}.
     *
     * @param collection a source of data for the new {@link SolidList}.
     */
    public SolidList(Collection<T> collection) {
        this(collection, collection.size());
    }

    /**
     * Returns an empty {@link SolidList}.
     *
     * @param <T> a type of list to return.
     * @return an empty {@link SolidList}.
     */
    public static <T> SolidList<T> empty() {
        //noinspection unchecked
        return (SolidList<T>)EMPTY;
    }

    /**
     * Creates and returns a new {@link SolidList} from an {@link Iterable}.
     *
     * @param iterable a source of data for {@link SolidList}.
     * @param <T>      a type of list to return.
     * @return a new {@link SolidList} that is initialized with data from given {@link Iterable}.
     */
    public static <T> SolidList<T> copyOf(Iterable<T> iterable) {
        return new SolidList<>(iterable);
    }

    /**
     * Creates and returns a new {@link SolidList} from an array.
     *
     * @param array a source of data for {@link SolidList}.
     * @param <T>   a type of list to return.
     * @return a new {@link SolidList} that is initialized with data from the given array.
     */
    public static <T> SolidList<T> copyOf(T[] array) {
        return new SolidList<>(array);
    }

    /**
     * Creates and returns a new single-item {@link SolidList}.
     *
     * @param item an item for {@link SolidList} initialization.
     * @param <T>  a type of list to return.
     * @return a new {@link SolidList} that is initialized with the given item.
     */
    public static <T> SolidList<T> single(T item) {
        return new SolidList<>(Collections.singletonList(item));
    }

    @Deprecated
    @Override
    public void add(int location, T object) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public boolean add(T object) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public boolean addAll(int location, Collection<? extends T> collection) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public boolean addAll(Collection<? extends T> collection) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object object) {
        return array.contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return array.containsAll(collection);
    }

    @Override
    public T get(int location) {
        return array.get(location);
    }

    @Override
    public int indexOf(Object object) {
        return array.indexOf(object);
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIteratorImplementation();
    }

    @Override
    public int lastIndexOf(Object object) {
        return array.lastIndexOf(object);
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListIteratorImplementation();
    }

    @Override
    public ListIterator<T> listIterator(int location) {
        return new ListIteratorImplementation(location);
    }

    @Deprecated
    @Override
    public T remove(int location) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public boolean remove(Object object) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public T set(int location, T object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return array.size();
    }

    @Override
    public SolidList<T> subList(int start, int end) {
        return copyOf(array.subList(start, end));
    }

    @Override
    public Object[] toArray() {
        return array.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] contents) {
        return array.toArray(contents);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected SolidList(Parcel in) {
        //noinspection unchecked,unchecked
        array = in.readArrayList(CLASS_LOADER);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(array);
    }

    public static final Creator<SolidList> CREATOR = new Creator<SolidList>() {
        public SolidList createFromParcel(Parcel in) {
            return new SolidList(in);
        }

        public SolidList[] newArray(int size) {
            return new SolidList[size];
        }
    };

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object o) {
        //noinspection EqualsBetweenInconvertibleTypes
        return array.equals(o);
    }

    @Override
    public int hashCode() {
        return array != null ? array.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SolidList{" +
            "array=" + array +
            '}';
    }

    private class ListIteratorImplementation implements ListIterator<T> {

        ListIterator<T> iterator;

        ListIteratorImplementation() {
            iterator = array.listIterator();
        }

        ListIteratorImplementation(int start) {
            iterator = array.listIterator(start);
        }

        @Override
        public void add(T object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public boolean hasPrevious() {
            return iterator.hasPrevious();
        }

        @Override
        public T next() {
            return iterator.next();
        }

        @Override
        public int nextIndex() {
            return iterator.nextIndex();
        }

        @Override
        public T previous() {
            return iterator.previous();
        }

        @Override
        public int previousIndex() {
            return iterator.previousIndex();
        }

        @Deprecated
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Deprecated
        @Override
        public void set(T object) {
            throw new UnsupportedOperationException();
        }
    }
}
