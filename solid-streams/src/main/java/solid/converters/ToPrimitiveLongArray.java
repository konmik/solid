package solid.converters;

import java.util.Collection;

import solid.functions.SolidFunc1;
import solid.stream.Copy;

public class ToPrimitiveLongArray implements SolidFunc1<Iterable<Long>, long[]> {

    private static final SolidFunc1<Iterable<Long>, long[]> TO_PRIMITIVE_BYTE_ARRAY = new ToPrimitiveLongArray();

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(SolidFunc1)}
     * to convert an iterable stream of {@link Long} type into a primitive long[] array.
     *
     * @return a method that converts an iterable stream of {@link Long} type into a primitive long[] array.
     */
    public static SolidFunc1<Iterable<Long>, long[]> toPrimitiveLongArray() {
        return TO_PRIMITIVE_BYTE_ARRAY;
    }

    @Override
    public long[] call(Iterable<Long> value) {
        Collection<Long> objects = new Copy<>(value).collect(ToList.<Long>toList());
        long[] primitives = new long[objects.size()];
        int i = 0;
        for (Long object : objects)
            primitives[i++] = object;
        return primitives;
    }
}
