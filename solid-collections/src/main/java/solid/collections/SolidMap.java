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
 * This is basically a decorator around {@link java.util.LinkedHashMap}.
 *
 * {@link SolidMap} does not extend {@link Map} because of
 * {@link Map} has a higher priority over {@link Parcelable} when putting into
 * {@link Parcel}, see {@link Parcel#writeValue(Object)}.
 *
 * Use {@link SolidMap#asMap()}.
 *
 * @param <K> a type of keys.
 * @param <V> a type of values.
 */
public class SolidMap<K, V> extends Stream<Map.Entry<K, V>> implements Parcelable {

    private static final SolidMap<Object, Object> EMPTY = new SolidMap<>(new LinkedHashMap<>());

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
    public SolidMap(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
        LinkedHashMap<K, V> m = new LinkedHashMap<>();
        for (Map.Entry<? extends K, ? extends V> entry : iterable)
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
        return (SolidMap<K, V>) EMPTY;
    }

    /**
     * Returns an immutable {@link Map} interface.
     */
    public Map<K, V> asMap() {
        return map;
    }

    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return map.containsKey(value);
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    public V get(Object key) {
        return map.get(key);
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Set<K> keySet() {
        return map.keySet();
    }

    public int size() {
        return map.size();
    }

    public Collection<V> values() {
        return map.values();
    }

    private static final ClassLoader CLASS_LOADER = SolidMap.class.getClassLoader();

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SolidMap<?, ?> solidMap = (SolidMap<?, ?>) o;
        return map.equals(solidMap.map);
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
    public Iterator<Map.Entry<K, V>> iterator() {
        return map.entrySet().iterator();
    }
}
