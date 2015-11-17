package solid.stream;

import java.util.Iterator;

import solid.filters.DistinctFilter;

public class MergeDistinct<T> extends Stream<T> {

    private Iterable<T> composed;

    public MergeDistinct(Iterable<? extends T> source, Iterable<? extends T> with) {
        DistinctFilter<T> distinctFilter = new DistinctFilter<>();
        composed = new Merge<>(new Filter<>(source, distinctFilter), (new Filter<>(with, distinctFilter)));
    }

    @Override
    public Iterator<T> iterator() {
        return composed.iterator();
    }
}
