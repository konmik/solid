package solid.collectors;

import java.lang.reflect.Array;
import java.util.ArrayList;

import solid.functions.Func1;

public class ToArray {

    public static <T> Func1<Iterable<T>, T[]> toArray(final Class<T> cls) {
        return new Func1<Iterable<T>, T[]>() {
            @Override
            public T[] call(Iterable<T> value) {
                ArrayList<T> list = ToArrayList.<T>toArrayList().call(value);
                return list.toArray((T[]) Array.newInstance(cls, list.size()));
            }
        };
    }
}
