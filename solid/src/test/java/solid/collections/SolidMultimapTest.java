package solid.collections;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import solid.functions.SolidFunc1;
import test_utils.MockParcel;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class SolidMultimapTest {

    @Test
    public void testOfValues() throws Exception {
        SolidMultimap<Integer, Integer> multimap = SolidMultimap.ofValues(getValues(), new SolidFunc1<Integer, Integer>() {
            @Override
            public Integer call(Integer value) {
                return value < 4 ? 1 : value < 7 ? 2 : 3;
            }
        });
        assert1to9(multimap, true);
    }

    @Test
    public void testOfKeysValues() throws Exception {
        SolidMultimap<Integer, Integer> multimap = SolidMultimap.ofKeysValues(Arrays.asList(1, 2, 3, 4), getValues(), new SolidFunc1<Integer, Integer>() {
            @Override
            public Integer call(Integer value) {
                return value < 4 ? 1 : value < 7 ? 2 : 3;
            }
        });
        assert1to9(multimap, false);
        assertEquals(4, multimap.size());
        assertArrayEquals(new Object[0], multimap.get(3).value2().toArray());
    }

    @Test
    public void testOfPairs() throws Exception {
        SolidMultimap<Integer, Integer> multimap = SolidMultimap.ofPairs(Arrays.asList(
            new Pair<>(1, 1),
            new Pair<>(1, 2),
            new Pair<>(1, 3),
            new Pair<>(2, 4),
            new Pair<>(2, 5),
            new Pair<>(2, 6),
            new Pair<>(3, 7),
            new Pair<>(3, 8),
            new Pair<>(3, 9)
        ));
        assert1to9(multimap, true);
    }

    @Test
    public void testOfMap() throws Exception {
        SolidMultimap<Integer, Integer> multimap = SolidMultimap.ofMap(new HashMap<Integer, Iterable<Integer>>() {{
            put(1, Arrays.asList(1, 2, 3));
            put(2, Arrays.asList(4, 5, 6));
            put(3, Arrays.asList(7, 8, 9));
        }});
        assert1to9(multimap, true);
    }

    @Test
    public void testKeys() throws Exception {
        SolidMultimap<Integer, Integer> multimap = SolidMultimap.ofKeysValues(Arrays.asList(1, 2, 3, 4), getValues(), new SolidFunc1<Integer, Integer>() {
            @Override
            public Integer call(Integer value) {
                return value < 4 ? 1 : value < 7 ? 2 : 3;
            }
        });
        assertArrayEquals(new Integer[]{1, 2, 3, 4}, multimap.keys().toArray());
    }

    @Test
    public void testByKey() throws Exception {
        SolidMultimap<Integer, Integer> multimap = SolidMultimap.ofKeysValues(Arrays.asList(1, 2, 3, 4), getValues(), new SolidFunc1<Integer, Integer>() {
            @Override
            public Integer call(Integer value) {
                return value < 4 ? 1 : value < 7 ? 2 : 3;
            }
        });
        assertArrayEquals(new Integer[]{1, 2, 3}, multimap.byKey(1).toArray());
        assertArrayEquals(new Integer[]{4, 5, 6}, multimap.byKey(2).toArray());
        assertArrayEquals(new Integer[]{7, 8, 9}, multimap.byKey(3).toArray());
    }

    @Test
    public void testParcelable() throws Exception {
        SolidMultimap<Integer, Integer> multimap = create19();
        assertEquals(multimap, MockParcel.writeRead(multimap, SolidMultimap.CREATOR));
    }

    private SolidMultimap<Integer, Integer> create19() {
        return SolidMultimap.ofValues(getValues(), new SolidFunc1<Integer, Integer>() {
            @Override
            public Integer call(Integer value) {
                return value < 4 ? 1 : value < 7 ? 2 : 3;
            }
        });
    }

    private List<Integer> getValues() {
        return Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    private void assert1to9(SolidMultimap<Integer, Integer> multimap, boolean size) {
        assertEquals(1, (int)multimap.get(0).value1());
        assertArrayEquals(new Object[]{1, 2, 3}, multimap.get(0).value2().toArray());

        assertEquals(2, (int)multimap.get(1).value1());
        assertArrayEquals(new Object[]{4, 5, 6}, multimap.get(1).value2().toArray());

        assertEquals(3, (int)multimap.get(2).value1());
        assertArrayEquals(new Object[]{7, 8, 9}, multimap.get(2).value2().toArray());

        if (size)
            assertEquals(3, multimap.size());
    }
}