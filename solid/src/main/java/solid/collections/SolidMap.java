package solid.collections;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import solid.stream.Stream;

/**
 * Represents an immutable parcelable map.
 * This is basically a decorator around Hashmap.
 *
 * @param <K> a type of keys.
 * @param <V> a type of values.
 */
public class SolidMap<K, V> extends Stream<Map.Entry<K, V>> implements Map<K, V>, Parcelable {

    private static final SolidMap<Object, Object> EMPTY = new SolidMap<>(new LinkedHashMap<>());
    private static final ClassLoader CLASS_LOADER = SolidMap.class.getClassLoader();

    private final Map<K, V> map;

    /**
     * Constructs a SolidMap from a given map.
     *
     * @param map a source map.
     */
    public SolidMap(Map<K, V> map) {
        this.map = Collections.unmodifiableMap(new LinkedHashMap<>(map));
    }

    /**
     * Constructs a SolidMap from a given iterable stream of entries.
     *
     * @param iterable a source iterable.
     */
    public SolidMap(Iterable<? extends Map.Entry<K, V>> iterable) {
        LinkedHashMap<K, V> m = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : iterable)
            m.put(entry.getKey(), entry.getValue());
        this.map = Collections.unmodifiableMap(m);
    }

    /**
     * Returns an empty {@link SolidMap}.
     *
     * @param <K> a type of keys.
     * @param <V> a type of values.
     * @return an empty {@link SolidMap}.
     */
    public static <K, V> SolidMap<K, V> empty() {
        //noinspection unchecked
        return (SolidMap<K, V>)EMPTY;
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
        return map.entrySet();
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
        return map.keySet();
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
        return map.values();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public SolidMap(Parcel in) {
        LinkedHashMap<K, V> temp = new LinkedHashMap<>();
        in.readMap(temp, CLASS_LOADER);
        //noinspection unchecked
        map = Collections.unmodifiableMap(temp);
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

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object instanceof Map) {
            Map<?, ?> other = (Map<?, ?>)object;
            if (size() != other.size())
                return false;

            for (Entry<K, V> entry : entrySet()) {
                K key = entry.getKey();
                V value = entry.getValue();
                Object value2 = other.get(key);
                if (value == null) {
                    if (value2 != null || !other.containsKey(key))
                        return false;
                }
                else if (!value.equals(value2))
                    return false;
            }
            return true;
        }
        return false;
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

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return map.entrySet().iterator();
    }
}
