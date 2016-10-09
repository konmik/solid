package solid.collections;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import solid.functions.Func1;
import solid.stream.Stream;

import static java.util.Arrays.asList;

/**
 * Represents an immutable parcelable list.
 * This is basically a decorator around ArrayList.
 *
 * @param <T> A type of data in the list.
 */
public class SolidList<T> extends Stream<T> implements List<T>, Parcelable {

    private static final SolidList<Object> EMPTY = new SolidList<>(Collections.emptyList());

    private final List<T> list;

    /**
     * Creates and returns a new {@link SolidList} from an array.
     *
     * @param array a source of data for {@link SolidList}.
     */
    public SolidList(T[] array) {
        this(asList(array));
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
        ArrayList<T> list = new ArrayList<>(initialCapacity);
        for (T value : iterable)
            list.add(value);
        this.list = Collections.unmodifiableList(list);
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
        return (SolidList<T>) EMPTY;
    }

    /**
     * Creates a {@link SolidList} from given arguments.
     *
     * @param items items to create list from.
     * @param <T>   <T> a type of list items to return.
     * @return a {@link SolidList} created from items.
     */
    public static <T> SolidList<T> list(T... items) {
        return new SolidList<>(items);
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
        return list.contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return list.containsAll(collection);
    }

    @Override
    public T get(int location) {
        return list.get(location);
    }

    @Override
    public int indexOf(Object object) {
        return list.indexOf(object);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public int lastIndexOf(Object object) {
        return list.lastIndexOf(object);
    }

    @Override
    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int location) {
        return list.listIterator(location);
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
        return list.size();
    }

    @Override
    public SolidList<T> subList(int start, int end) {
        return new SolidList<>(list.subList(start, end));
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] contents) {
        return list.toArray(contents);
    }

    private static final ClassLoader CLASS_LOADER = SolidList.class.getClassLoader();

    @Override
    public int describeContents() {
        return 0;
    }

    protected SolidList(Parcel in) {
        //noinspection unchecked,unchecked
        ArrayList<T> temp = new ArrayList<>();
        in.readList(temp, CLASS_LOADER);
        list = Collections.unmodifiableList(temp);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(list);
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
        if (this == o) return true;
        return list.equals(o);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public String toString() {
        return "SolidList{" +
            "list=" + list +
            '}';
    }

    @Override
    public <R> SolidList<R> map(Func1<T, R> func) {
        final List<R> result = new ArrayList<>(list.size());
        for (T item : list) {
            result.add(func.call(item));
        }
        return new SolidList<>(result);
    }
}
