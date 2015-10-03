package solid.stream;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ReadOnlyIteratorTest {
    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() throws Exception {
        new ReadOnlyIterator() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Object next() {
                return null;
            }
        }.remove();
    }

    @Test
    public void testIntegration() throws Exception {
        assertTrue(Stream.of(1).iterator() instanceof ReadOnlyIterator);
    }
}