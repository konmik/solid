package solid.experimental.algorithms;

import java.util.Iterator;

import solid.experimental.collections.Pair;
import solid.functions.SolidAction2;
import solid.functions.SolidFunc1;
import solid.stream.Stream;

public class ListUpdateConvert<T1, T2> extends Stream<T1> {

    private StreamComparisonSeparate<T1, T2> comparison;
    private SolidAction2<T1, T2> updater;
    private SolidFunc1<T2, T1> converter;

    public ListUpdateConvert(StreamComparisonSeparate<T1, T2> comparison, SolidFunc1<T2, T1> converter, SolidAction2<T1, T2> updater) {
        this.comparison = comparison;
        this.converter = converter;
        this.updater = updater;
    }

    @Override
    public Iterator<T1> iterator() {
        return comparison.both()
            .map(new SolidFunc1<Pair<T1, T2>, T1>() {
                @Override
                public T1 call(Pair<T1, T2> value) {
                    updater.call(value.value1(), value.value2());
                    return value.value1();
                }
            })
            .merge(comparison.second().map(converter))
            .iterator();
    }
}
