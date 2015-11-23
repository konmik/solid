package solid.converters;

import java.util.Map;

import solid.collections.SolidEntry;
import solid.collections.SolidMap;
import solid.collections.SolidPair;
import solid.functions.Func1;
import solid.stream.Stream;

public class ToSolidMap {

    public static <T, K, V> Func1<Iterable<T>, SolidMap<K, V>> toSolidMap(final Func1<T, K> keyExtractor, final Func1<T, V> valueExtractor) {
        return new Func1<Iterable<T>, SolidMap<K, V>>() {
            @Override
            public SolidMap<K, V> call(final Iterable<T> iterable) {
                return new SolidMap<>(
                    Stream.stream(iterable).map(new Func1<T, Map.Entry<K, V>>() {
                        @Override
                        public Map.Entry<K, V> call(T value) {
                            return new SolidEntry<>(keyExtractor.call(value), valueExtractor.call(value));
                        }
                    }));
            }
        };
    }

    public static <K, V> Func1<Iterable<Map.Entry<K, V>>, SolidMap<K, V>> toSolidMap() {
        return new Func1<Iterable<Map.Entry<K, V>>, SolidMap<K, V>>() {
            @Override
            public SolidMap<K, V> call(Iterable<Map.Entry<K, V>> iterable) {return new SolidMap<>(iterable);}
        };
    }

    public static <K, V> Func1<Iterable<SolidPair<K, V>>, SolidMap<K, V>> fromPairs() {
        return new Func1<Iterable<SolidPair<K, V>>, SolidMap<K, V>>() {
            @Override
            public SolidMap<K, V> call(Iterable<SolidPair<K, V>> iterable) {
                return new SolidMap<>(
                    Stream.stream(iterable).map(new Func1<SolidPair<K, V>, SolidEntry<K, V>>() {
                        @Override
                        public SolidEntry<K, V> call(SolidPair<K, V> pair) {return new SolidEntry<>(pair.first, pair.second);}
                    }));
            }
        };
    }
}
