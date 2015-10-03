package solid.converters;

import java.util.LinkedHashMap;

import solid.collections.SolidMap;
import solid.functions.SolidFunc1;

/**
 * A converter to convert a stream into {@link SolidMap} using a key extraction function.
 *
 * @param <K> a key type
 * @param <V> a stream item type
 */
public class ToSolidMapByKey<K, V> implements SolidFunc1<Iterable<V>, SolidMap<K, V>> {

    private final SolidFunc1<V, K> toKey;

    public static <K, V> ToSolidMapByKey<K, V> toSolidMapByKey(SolidFunc1<V, K> toKey) {
        return new ToSolidMapByKey<>(toKey);
    }

    public ToSolidMapByKey(SolidFunc1<V, K> toKey) {
        this.toKey = toKey;
    }

    @Override
    public SolidMap<K, V> call(Iterable<V> iterable) {
        LinkedHashMap<K, V> map = new LinkedHashMap<>();
        for (V value : iterable)
            map.put(toKey.call(value), value);
        return new SolidMap<>(map);
    }
}
