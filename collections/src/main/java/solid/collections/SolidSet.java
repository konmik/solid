package solid.collections;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import solid.functions.Func1;
import solid.stream.Stream;

/**
 * Represents an immutable parcelable set.
 * This is basically a decorator around LinkedHashSet.
 *
 * @param <T> A type of data in the list.
 */
public class SolidSet<T> extends Stream<T> implements Set<T>, Parcelable {

    private static final Set<Object> EMPTY = new SolidSet<>(Collections.emptySet());

    private final Set<T> set;

    public SolidSet(T[] array) {
        this(Arrays.asList(array));
    }

    public SolidSet(Collection<? extends T> collection) {
        this.set = Collections.unmodifiableSet(new LinkedHashSet<>(collection));
    }

    public SolidSet(Iterable<? extends T> iterable) {
        this(iterable, 0);
    }

    public SolidSet(Iterable<? extends T> iterable, int initialCapacity) {
        Set<T> set = new LinkedHashSet<>(initialCapacity);
        for (T value : iterable)
            set.add(value);
        this.set = Collections.unmodifiableSet(set);
    }

    public static <T> SolidSet<T> empty() {
        //noinspection unchecked
        return (SolidSet<T>) EMPTY;
    }

    /**
     * Creates a SolidSet from given items.
     *
     * @param items items to create SolidSet from.
     * @param <T>   a type if set items.
     * @return a SolidSet from given items.
     */
    public static <T> SolidSet<T> set(T... items) {
        return new SolidSet<>(items);
    }

    @Override
    public boolean contains(Object object) {
        return set.contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return set.containsAll(collection);
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public Object[] toArray() {
        return set.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] array) {
        return set.toArray(array);
    }

    @Override
    public Iterator<T> iterator() {
        return set.iterator();
    }

    @Deprecated
    @Override
    public boolean add(T object) {
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

    private static final ClassLoader CLASS_LOADER = SolidSet.class.getClassLoader();

    protected SolidSet(Parcel in) {
        set = Collections.unmodifiableSet(new LinkedHashSet<>(in.readArrayList(CLASS_LOADER)));
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(new ArrayList(set));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SolidSet> CREATOR = new Creator<SolidSet>() {
        @Override
        public SolidSet createFromParcel(Parcel in) {
            return new SolidSet(in);
        }

        @Override
        public SolidSet[] newArray(int size) {
            return new SolidSet[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return set.equals(((SolidSet<?>) o).set);
    }

    @Override
    public int hashCode() {
        return set.hashCode();
    }

    @Override
    public String toString() {
        return "SolidSet{" +
            "set=" + set +
            '}';
    }

    @Override
    public <R> SolidSet<R> map(Func1<T, R> func) {
        final List<R> result = new ArrayList<>(set.size());
        for (T item : set) {
            result.add(func.call(item));
        }
        return new SolidSet<>(result);
    }
}
