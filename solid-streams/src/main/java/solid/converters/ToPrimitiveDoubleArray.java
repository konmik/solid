package solid.converters;

import java.util.ArrayList;

import solid.functions.Func1;

public class ToPrimitiveDoubleArray {

    private static final Func1<Iterable<Double>, double[]> TO_PRIMITIVE_DOUBLE_ARRAY = new Func1<Iterable<Double>, double[]>() {
        @Override
        public double[] call(Iterable<Double> value) {
            ArrayList<Double> objects = ToArrayList.<Double>toArrayList().call(value);
            double[] primitives = new double[objects.size()];
            int i = 0;
            for (Double object : objects)
                primitives[i++] = object;
            return primitives;
        }
    };

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(Func1)}
     * to convert an iterable stream of {@link Double} type into a primitive double[] array.
     *
     * @return a method that converts an iterable stream of {@link Double} type into a primitive double[] array.
     */
    public static Func1<Iterable<Double>, double[]> toPrimitiveDoubleArray() {
        return TO_PRIMITIVE_DOUBLE_ARRAY;
    }
}
