package solid.collectors;

import java.util.Map;

import solid.collections.Pair;
import solid.collections.SolidMap;
import solid.functions.Func1;

import static solid.stream.Stream.stream;

public class ToSolidMap {

    /**
     * Returns a function that converts a stream into {@link SolidMap} using given key and value extractor methods.
     */
    public static <T, K, V> Func1<Iterable<T>, SolidMap<K, V>> toSolidMap(final Func1<T, K> keyExtractor, final Func1<T, V> valueExtractor) {
        return new Func1<Iterable<T>, SolidMap<K, V>>() {
            @Override
            public SolidMap<K, V> call(final Iterable<T> iterable) {
                return new SolidMap<>(
                    stream(iterable).map(new Func1<T, Pair<K, V>>() {
                        @Override
                        public Pair<K, V> call(T value) {
                            return new Pair<>(keyExtractor.call(value), valueExtractor.call(value));
                        }
                    }));
            }
        };
    }

    /**
     * Returns a function that converts a stream into {@link SolidMap} using a given key extractor method.
     */
    public static <T, K> Func1<Iterable<T>, SolidMap<K, T>> toSolidMap(final Func1<T, K> keyExtractor) {
        return toSolidMap(keyExtractor, new Func1<T, T>() {
            @Override
            public T call(T value) {
                return value;
            }
        });
    }

    /**
     * Returns a function that converts a stream of {@link java.util.Map.Entry} into {@link SolidMap}.
     */
    public static <K, V> Func1<Iterable<Map.Entry<K, V>>, SolidMap<K, V>> toSolidMap() {
        return new Func1<Iterable<Map.Entry<K, V>>, SolidMap<K, V>>() {
            @Override
            public SolidMap<K, V> call(Iterable<Map.Entry<K, V>> iterable) {
                return new SolidMap<>(stream(iterable).map(new Func1<Map.Entry<K, V>, Pair<K, V>>() {
                    @Override
                    public Pair<K, V> call(Map.Entry<K, V> value) {
                        return new Pair<>(value.getKey(), value.getValue());
                    }
                }));
            }
        };
    }

    /**
     * Returns a function that converts a stream of {@link Pair} into {@link SolidMap}.
     */
    public static <K, V> Func1<Iterable<Pair<K, V>>, SolidMap<K, V>> pairsToSolidMap() {
        return new Func1<Iterable<Pair<K, V>>, SolidMap<K, V>>() {
            @Override
            public SolidMap<K, V> call(Iterable<Pair<K, V>> iterable) {
                return new SolidMap<>(iterable);
            }
        };
    }
}
