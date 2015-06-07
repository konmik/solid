package solid.converters;

import java.util.Collection;

import solid.functions.SolidFunc1;
import solid.stream.Copy;

public class ToPrimitiveIntegerArray implements SolidFunc1<Iterable<Integer>, int[]> {

    private static final SolidFunc1<Iterable<Integer>, int[]> TO_PRIMITIVE_INTEGER_ARRAY = new ToPrimitiveIntegerArray();

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(SolidFunc1)}
     * to convert an iterable stream of {@link Integer} type into a primitive int[] array.
     *
     * @return a method that converts an iterable stream of {@link Integer} type into a primitive int[] array.
     */
    public static SolidFunc1<Iterable<Integer>, int[]> toPrimitiveIntegerArray() {
        return TO_PRIMITIVE_INTEGER_ARRAY;
    }

    @Override
    public int[] call(Iterable<Integer> value) {
        Collection<Integer> objects = new Copy<>(value).collect(ToList.<Integer>toList());
        int[] primitives = new int[objects.size()];
        int i = 0;
        for (Integer object : objects)
            primitives[i++] = object;
        return primitives;
    }
}
