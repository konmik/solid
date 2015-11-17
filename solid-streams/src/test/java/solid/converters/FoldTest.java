package solid.converters;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import solid.functions.SolidFunc2;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class FoldTest {
    @Test
    public void test_simple() throws Exception {
        assertEquals((Integer)10, new Fold<Integer, Integer>(1, (value1, value2) -> value1 + value2).call(asList(2, 3, 4)));
    }

    @Test
    public void test_nulls() throws Exception {
        assertEquals(null, new Fold<Integer, Integer>(null, (value1, value2) -> null).call(Arrays.<Integer>asList(null, null)));
    }

    @Test
    public void test_empty() throws Exception {
        assertEquals(null, new Fold<Integer, Integer>(null, null).call(Collections.<Integer>emptyList()));
    }

    public void all() throws Exception {
        test_empty();
        test_nulls();
        test_simple();
    }
}