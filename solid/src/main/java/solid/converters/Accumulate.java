package solid.converters;

import solid.functions.SolidFunc1;
import solid.functions.SolidFunc2;

public class Accumulate<T, R> implements SolidFunc1<Iterable<T>, R> {

    private final R initial;
    private final SolidFunc2<R, T, R> accumulator;

    public Accumulate(R initial, SolidFunc2<R, T, R> accumulator) {
        this.initial = initial;
        this.accumulator = accumulator;
    }

    @Override
    public R call(Iterable<T> it) {
        R result = initial;
        for (T value : it)
            result = accumulator.call(result, value);
        return result;
    }
}
