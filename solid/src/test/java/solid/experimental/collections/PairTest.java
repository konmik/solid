package solid.experimental.collections;

import org.junit.Test;

import test_utils.MockParcel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class PairTest {

    @Test
    public void testCtor() throws Exception {
        Pair<Integer, String> pair = new Pair<>(1, "2");
        assertEquals(1, pair.value1().intValue());
        assertEquals("2", pair.value2());
    }

    @Test
    public void testWriteToParcel() throws Exception {
        Pair<Integer, String> pair = new Pair<>(1, "2");
        assertEquals(pair, MockParcel.writeRead(pair, Pair.CREATOR));
    }

    @Test
    public void testEquals() throws Exception {
        Pair<Integer, String> pair = new Pair<>(1, "2");

        Pair<Integer, String> pair2 = new Pair<>(1, "2");
        assertTrue(pair.equals(pair2));
        assertEquals(pair.hashCode(), pair2.hashCode());

        Pair<Integer, String> pair3 = new Pair<>(1, "3");
        assertFalse(pair.equals(pair3));
        assertNotEquals(pair.hashCode(), pair3.hashCode());
    }
}
