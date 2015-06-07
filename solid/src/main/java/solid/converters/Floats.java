package solid.converters;

import java.util.Iterator;

import solid.stream.ReadOnlyIterator;
import solid.stream.Stream;

public class Floats extends Stream<Float> {

    private float[] floats;

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
