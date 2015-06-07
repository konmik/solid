package solid.collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import test_utils.MockParcel;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotEquals;

public class SolidListTest {

    @Test
    public void testConstructors() throws Exception {
        assert123(new SolidList<>(new Integer[]{1, 2, 3}));
        assert123(new SolidList<>((Iterable<Integer>)Arrays.asList(1, 2, 3)));
        assert123(new SolidList<>(Arrays.asList(1, 2, 3)));
        assert123(SolidList.copyOf(new Integer[]{1, 2, 3}));
        assert123(SolidList.copyOf(Arrays.asList(new Integer[]{1, 2, 3})));

        SolidList<Integer> list = SolidList.single(1);
        assertEquals(1, list.size());
        assertEquals(1, (int)list.get(0));

        list = SolidList.empty();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testContains() throws Exception {
        SolidList<Integer> list1 = new SolidList<>(new Integer[]{1, 2, 3});
        assertTrue(list1.contains(1));
        assertTrue(list1.contains(2));
        assertTrue(list1.contains(3));
        assertFalse(list1.contains(4));
    }

    @Test
    public void testContainsAll() throws Exception {
        SolidList<Integer> list1 = new SolidList<>(new Integer[]{1, 2, 3});
        SolidList<Integer> list2 = new SolidList<>(new Integer[]{1, 2, 3});
        assertTrue(list1.containsAll(list2));
    }

    @Test
    public void testGet() throws Exception {
        SolidList<Integer> list1 = new SolidList<>(new Integer[]{1, 2, 3});
        assertEquals(2, (int)list1.get(1));
    }

    @Test
    public void testIndexOf() throws Exception {
        SolidList<Integer> list1 = new SolidList<>(new Integer[]{1, 2, 3});
        assertEquals(0, list1.indexOf(1));
        assertEquals(1, list1.indexOf(2));
        assertEquals(2, list1.indexOf(3));
    }

    @Test
    public void testIsEmpty() throws Exception {
        SolidList<Integer> list1 = new SolidList<>(new Integer[]{1, 2, 3});
        assertFalse(list1.isEmpty());
        assertTrue(SolidList.empty().isEmpty());
    }

    @Test
    public void testLastIndexOf() throws Exception {
        SolidList<Integer> list1 = new SolidList<>(new Integer[]{1, 2, 3, 1, 2, 3});
        assertEquals(3, list1.lastIndexOf(1));
        assertEquals(4, list1.lastIndexOf(2));
        assertEquals(5, list1.lastIndexOf(3));
    }

    @Test
    public void testSize() throws Exception {
        SolidList<Integer> list1 = new SolidList<>(new Integer[]{1, 2, 3, 1, 2, 3});
        assertEquals(6, list1.size());
        assertEquals(0, SolidList.empty().size());
    }

    @Test
    public void testSubList() throws Exception {
        SolidList<Integer> list1 = new SolidList<>(new Integer[]{0, 1, 2, 3, 1, 2, 3, 0});
        assert123(list1.subList(1, 4));
        assert123(list1.subList(4, 7));
        assertEquals(0, (int)list1.subList(0, 1).get(0));
        assertEquals(0, (int)list1.subList(7, 8).get(0));
        assertEquals(0, list1.subList(3, 3).size());
    }

    @Test
    public void testToArray() throws Exception {
        assertArrayEquals(new SolidList<>(new Integer[]{1, 2, 3}).toArray(), new Object[]{1, 2, 3});
    }

    @Test
    public void testToArrayTyped() throws Exception {
        assertArrayEquals(new SolidList<>(new Integer[]{1, 2, 3}).toArray(new Integer[3]), new Integer[]{1, 2, 3});
        //noinspection ToArrayCallWithZeroLengthArrayArgument
        assertArrayEquals(new SolidList<>(new Integer[]{1, 2, 3}).toArray(new Integer[0]), new Integer[]{1, 2, 3});
    }

    @Test
    public void testParcelable() throws Exception {
        SolidList<Integer> list1 = new SolidList<>(new Integer[]{0, 1, 2, 3, 1, 2, 3, 0});
        assertEquals(list1, MockParcel.writeRead(list1, SolidList.CREATOR));
    }

    @Test
    public void testEqualsHash() throws Exception {
        SolidList<Integer> list1 = new SolidList<>(new Integer[]{0, 1, 2, 3, 1, 2, 3, 0});

        SolidList<Integer> list2 = new SolidList<>(new Integer[]{0, 1, 2, 3, 1, 2, 3, 0});
        assertTrue(list1.equals(list2));
        assertEquals(list1.hashCode(), list2.hashCode());

        SolidList<Integer> list3 = new SolidList<>(new Integer[]{0, 1, 2, 3, 1, 2, 3, 999});
        assertFalse(list1.equals(list3));
        assertNotEquals(list1.hashCode(), list3.hashCode());

        //noinspection EqualsWithItself
        assertTrue(SolidList.empty().equals(SolidList.empty()));
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("SolidList{array=[1, 2, 3]}", new SolidList<>(new Integer[]{1, 2, 3}).toString());
    }

    @Test
    public void testIterator() throws Exception {
        ArrayList<Integer> target = new ArrayList<>();
        for (int i : new SolidList<>(new Integer[]{1, 2, 3}))
            target.add(i);
        assert123(target);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testClearThrows() throws Exception {
        new SolidList<>(new ArrayList<Integer>()).clear();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddThrows() throws Exception {
        new SolidList<>(new ArrayList<Integer>()).add(0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAdd2Throws() throws Exception {
        new SolidList<>(new ArrayList<Integer>()).add(0, 0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAllThrows() throws Exception {
        new SolidList<>(new ArrayList<Integer>()).addAll(new ArrayList<Integer>());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAll2Throws() throws Exception {
        new SolidList<>(new ArrayList<Integer>()).addAll(0, new ArrayList<Integer>());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveThrows() throws Exception {
        new SolidList<>(Collections.singletonList(0)).remove(0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveObjectThrows() throws Exception {
        new SolidList<>(Collections.singletonList(0)).remove((Integer)0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAllThrows() throws Exception {
        new SolidList<>(Collections.singletonList(0)).removeAll(Collections.singletonList(0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRetainAllThrows() throws Exception {
        new SolidList<>(Collections.singletonList(0)).retainAll(Collections.singletonList(0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSetThrows() throws Exception {
        new SolidList<>(Collections.singletonList(0)).set(0, 0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorRemoveThrows() throws Exception {
        Iterator<Integer> iterator = new SolidList<>(Collections.singletonList(0)).iterator();
        iterator.next();
        iterator.remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testListIteratorRemoveThrows() throws Exception {
        ListIterator<Integer> iterator = new SolidList<>(Collections.singletonList(0)).listIterator();
        iterator.next();
        iterator.remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testListIteratorSetThrows() throws Exception {
        ListIterator<Integer> iterator = new SolidList<>(Collections.singletonList(0)).listIterator();
        iterator.next();
        iterator.set(0);
    }

    private void assert123(List<Integer> list) throws Exception {
        assertEquals(3, list.size());
        assertEquals(1, (int)list.get(0));
        assertEquals(2, (int)list.get(1));
        assertEquals(3, (int)list.get(2));
    }
}

