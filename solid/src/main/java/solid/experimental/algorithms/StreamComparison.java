package solid.experimental.algorithms;

import java.util.ArrayList;
import java.util.List;

import solid.stream.Copy;
import solid.stream.Stream;

import static solid.stream.Stream.stream;

/**
 * Compares two streams and exposes streams for items that exist:
 * a) in both streams,
 * b) only in the first stream,
 * c) only in the second stream.
 * <p/>
 * This algorithm is vital when it comes to updating a list with data from another list.
 */
public class StreamComparison<T1> {

    private Iterable<T1> source1;
    private Iterable<T1> source2;

    private boolean done;
    private List<T1> both = new ArrayList<>();
    private List<T1> only1 = new ArrayList<>();
    private List<T1> only2 = new ArrayList<>();

    public StreamComparison(Iterable<T1> source1, Iterable<T1> source2) {
        this.source1 = source1;
        this.source2 = source2;
    }

    public Stream<T1> both() {
        analyze();
        return stream(both);
    }

    public Stream<T1> first() {
        analyze();
        return stream(only1);
    }

    public Stream<T1> second() {
        analyze();
        return stream(only2);
    }

    private void analyze() {
        if (!done) {
            only2 = new Copy<>(source2).toList();

            for (T1 o : source1) {
                boolean found = false;
                for (T1 n : only2) {
                    if (o == null ? n == null : o.equals(n)) {
                        both.add(o);
                        only2.remove(n);
                        found = true;
                        break;
                    }
                }
                if (!found)
                    only1.add(o);
            }

            done = true;
        }
    }
}
