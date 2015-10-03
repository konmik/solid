package solid.stream;

import org.junit.Test;

import java.util.Collections;

import solid.collections.SolidEntry;
import solid.collections.SolidList;
import solid.functions.SolidFunc1;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static test_utils.AssertIterableEquals.assertIterableEquals;

public class GroupTest {
    @Test
    public void group_works() throws Exception {
        assertIterableEquals(
            asList(new SolidEntry<>("1", SolidList.copyOf(asList(1)))),
            new Group<>(singletonList(1), new SolidFunc1<Integer, String>() {
                @Override
                public String call(Integer value) {
                    return value.toString();
                }
            }));
    }

    @Test
    public void group_nulls() throws Exception {
        assertIterableEquals(
            asList(new SolidEntry<String, SolidList<Integer>>(null, SolidList.copyOf(asList((Integer)null)))),
            new Group<>(singletonList((Integer)null), new SolidFunc1<Integer, String>() {
                @Override
                public String call(Integer value) {
                    return null;
                }
            }));
    }

    @Test
    public void group_work_no_items() throws Exception {
        assertIterableEquals(
            Collections.<SolidEntry<String, SolidList<Integer>>>emptyList(),
            new Group<>(Collections.<Integer>emptyList(), new SolidFunc1<Integer, String>() {
                @Override
                public String call(Integer value) {
                    return value.toString();
                }
            }));
    }

    @Test
    public void group_works_tens() throws Exception {
        assertIterableEquals(
            asList(
                new SolidEntry<>("1", SolidList.copyOf(asList(14, 15, 16))),
                new SolidEntry<>("0", SolidList.copyOf(asList(1, 2, 3)))),
            new Group<>(asList(14, 1, 2, 15, 16, 3), new SolidFunc1<Integer, String>() {
                @Override
                public String call(Integer value) {
                    return Integer.valueOf(value / 10).toString();
                }
            }));
    }
}