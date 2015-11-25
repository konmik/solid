package solid.collectors;

import java.util.Arrays;

import solid.functions.Func1;

public class ToArrays {

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(Func1)}
     * to convert an iterable stream of {@link Number} type into a primitive byte[] array.
     *
     * @return a method that converts an iterable stream of {@link Number} type into a primitive byte[] array.
     */
    public static Func1<Iterable<Byte>, byte[]> toBytes() {
        return new Func1<Iterable<Byte>, byte[]>() {
            @Override
            public byte[] call(Iterable<Byte> value) {
                return new QuickNumberArray(value).toBytes();
            }
        };
    }

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(Func1)}
     * to convert an iterable stream of {@link Number} type into a primitive double[] array.
     *
     * @return a method that converts an iterable stream of {@link Number} type into a primitive double[] array.
     */
    public static Func1<Iterable<Double>, double[]> toDoubles() {
        return new Func1<Iterable<Double>, double[]>() {
            @Override
            public double[] call(Iterable<Double> value) {
                return new QuickNumberArray(value).toDoubles();
            }
        };
    }

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(Func1)}
     * to convert an iterable stream of {@link Number} type into a primitive float[] array.
     *
     * @return a method that converts an iterable stream of {@link Number} type into a primitive float[] array.
     */
    public static Func1<Iterable<Float>, float[]> toFloats() {
        return new Func1<Iterable<Float>, float[]>() {
            @Override
            public float[] call(Iterable<Float> value) {
                return new QuickNumberArray(value).toFloats();
            }
        };
    }

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(Func1)}
     * to convert an iterable stream of {@link Number} type into a primitive int[] array.
     *
     * @return a method that converts an iterable stream of {@link Number} type into a primitive int[] array.
     */
    public static Func1<Iterable<Integer>, int[]> toInts() {
        return new Func1<Iterable<Integer>, int[]>() {
            @Override
            public int[] call(Iterable<Integer> value) {
                return new QuickNumberArray(value).toInts();
            }
        };
    }

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(Func1)}
     * to convert an iterable stream of {@link Number} type into a primitive long[] array.
     *
     * @return a method that converts an iterable stream of {@link Number} type into a primitive long[] array.
     */
    public static Func1<Iterable<Long>, long[]> toLongs() {
        return new Func1<Iterable<Long>, long[]>() {
            @Override
            public long[] call(Iterable<Long> value) {
                return new QuickNumberArray(value).toLongs();
            }
        };
    }

    /**
     * Returns a method that can be used with {@link solid.stream.Stream#collect(Func1)}
     * to convert an iterable stream of {@link Number} type into a primitive short[] array.
     *
     * @return a method that converts an iterable stream of {@link Number} type into a primitive short[] array.
     */
    public static Func1<Iterable<Short>, short[]> toShorts() {
        return new Func1<Iterable<Short>, short[]>() {
            @Override
            public short[] call(Iterable<Short> value) {
                return new QuickNumberArray(value).toShorts();
            }
        };
    }

    private static final Number[] EMPTY_ARRAY = new Number[0];
    private static final int MIN_CAPACITY_INCREMENT = 12;

    private static class QuickNumberArray {

        private Number[] array = EMPTY_ARRAY;
        private int length = 0;

        private QuickNumberArray(Iterable<? extends Number> iterable) {
            for (Number number : iterable) {
                if (length == array.length) {
                    int increment = length >> 1;
                    array = Arrays.copyOf(array, length + (increment < MIN_CAPACITY_INCREMENT ? MIN_CAPACITY_INCREMENT : increment));
                }
                array[length++] = number;
            }
        }

        public byte[] toBytes() {
            byte[] primitives = new byte[length];
            for (int i = 0; i < length; i++)
                primitives[i] = array[i].byteValue();
            return primitives;
        }

        public double[] toDoubles() {
            double[] primitives = new double[length];
            for (int i = 0; i < length; i++)
                primitives[i] = array[i].doubleValue();
            return primitives;
        }

        public float[] toFloats() {
            float[] primitives = new float[length];
            for (int i = 0; i < length; i++)
                primitives[i] = array[i].floatValue();
            return primitives;
        }

        public int[] toInts() {
            int[] primitives = new int[length];
            for (int i = 0; i < length; i++)
                primitives[i] = array[i].intValue();
            return primitives;
        }

        public long[] toLongs() {
            long[] primitives = new long[length];
            for (int i = 0; i < length; i++)
                primitives[i] = array[i].longValue();
            return primitives;
        }

        public short[] toShorts() {
            short[] primitives = new short[length];
            for (int i = 0; i < length; i++)
                primitives[i] = array[i].shortValue();
            return primitives;
        }
    }
}
