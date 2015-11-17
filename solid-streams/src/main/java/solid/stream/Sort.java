package solid.stream;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Sort<T> extends Stream<T> {

    private Iterable<T> source;
    private Comparator<T> comparator;

    public Sort(Iterable<T> source, Comparator<T> comparator) {
        this.source = source;
        this.comparator = comparator;
    }

    @Override
    public Iterator<T> iterator() {
        List<T> array = new Copy<>(source).toList();
        //noinspection unchecked
        Collections.sort(array, comparator);
        return new Copy<>(array).iterator();
    }
}
