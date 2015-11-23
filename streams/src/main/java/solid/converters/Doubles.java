package solid.converters;

import java.util.Iterator;

import solid.stream.ReadOnlyIterator;
import solid.stream.Stream;

public class Doubles extends Stream<Double> {

    private double[] doubles;

    /**
     * Creates a new stream of {@link Double} type that contains all items of a given array.
     *
     * @param doubles an array to get items from.
     * @return a new stream of {@link Double} type that contains all items of a given array.
     */
    public static Stream<Double> doubles(double[] doubles) {
        return new Doubles(doubles);
    }

    public Doubles(double[] doubles) {
        this.doubles = doubles;
    }

    @Override
    public Iterator<Double> iterator() {
        return new ReadOnlyIterator<Double>() {

            int i;

            @Override
            public boolean hasNext() {
                return i < doubles.length;
            }

            @Override
            public Double next() {
                return doubles[i++];
            }
        };
    }
}
