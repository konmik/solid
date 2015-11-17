package solid.converters;

import java.util.List;

import solid.functions.SolidFunc1;
import solid.stream.Copy;

public class ToPrimitiveByteArray implements SolidFunc1<Iterable<Byte>, byte[]> {

    private static final SolidFunc1<Iterable<Byte>, byte[]> TO_PRIMITIVE_BYTE_ARRAY = new ToPrimitiveByteArray();

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(SolidFunc1)}
     * to convert an iterable stream of {@link Byte} type into a primitive byte[] array.
     *
     * @return a method that converts an iterable stream of {@link Byte} type into a primitive byte[] array.
     */
    public static SolidFunc1<Iterable<Byte>, byte[]> toPrimitiveByteArray() {
        return TO_PRIMITIVE_BYTE_ARRAY;
    }

    @Override
    public byte[] call(Iterable<Byte> value) {
        List<Byte> objects = new Copy<>(value).collect(ToList.<Byte>toList());
        byte[] primitives = new byte[objects.size()];
        int i = 0;
        for (Byte object : objects)
            primitives[i++] = object;
        return primitives;
    }
}
