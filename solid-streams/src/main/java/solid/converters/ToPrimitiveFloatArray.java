package solid.converters;

import java.util.ArrayList;

import solid.functions.SolidFunc1;

public class ToPrimitiveFloatArray {

    private static final SolidFunc1<Iterable<Float>, float[]> TO_PRIMITIVE_FLOAT_ARRAY = value -> {
        ArrayList<Float> objects = ToArrayList.<Float>toArrayList().call(value);
        float[] primitives = new float[objects.size()];
        int i = 0;
        for (Float object : objects)
            primitives[i++] = object;
        return primitives;
    };

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(SolidFunc1)}
     * to convert an iterable stream of {@link Float} type into a primitive float[] array.
     *
     * @return a method that converts an iterable stream of {@link Float} type into a primitive float[] array.
     */
    public static SolidFunc1<Iterable<Float>, float[]> toPrimitiveFloatArray() {
        return TO_PRIMITIVE_FLOAT_ARRAY;
    }
}
