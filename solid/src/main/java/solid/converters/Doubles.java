package solid.converters;

import java.util.Iterator;

import solid.stream.ReadOnlyIterator;
import solid.stream.Stream;

public class Doubles extends Stream<Double> {

    private double[] doubles;

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
