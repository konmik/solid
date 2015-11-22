package solid.collections;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import solid.functions.Func1;
import testkit.ParcelFn;

import static org.junit.Assert.assertEquals;
import static solid.stream.Stream.of;

@RunWith(AndroidJUnit4.class)
public class SolidListTest {
    @Test
    public void testParcelable() throws Exception {
        SolidList<Integer> list = of(1, 2, 3).collect((solid.functions.Func1<Iterable<Integer>, SolidList<Integer>>) new Func1<Iterable<Integer>, SolidList<Integer>>() {
            @Override
            public SolidList<Integer> call(Iterable<Integer> iterable) {
                return new SolidList<Integer>(iterable);
            }
        });
        assertEquals(list, ParcelFn.unmarshall(ParcelFn.marshall(list)));
    }
}