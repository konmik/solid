package solid.stream;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Reverse<T> extends Stream<T> {

    private Iterable<T> source;

    public Reverse(Iterable<T> source) {
        this.source = source;
    }

    @Override
    public Iterator<T> iterator() {
        List<T> array = new Copy<>(source).toList();
        Collections.reverse(array);
        return new Copy<>(array).iterator();
    }
}
