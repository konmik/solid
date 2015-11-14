package solid.converters;

import java.util.Map;

import solid.collections.SolidMap;
import solid.functions.SolidFunc1;

/**
 * This collector does not work and will be removed on the next major release.
 *
 * If you're using retrolambda you can collect a stream of
 * {@link java.util.Map.Entry} into a
 * {@link SolidMap} like this:
 *
 * <pre><code>stream.collect(SolidMap::new)</code></pre>
 */
@Deprecated
public class ToSolidMap<K, V> implements SolidFunc1<Iterable<? extends Map.Entry<K, V>>, SolidMap<K, V>> {

    private static final ToSolidMap TO_SOLID_MAP = new ToSolidMap<>();

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(SolidFunc1)}
     * to convert a stream of {@link Map.Entry} into a {@link SolidMap}.
     *
     * @param <K> a type of {@link SolidMap} keys.
     * @param <V> a type of {@link SolidMap} values.
     * @return a method that converts an iterable into a {@link SolidMap}.
     */
    public static <K, V> SolidFunc1<Iterable<? extends Map.Entry<K, V>>, SolidMap<K, V>> toSolidMap() {
        //noinspection unchecked
        return TO_SOLID_MAP;
    }

    @Override
    public SolidMap<K, V> call(Iterable<? extends Map.Entry<K, V>> value) {
        return new SolidMap<>(value);
    }
}
