package solid.experimental.algorithms;

import java.util.Iterator;

import solid.stream.Stream;

public class ListUpdate<T1> extends Stream<T1> {

    private StreamComparison<T1> comparison;

    public ListUpdate(StreamComparison<T1> comparison) {
        this.comparison = comparison;
    }

    @Override
    public Iterator<T1> iterator() {
        return comparison.both().merge(comparison.second()).iterator();
    }
}
