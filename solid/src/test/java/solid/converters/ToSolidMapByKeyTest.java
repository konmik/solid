package solid.converters;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;

import solid.collections.SolidMap;
import solid.functions.SolidFunc1;

import static java.util.Arrays.asList;
import static test_utils.AssertIterableEquals.assertIterableEquals;

public class ToSolidMapByKeyTest {
    @Test
    public void works() throws Exception {
        assertIterableEquals(
            makeMap(new String[]{"1"}, new Integer[]{1}),
            new ToSolidMapByKey<>(new SolidFunc1<Integer, String>() {
                @Override
                public String call(Integer value) {
                    return value.toString();
                }
            }).call(asList(1)));
    }

    @Test
    public void with_nulls() throws Exception {
        assertIterableEquals(
            makeMap(new String[]{null}, new Integer[]{null}),
            new ToSolidMapByKey<>(new SolidFunc1<Integer, String>() {
                @Override
                public String call(Integer value) {
                    return null;
                }
            }).call(asList((Integer)null)));
    }

    @Test
    public void with_no_items() throws Exception {
        assertIterableEquals(
            makeMap(new String[]{}, new Integer[]{}),
            new ToSolidMapByKey<>(new SolidFunc1<Integer, String>() {
                @Override
                public String call(Integer value) {
                    return value.toString();
                }
            }).call(Arrays.<Integer>asList()));
    }

    @Test
    public void with_tens() throws Exception {
        assertIterableEquals(
            makeMap(new String[]{"1", "0"}, new Integer[]{16, 3}),
            new ToSolidMapByKey<>(new SolidFunc1<Integer, String>() {
                @Override
                public String call(Integer value) {
                    return Integer.valueOf(value / 10).toString();
                }
            }).call(asList(14, 1, 2, 15, 16, 3)));
    }

    @Test
    public void testStatic() throws Exception {
        assertIterableEquals(
            makeMap(new String[]{"1"}, new Integer[]{1}),
            ToSolidMapByKey.toSolidMapByKey(new SolidFunc1<Integer, String>() {
                @Override
                public String call(Integer value) {
                    return value.toString();
                }
            }).call(asList(1)));
    }

    public void all() throws Exception {
        works();
        with_nulls();
        with_no_items();
        with_tens();
    }

    public static <K, V> SolidMap<K, V> makeMap(K[] keys, V[] values) {
        LinkedHashMap<K, V> map = new LinkedHashMap<>();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }
        return new SolidMap<>(map);
    }
}