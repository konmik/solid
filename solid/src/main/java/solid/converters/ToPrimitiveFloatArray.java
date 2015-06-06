package solid.converters;

import java.util.List;

import solid.functions.SolidFunc1;
import solid.stream.Copy;

public class ToPrimitiveFloatArray implements SolidFunc1<Iterable<Float>, float[]> {

    private static final SolidFunc1<Iterable<Float>, float[]> TO_PRIMITIVE_FLOAT_ARRAY = new ToPrimitiveFloatArray();

    public static SolidFunc1<Iterable<Float>, float[]> toPrimitiveFloatArray() {
        return TO_PRIMITIVE_FLOAT_ARRAY;
    }

    @Override
    public float[] call(Iterable<Float> value) {
        List<Float> objects = new Copy<>(value).collect(ToList.<Float>toList());
        float[] primitives = new float[objects.size()];
        int i = 0;
        for (Float object : objects)
            primitives[i++] = object;
        return primitives;
    }
}
