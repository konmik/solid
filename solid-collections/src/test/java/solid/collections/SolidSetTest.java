package solid.collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import test_utils.MockParcel;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class SolidSetTest {

    @Test
    public void testConstructors() throws Exception {
        assert123(new SolidSet<>(new Integer[]{1, 2, 3}));
        assert123(new SolidSet<>(Arrays.asList(1, 2, 3)));
        assert123(new SolidSet<>(Arrays.asList(1, 2, 3, 3, 2, 1)));
        assert123(new SolidSet<>((Iterable<Integer>) Arrays.asList(1, 2, 3)));
        assert123(new SolidSet<>((Iterable<Integer>) Arrays.asList(1, 2, 3), 3));

        assertTrue(new SolidSet<>(Collections.singletonList(1)).contains(1));
        assertEquals(1, new SolidSet<>(Collections.singletonList(1)).size());
        assertEquals(0, new SolidSet<>(Collections.emptyList()).size());
        assertTrue(new SolidSet<>(Collections.singletonList(null)).contains(null));
    }

    @Test
    public void testContains() throws Exception {
        assertTrue(new SolidSet<>(Arrays.asList(1, 2, 3)).contains(1));
        assertFalse(new SolidSet<>(Arrays.asList(1, 2, 3)).contains(0));
        assertTrue(new SolidSet<>(Arrays.asList(1, null, 3)).contains(null));
    }

    @Test
    public void testContainsAll() throws Exception {
        assertTrue(new SolidSet<>(Arrays.asList(1, 2, 3)).containsAll(Arrays.asList(3, 2, 1)));
        assertTrue(new SolidSet<>(Arrays.asList(1, null, 3)).containsAll(Arrays.asList(null, 1, 3)));
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertFalse(new SolidSet<>(Arrays.asList(1, 2, 3)).isEmpty());
        assertTrue(new SolidSet<>(Collections.emptyList()).isEmpty());
    }

    @Test
    public void testSize() throws Exception {
        assertEquals(3, new SolidSet<>(Arrays.asList(1, 2, 3)).size());
        assertEquals(0, new SolidSet<>(Collections.emptyList()).size());
    }

    @Test
    public void testToArray() throws Exception {
        assertArrayEquals(new SolidSet<>(new Integer[]{1, 2, 3}).toArray(), new Object[]{1, 2, 3});
    }

    @Test
    public void testToArrayTyped() throws Exception {
        assertArrayEquals(new SolidSet<>(new Integer[]{1, 2, 3}).toArray(new Integer[3]), new Integer[]{1, 2, 3});
        //noinspection ToArrayCallWithZeroLengthArrayArgument
        assertArrayEquals(new SolidSet<>(new Integer[]{1, 2, 3}).toArray(new Integer[0]), new Integer[]{1, 2, 3});
    }

    @Test
    public void testIterator() throws Exception {
        ArrayList<Integer> target = new ArrayList<>();
        for (int i : new SolidSet<>(new Integer[]{1, 2, 3}))
            target.add(i);
        assert123(new SolidSet<>(target));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorImmutable() throws Exception {
        Iterator<Integer> iterator = new SolidSet<>(new Integer[]{1, 2, 3}).iterator();
        iterator.next();
        iterator.remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAdd() throws Exception {
        new SolidSet<>(new Integer[]{1, 2, 3}).add(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAll() throws Exception {
        new SolidSet<>(new Integer[]{1, 2, 3}).addAll(Arrays.asList(1, 2, 3));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testClear() throws Exception {
        new SolidSet<>(new Integer[]{1, 2, 3}).clear();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() throws Exception {
        new SolidSet<>(new Integer[]{1, 2, 3}).remove(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAll() throws Exception {
        new SolidSet<>(new Integer[]{1, 2, 3}).removeAll(Arrays.asList(1, 2, 3));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRetainAll() throws Exception {
        new SolidSet<>(new Integer[]{1, 2, 3}).retainAll(Arrays.asList(2, 3));
    }

    @Test
    public void testParcelable() throws Exception {
        SolidSet<Integer> set = new SolidSet<>(new Integer[]{1, 2, 3});
        assert123(MockParcel.writeRead(set, SolidSet.CREATOR));
        assertEquals(12, SolidSet.CREATOR.newArray(12).length);
        assertEquals(0, set.describeContents());
    }

    @Test
    public void testEmpty() throws Exception {
        assertEquals(0, SolidSet.empty().size());
    }

    @Test
    public void testToString() throws Exception {
        assertTrue(new SolidSet<>(new Integer[]{1, 13, 3}).toString().contains("13"));
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(new SolidSet<>(new Integer[]{1, 13, 3}).hashCode(), new SolidSet<>(new Integer[]{1, 13, 3}).hashCode());
        assertNotEquals(SolidSet.empty().hashCode(), new SolidSet<>(new Integer[]{1, 13, 3}).hashCode());
    }

    @Test
    public void testEquals() throws Exception {
        assertEquals(new SolidSet<>(new Integer[]{1, 13, 3}), new SolidSet<>(new Integer[]{1, 13, 3}));
        assertNotEquals(SolidSet.empty(), new SolidSet<>(new Integer[]{1, 13, 3}));
    }

    private void assert123(Set<Integer> set) throws Exception {
        assertEquals(3, set.size());
        assertTrue(set.contains(1));
        assertTrue(set.contains(2));
        assertTrue(set.contains(3));
    }
}
