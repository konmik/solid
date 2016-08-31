package solid.collections;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import solid.functions.Func1;
import testkit.ParcelFn;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static solid.collections.SolidList.list;
import static solid.stream.Stream.of;

@RunWith(AndroidJUnit4.class)
public class SolidListTest {

    @Test
    public void testParcelable() throws Exception {
        SolidList<Integer> list = of(1, 2, 3).collect(new Func1<Iterable<Integer>, SolidList<Integer>>() {
            @Override
            public SolidList<Integer> call(Iterable<Integer> iterable) {
                return new SolidList<>(iterable);
            }
        });
        assertEquals(list, ParcelFn.unmarshall(ParcelFn.marshall(list)));
    }

    @Test
    public void testConstructors() throws Exception {
        assert123(new SolidList<>(new Integer[]{1, 2, 3}));
        assert123(new SolidList<>((Iterable<Integer>) Arrays.asList(1, 2, 3)));
        assert123(new SolidList<>(Arrays.asList(1, 2, 3)));

        SolidList<Integer> list = SolidList.empty();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testList() throws Exception {
        assert123(list(1, 2, 3));
        assertEquals(0, list().size());
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
        assertEquals(2, (int) list1.get(1));
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
        assertEquals(0, (int) list1.subList(0, 1).get(0));
        assertEquals(0, (int) list1.subList(7, 8).get(0));
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
    public void testEqualsHash() throws Exception {
        SolidList<Integer> list1 = new SolidList<>(new Integer[]{0, 1, 2, 3, 1, 2, 3, 0});
        SolidList<Integer> list2 = new SolidList<>(new Integer[]{0, 1, 2, 3, 1, 2, 3, 0});
        assertTrue(list1.equals(list2));
        assertEquals(list1.hashCode(), list2.hashCode());

        SolidList<Integer> list3 = new SolidList<>(new Integer[]{0, 1, 2, 3, 1, 2, 3, 999});
        assertFalse(list1.equals(list3));
        assertNotEquals(list1.hashCode(), list3.hashCode());

        List<Integer> arrayList = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 1, 2, 3, 0));
        assertTrue(list1.equals(arrayList));
        assertTrue(arrayList.equals(list1));
        assertEquals(list1.hashCode(), arrayList.hashCode());

        //noinspection EqualsWithItself
        assertTrue(SolidList.empty().equals(SolidList.empty()));
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("SolidList{list=[1, 2, 3]}", new SolidList<>(new Integer[]{1, 2, 3}).toString());
    }

    @Test
    public void testIterator() throws Exception {
        ArrayList<Integer> target = new ArrayList<>();
        for (int i : new SolidList<>(new Integer[]{1, 2, 3}))
            target.add(i);
        assert123(target);
    }

    @Test
    public void testListIteratorPrevious() throws Exception {
        ListIterator<Integer> iterator = new SolidList<>(new Integer[]{1, 2, 3}).listIterator();
        assertFalse(iterator.hasPrevious());

        iterator.next();
        assertTrue(iterator.hasPrevious());
        assertEquals(1, iterator.nextIndex());

        iterator.next();
        assertEquals(1, iterator.previousIndex());

        iterator.previous();
        assertEquals(0, iterator.previousIndex());
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
        new SolidList<>(Collections.singletonList(0)).remove((Integer) 0);
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

    @Test(expected = UnsupportedOperationException.class)
    public void testListIteratorAddThrows() throws Exception {
        ListIterator<Integer> iterator = new SolidList<>(Collections.singletonList(0)).listIterator();
        iterator.next();
        iterator.add(0);
    }

    @Test
    public void testListIterator() throws Exception {
        SolidList<Integer> list1 = new SolidList<>(new Integer[]{0, 1});
        ListIterator<Integer> iterator = list1.listIterator(1);
        assertTrue(iterator.hasNext());
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    private void assert123(List<Integer> list) throws Exception {
        assertEquals(3, list.size());
        assertEquals(1, (int) list.get(0));
        assertEquals(2, (int) list.get(1));
        assertEquals(3, (int) list.get(2));
    }
}