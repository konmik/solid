package solid.converters;

import solid.functions.SolidFunc1;
import solid.functions.SolidFunc2;

public class Fold<T, R> implements SolidFunc1<Iterable<T>, R> {

    private final SolidFunc2<R, T, R> operation;
    private final R initial;

    public Fold(R initial, SolidFunc2<R, T, R> operation) {
        this.operation = operation;
        this.initial = initial;
    }

    @Override
    public R call(Iterable<T> it) {
        R result = initial;
        for (T anIt : it)
            result = operation.call(result, anIt);
        return result;
    }
}
