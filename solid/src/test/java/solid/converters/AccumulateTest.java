package solid.converters;

import org.junit.Test;

import java.util.Arrays;

import solid.functions.SolidFunc2;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

public class AccumulateTest {

    @Test
    public void test_just_works() throws Exception {
        assertEquals((Integer) 109, new Accumulate<>(100, new SolidFunc2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer value1, Integer value2) {
                return value1 + value2;
            }
        }).call(asList(2, 3, 4)));
    }

    @Test
    public void test_null() throws Exception {
        assertEquals(null, new Accumulate<>(null, new SolidFunc2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer value1, Integer value2) {
                return null;
            }
        }).call(Arrays.<Integer>asList(null, null, null)));
    }

    @Test
    public void test_empty() throws Exception {
        assertEquals(null, new Accumulate<>(null, null).call(emptyList()));
    }

    public void all() throws Exception {
        test_just_works();
        test_null();
        test_empty();
    }
}