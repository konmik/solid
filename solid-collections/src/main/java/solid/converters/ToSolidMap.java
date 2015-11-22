package solid.converters;

import android.util.Pair;

import java.util.Iterator;
import java.util.Map;

import solid.collections.SolidEntry;
import solid.collections.SolidMap;
import solid.functions.Func0;
import solid.functions.Func1;
import solid.stream.ReadOnlyIterator;
import solid.stream.Stream;

import static solid.stream.Stream.stream;

public class ToSolidMap {

    public static <T, K, V> Func1<Iterable<T>, SolidMap<K, V>> toSolidMap(final Func1<T, K> keyExtractor, final Func1<T, V> valueExtractor) {
        return new Func1<Iterable<T>, SolidMap<K, V>>() {
            @Override
            public SolidMap<K, V> call(final Iterable<T> iterable) {
                return new SolidMap<K, V>(Stream.from(new Func0<Iterator<Map.Entry<K, V>>>() {
                    @Override
                    public Iterator<Map.Entry<K, V>> call() {
                        return new ReadOnlyIterator<Map.Entry<K, V>>() {

                            Iterator<T> iterator = iterable.iterator();

                            @Override
                            public boolean hasNext() {
                                return iterator.hasNext();
                            }

                            @Override
                            public Map.Entry<K, V> next() {
                                T next = iterator.next();
                                return new SolidEntry<>(keyExtractor.call(next), valueExtractor.call(next));
                            }
                        };
                    }
                }));
            }
        };
    }

    public static <K, V> Func1<Iterable<Map.Entry<K, V>>, SolidMap<K, V>> toSolidMap() {
        return new Func1<Iterable<Map.Entry<K, V>>, SolidMap<K, V>>() {
            @Override
            public SolidMap<K, V> call(Iterable<Map.Entry<K, V>> iterable) {return new SolidMap<K, V>(iterable);}
        };
    }

//    public static <K, V> Func1<Iterable<Pair<K, V>>, SolidMap<K, V>> fromPairs() {
//        return iterable -> new SolidMap<>(
//            stream(iterable).<SolidEntry<K, V>>map(pair ->
//                new SolidEntry<>(pair.first, pair.second)));
//    }
}
