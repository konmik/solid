package solid.stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import solid.collections.SolidEntry;
import solid.collections.SolidList;
import solid.functions.SolidFunc1;

/**
 * A converter to convert a stream into a stream of grouped streams.
 *
 * @param <K> a key type
 * @param <V> a stream item type
 */
public class Group<K, V> extends Stream<SolidEntry<K, SolidList<V>>> {

    private final Iterable<? extends V> source;
    private final SolidFunc1<V, K> toKey;

    public Group(Iterable<? extends V> source, SolidFunc1<V, K> toKey) {
        this.source = source;
        this.toKey = toKey;
    }

    @Override
    public Iterator<SolidEntry<K, SolidList<V>>> iterator() {
        List<K> keys = new ArrayList<>();
        final Map<K, List<V>> map = new HashMap<>();
        for (V value : source) {
            K key = toKey.call(value);
            ArrayList<V> list = (ArrayList<V>)map.get(key);
            if (list == null) {
                keys.add(key);
                map.put(key, list = new ArrayList<>());
            }
            list.add(value);
        }
        return new solid.stream.Map<>(keys, new SolidFunc1<K, SolidEntry<K, SolidList<V>>>() {
            @Override
            public SolidEntry<K, SolidList<V>> call(K key) {
                return new SolidEntry<>(key, new SolidList<>(map.get(key)));
            }
        }).iterator();
    }
}
