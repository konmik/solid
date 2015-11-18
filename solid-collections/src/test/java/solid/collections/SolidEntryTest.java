package solid.collections;

import org.junit.Test;

import java.util.HashMap;

import test_utils.MockParcel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class SolidEntryTest {
    @Test
    public void testConstructor1() throws Exception {
        SolidEntry<String, Integer> entry = new SolidEntry<>("1", 1);
        assertEquals("1", entry.getKey());
        assertEquals((Integer) 1, entry.getValue());
    }

    @Test
    public void testConstructor2() throws Exception {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("1", 1);
        SolidEntry<String, Integer> entry = new SolidEntry<>(map.entrySet().iterator().next());
        assertEquals("1", entry.getKey());
        assertEquals((Integer) 1, entry.getValue());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetThrows() throws Exception {
        SolidEntry<String, Integer> entry = new SolidEntry<>("1", 1);
        entry.setValue(2);
    }

    @Test
    public void testParcelable() throws Exception {
        SolidEntry<String, Integer> entry = new SolidEntry<>("1", 1);
        assertEquals(entry, MockParcel.writeRead(entry, SolidEntry.CREATOR));
        assertEquals(12, SolidEntry.CREATOR.newArray(12).length);
        assertEquals(0, entry.describeContents());
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(new SolidEntry<>("1", 1).hashCode(), new SolidEntry<>("1", 1).hashCode());
        assertNotEquals(new SolidEntry<>("1", 1).hashCode(), new SolidEntry<>("1", 2).hashCode());
    }

    @Test
    public void testToString() throws Exception {
        assertTrue(new SolidEntry<>("1", 1).toString().contains("1"));
    }
}