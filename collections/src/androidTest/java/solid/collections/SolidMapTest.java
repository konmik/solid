package solid.collections;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import testkit.ParcelFn;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SolidMapTest {
    @Test
    public void testParcelUnparcel() throws Exception {
        SolidMap map = new SolidMap(create123map234());
        assertSetEquals(map.entrySet(), ((SolidMap) ParcelFn.unmarshall(ParcelFn.marshall(map))).entrySet());
    }

    @Test
    public void testMapConstructor() throws Exception {
        HashMap<Integer, Integer> hashMap = create123map234();

        SolidMap<Integer, Integer> map = new SolidMap<>(hashMap);
        assertEquals((Integer) 2, map.get(1));
        assertEquals((Integer) 3, map.get(2));
        assertEquals((Integer) 4, map.get(3));
        assertEquals(3, map.size());

        hashMap.put(4, 5);
        assertNotEquals(hashMap, map);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAsMapIsImmutable() throws Exception {
        new SolidMap<>(create123map234()).asMap().put(1, 1);
    }

    @Test
    public void testEmpty() throws Exception {
        assertEquals(0, SolidMap.empty().size());
    }

    @Test
    public void testContainsKey() throws Exception {
        SolidMap<Integer, Integer> map = new SolidMap<>(create123map234());
        assertTrue(map.containsKey(1));
        assertFalse(map.containsKey(4));
    }

    @Test
    public void testContainsValue() throws Exception {
        SolidMap<Integer, Integer> map = new SolidMap<>(create123map234());
        assertTrue(map.containsValue(2));
        assertFalse(map.containsValue(5));
    }

    @Test
    public void testEntrySet() throws Exception {
        SolidMap<Integer, Integer> map = new SolidMap<>(create123map234());
        assertSetEquals(create123map234().entrySet(), map.entrySet());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testEntrySetIsUnmodifiable() throws Exception {
        new SolidMap<>(create123map234()).entrySet().clear();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testEntryIsUnmodifiable() throws Exception {
        for (Map.Entry entry : new SolidMap<>(create123map234()).entrySet())
            entry.setValue(1);
    }

    @Test
    public void testGet() throws Exception {
        assertEquals((Integer) 2, new SolidMap<>(create123map234()).get(1));
        assertEquals(null, new SolidMap<>(create123map234()).get(5));
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertTrue(new SolidMap<>(new HashMap<>()).isEmpty());
        assertFalse(new SolidMap<>(create123map234()).isEmpty());
    }

    @Test
    public void testKeySet() throws Exception {
        SolidMap<Integer, Integer> map = new SolidMap<>(create123map234());
        assertSetEquals(create123map234().keySet(), map.keySet());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testKeySetIsUnmodifiable() throws Exception {
        new SolidMap<>(create123map234()).keySet().clear();
    }

    @Test
    public void testSize() throws Exception {
        assertEquals(3, new SolidMap<>(create123map234()).size());
        assertEquals(0, new SolidMap<>(new HashMap<>()).size());
    }

    @Test
    public void testValues() throws Exception {
        assertArrayEquals(new Integer[]{2, 3, 4}, new SolidMap<>(create123map234()).values().toArray());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testValuesIsUnmodifiable() throws Exception {
        new SolidMap<>(create123map234()).values().remove(1);
    }

    @Test
    public void testEquals() throws Exception {
        SolidMap<Integer, Integer> map = new SolidMap<>(create123map234());
        assertTrue(map.equals(map));

        assertTrue(new SolidMap<>(create123map234()).equals(new SolidMap<>(create123map234())));

        HashMap<Integer, Integer> map234null = create123map234();
        map234null.remove(1);
        assertFalse(map.equals(new SolidMap<>(map234null)));

        map234null.put(null, null);
        assertTrue(new SolidMap<>(map234null).equals(new SolidMap<>(map234null)));
        assertFalse(new SolidMap<>(create123map234()).equals(new SolidMap<>(map234null)));

        HashMap<Integer, Integer> map234null_ = create123map234();
        map234null_.put(3, null);
        assertFalse(new SolidMap<>(map234null).equals(create123map234()));

        assertFalse(map.equals(1));
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(SolidMap.empty().hashCode(), SolidMap.empty().hashCode());
        assertEquals(new SolidMap<>(create123map234()).hashCode(), new SolidMap<>(create123map234()).hashCode());
        assertNotEquals(SolidMap.empty().hashCode(), new SolidMap<>(create123map234()).hashCode());
    }

    @Test
    public void testIterator() throws Exception {
        SolidMap<Integer, Integer> map = new SolidMap<>(create123map234());
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : map) {
            hashMap.put(entry.getKey(), entry.getValue());
        }
        assertEquals(map, new SolidMap<>(hashMap));
    }

    @Test
    public void testToString() throws Exception {
        SolidMap<Integer, Integer> map = new SolidMap<>(create123map234());
        assertTrue(map.toString().contains("1"));
        assertTrue(map.toString().contains("4"));
        assertFalse(map.toString().contains("5"));
    }

    private <T> void assertSetEquals(Set<T> expected, Set<T> actual) {
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    private LinkedHashMap<Integer, Integer> create123map234() {
        return new LinkedHashMap<Integer, Integer>() {{
            put(1, 2);
            put(2, 3);
            put(3, 4);
        }};
    }
}
