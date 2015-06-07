package solid.experimental.algorithms;

import java.util.ArrayList;
import java.util.List;

import solid.experimental.collections.Pair;
import solid.functions.SolidFunc2;
import solid.stream.Copy;
import solid.stream.Stream;

import static solid.stream.Stream.stream;

/**
 * Same algorithm as {@link StreamComparison} but for a custom comparison and for streams of two different types.
 */
public class StreamComparisonSeparate<T1, T2> {

    private Iterable<T1> source1;
    private Iterable<T2> source2;
    private SolidFunc2<T1, T2, Boolean> comparator;

    private boolean analyzed;
    private List<Pair<T1, T2>> both = new ArrayList<>();
    private List<T1> only1 = new ArrayList<>();
    private List<T2> only2 = new ArrayList<>();

    public StreamComparisonSeparate(Iterable<T1> source1, Iterable<T2> source2) {
        this(source1, source2, new SolidFunc2<T1, T2, Boolean>() {
            @Override
            public Boolean call(T1 value1, T2 value2) {
                return value1 == null ? value2 == null : value1.equals(value2);
            }
        });
    }

    public StreamComparisonSeparate(Iterable<T1> source1, Iterable<T2> source2, SolidFunc2<T1, T2, Boolean> comparator) {
        this.source1 = source1;
        this.source2 = source2;
        this.comparator = comparator;
    }

    public Stream<Pair<T1, T2>> both() {
        analyze();
        return stream(both);
    }

    public Stream<T1> first() {
        analyze();
        return stream(only1);
    }

    public Stream<T2> second() {
        analyze();
        return stream(only2);
    }

    private void analyze() {
        if (!analyzed) {
            only2 = new Copy<>(source2).toList();

            for (T1 o : source1) {
                boolean found = false;
                for (T2 n : only2) {
                    if (comparator.call(o, n)) {
                        both.add(new Pair<>(o, n));
                        only2.remove(n);
                        found = true;
                        break;
                    }
                }
                if (!found)
                    only1.add(o);
            }

            analyzed = true;
        }
    }
}
