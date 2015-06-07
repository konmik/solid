package solid.experimental.collections;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import solid.collections.SolidList;
import solid.functions.SolidFunc1;

/**
 * Represents an immutable parcelable ordered multimap.
 * This class mostly acts as a shortcut for SolidList<Pair<K, SolidList<V>>>.
 *
 * @param <K> a type of key
 * @param <V> a type of value
 */
public class SolidMultimap<K, V> extends SolidList<SolidMultimap.Entry<K, V>> {

    private SolidList<K> keys;

    /**
     * Constructs a {@link SolidMultimap} from given items.
     *
     * @param iterable a source of items.
     */
    public SolidMultimap(Iterable<Entry<K, V>> iterable) {
        super(iterable);
        createKeys();
    }

    /**
     * Constructs a {@link SolidMultimap} from given values, extracting keys for each value with given method.
     * The order of items is not guaranteed during construction.
     *
     * @param values       a source of values.
     * @param keyExtractor a method for keys extraction.
     * @param <K>          a type of keys.
     * @param <V>          a type of values.
     * @return a multimap that is constructed of given values.
     */
    public static <K, V> SolidMultimap<K, V> ofValues(Iterable<V> values, SolidFunc1<V, K> keyExtractor) {
        return ofMap(groupChildren(values, keyExtractor));
    }

    /**
     * Constructs a {@link SolidMultimap} from given values, extracting keys for each value with given method.
     * The order of items is not guaranteed during construction.
     * <p/>
     * Includes all groups even though they nave no children.
     *
     * @param groups       a source of groups without children.
     * @param values       a source of values.
     * @param keyExtractor a method for keys extraction.
     * @param <K>          a type of keys.
     * @param <V>          a type of values.
     * @return a multimap that is constructed of given values and groups.
     */
    public static <K, V> SolidMultimap<K, V> ofKeysValues(Iterable<K> groups, Iterable<V> values, SolidFunc1<V, K> keyExtractor) {
        HashMap<K, ArrayList<V>> map = groupChildren(values, keyExtractor);
        for (K g : groups) {
            if (map.get(g) == null)
                map.put(g, new ArrayList<V>());
        }
        return ofMap(map);
    }

    /**
     * Constructs a {@link SolidMultimap} from given key-value pairs.
     * The order of items is not guaranteed during construction.
     *
     * @param pairs a source of pairs.
     * @param <K>   a type of keys.
     * @param <V>   a type of values.
     * @return a multimap that is constructed of given keys and values.
     */
    public static <K, V> SolidMultimap<K, V> ofPairs(Iterable<Pair<K, V>> pairs) {
        HashMap<K, Collection<V>> map = new HashMap<>();
        for (Pair<K, V> v : pairs) {
            K key = v.value1();
            Collection<V> list = map.get(key);
            if (list == null)
                map.put(key, list = new ArrayList<>());
            list.add(v.value2());
        }
        return ofMap(map);
    }

    /**
     * Constructs a {@link SolidMultimap} from a given map of key-Iterable(value) entries.
     * The order of items is not guaranteed during construction.
     *
     * @param map a source of data
     * @param <K> a type of keys.
     * @param <V> a type of values.
     * @param <I> a type of value iterator.
     * @return a multimap that is constructed of a given map.
     */
    public static <K, V, I extends Iterable<V>> SolidMultimap<K, V> ofMap(Map<K, I> map) {
        ArrayList<Entry<K, V>> builder = new ArrayList<>(map.size());
        for (Map.Entry<K, I> entry : map.entrySet())
            builder.add(new Entry<>(entry.getKey(), new SolidList<>(entry.getValue())));
        return new SolidMultimap<>(builder);
    }

    private static <K, V> HashMap<K, ArrayList<V>> groupChildren(Iterable<V> value, SolidFunc1<V, K> keyExtractor) {
        HashMap<K, ArrayList<V>> map = new HashMap<>();
        for (V v : value) {
            K key = keyExtractor.call(v);
            ArrayList<V> list = map.get(key);
            if (list == null)
                map.put(key, list = new ArrayList<>());
            list.add(v);
        }
        return map;
    }

    // this class is only a shortcut for Pair<K, SolidList<V>>
    public static class Entry<K, V> extends Pair<K, SolidList<V>> {

        public Entry(K key, SolidList<V> values) {
            super(key, values);
            if (key == null || values == null)
                throw new NullPointerException("Nulls are not allowed");
        }

        @Override
        public int describeContents() {
            return 0;
        }

        Entry(Parcel in) {
            super(in);
        }

        public static final Creator<Entry> CREATOR = new Creator<Entry>() {
            public Entry createFromParcel(Parcel in) {
                return new Entry(in);
            }

            public Entry[] newArray(int size) {
                return new Entry[size];
            }
        };
    }

    protected SolidMultimap(Parcel in) {
        super(in);
        createKeys();
    }

    public SolidList<K> keys() {
        return keys;
    }

    public SolidList<V> byKey(K key) {
        int index = keys.indexOf(key);
        return index < 0 ? SolidList.<V>empty() : get(index).value2();
    }

    private void createKeys() {
        ArrayList<K> k = new ArrayList<>(size());
        for (SolidMultimap.Entry<K, V> entry : this)
            k.add(entry.value1());
        keys = new SolidList<>(k);
    }

    public static final Creator<SolidMultimap> CREATOR = new Creator<SolidMultimap>() {
        public SolidMultimap createFromParcel(Parcel in) {
            return new SolidMultimap(in);
        }

        public SolidMultimap[] newArray(int size) {
            return new SolidMultimap[size];
        }
    };
}
