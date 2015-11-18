package solid.converters;

import java.util.List;

import solid.functions.Func1;

public class ToPrimitiveByteArray {

    private static final Func1<Iterable<Byte>, byte[]> TO_PRIMITIVE_BYTE_ARRAY = value -> {
        List<Byte> objects = ToArrayList.<Byte>toArrayList().call(value);
        byte[] primitives = new byte[objects.size()];
        int i = 0;
        for (Byte object : objects)
            primitives[i++] = object;
        return primitives;
    };

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(Func1)}
     * to convert an iterable stream of {@link Byte} type into a primitive byte[] array.
     *
     * @return a method that converts an iterable stream of {@link Byte} type into a primitive byte[] array.
     */
    public static Func1<Iterable<Byte>, byte[]> toPrimitiveByteArray() {
        return TO_PRIMITIVE_BYTE_ARRAY;
    }
}
