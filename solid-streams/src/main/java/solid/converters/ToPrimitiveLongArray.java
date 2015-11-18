package solid.converters;

import java.util.ArrayList;

import solid.functions.Func1;

public class ToPrimitiveLongArray {

    private static final Func1<Iterable<Long>, long[]> TO_PRIMITIVE_BYTE_ARRAY = value -> {
        ArrayList<Long> objects = ToArrayList.<Long>toArrayList().call(value);
        long[] primitives = new long[objects.size()];
        int i = 0;
        for (Long object : objects)
            primitives[i++] = object;
        return primitives;
    };

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(Func1)}
     * to convert an iterable stream of {@link Long} type into a primitive long[] array.
     *
     * @return a method that converts an iterable stream of {@link Long} type into a primitive long[] array.
     */
    public static Func1<Iterable<Long>, long[]> toPrimitiveLongArray() {
        return TO_PRIMITIVE_BYTE_ARRAY;
    }
}
