package solid.converters;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import solid.functions.SolidFunc2;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReduceTest {
    @Test
    public void test_works() throws Exception {
        assertEquals((Integer)9, new Reduce<>(new SolidFunc2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer value1, Integer value2) {
                return value1 + value2;
            }
        }).call(asList(2, 3, 4)));
    }

    @Test
    public void test_null() throws Exception {
        assertEquals(null, new Reduce<>(new SolidFunc2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer value1, Integer value2) {
                return null;
            }
        }).call(Arrays.<Integer>asList(null, null, null)));
    }

    @Test
    public void test_one_item() throws Exception {
        assertEquals((Integer)9, new Reduce<Integer>(null).call(asList(9)));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_empty() throws Exception {
        new Reduce<>(null).call(Collections.emptyList());
    }

    public void all() throws Exception {

        boolean thrown = false;
        try {
            test_empty();
        }
        catch (UnsupportedOperationException e) {
            thrown = true;
        }
        assertTrue(thrown);

        test_one_item();
        test_null();
        test_works();
    }
}