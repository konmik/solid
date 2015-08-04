package solid.collections;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents an immutable parcelable map.
 * This is basically a decorator around Hashmap.
 *
 * @param <K> a type of keys.
 * @param <V> a type of values.
 */
public class SolidMap<K, V> implements Map<K, V>, Parcelable {

    private static final ClassLoader CLASS_LOADER = SolidMap.class.getClassLoader();

    private final HashMap<K, V> map;

    public SolidMap(Map<K, V> map) {
        this.map = new HashMap<>(map);
    }

    @Deprecated
    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsKey(value);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return Collections.unmodifiableSet(map.entrySet());
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public Set<K> keySet() {
        return Collections.unmodifiableSet(map.keySet());
    }

    @Deprecated
    @Override
    public V put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public V remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public Collection<V> values() {
        return Collections.unmodifiableCollection(map.values());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public SolidMap(Parcel in) {
        //noinspection unchecked
        map = in.readHashMap(CLASS_LOADER);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(map);
    }

    public static final Creator<SolidMap> CREATOR = new Creator<SolidMap>() {
        @Override
        public SolidMap createFromParcel(Parcel in) {
            return new SolidMap(in);
        }

        @Override
        public SolidMap[] newArray(int size) {
            return new SolidMap[size];
        }
    };

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object o) {
        //noinspection EqualsBetweenInconvertibleTypes
        return map.equals(o);
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    @Override
    public String toString() {
        return "SolidMap{" +
            "map=" + map +
            '}';
    }
}
