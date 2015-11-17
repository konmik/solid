package solid.converters;

import java.util.Iterator;

import solid.functions.SolidFunc1;
import solid.functions.SolidFunc2;

public class Reduce<T> implements SolidFunc1<Iterable<T>, T> {

    private final SolidFunc2<T, T, T> operation;

    public Reduce(SolidFunc2<T, T, T> operation) {
        this.operation = operation;
    }

    @Override
    public T call(Iterable<T> it) {
        Iterator<T> iterator = it.iterator();
        if (!iterator.hasNext())
            throw new UnsupportedOperationException("Can't reduce an empty iterable");

        T result = iterator.next();
        while (iterator.hasNext())
            result = operation.call(result, iterator.next());
        return result;
    }
}
