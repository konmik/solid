package solid.converters;

import java.util.ArrayList;

import solid.functions.Func1;

public class ToPrimitiveFloatArray {

    private static final Func1<Iterable<Float>, float[]> TO_PRIMITIVE_FLOAT_ARRAY = new Func1<Iterable<Float>, float[]>() {
        @Override
        public float[] call(Iterable<Float> value) {
            ArrayList<Float> objects = ToArrayList.<Float>toArrayList().call(value);
            float[] primitives = new float[objects.size()];
            int i = 0;
            for (Float object : objects)
                primitives[i++] = object;
            return primitives;
        }
    };

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(Func1)}
     * to convert an iterable stream of {@link Float} type into a primitive float[] array.
     *
     * @return a method that converts an iterable stream of {@link Float} type into a primitive float[] array.
     */
    public static Func1<Iterable<Float>, float[]> toPrimitiveFloatArray() {
        return TO_PRIMITIVE_FLOAT_ARRAY;
    }
}
