package solid.converters;

import java.util.Iterator;

import solid.stream.ReadOnlyIterator;
import solid.stream.Stream;

public class Floats extends Stream<Float> {

    private float[] floats;

    /**
     * Creates a new stream of {@link Float} type that contains all items of a given array.
     *
     * @param floats an array to get items from.
     * @return a new stream of {@link Float} type that contains all items of a given array.
     */
    public static Stream<Float> floats(float[] floats) {
        return new Floats(floats);
    }

    public Floats(float[] floats) {
        this.floats = floats;
    }

    @Override
    public Iterator<Float> iterator() {
        return new ReadOnlyIterator<Float>() {

            int i;

            @Override
            public boolean hasNext() {
                return i < floats.length;
            }

            @Override
            public Float next() {
                return floats[i++];
            }
        };
    }
}
