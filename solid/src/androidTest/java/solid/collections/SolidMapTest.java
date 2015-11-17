package solid.collections;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Set;

import testkit.ParcelFn;

import static org.junit.Assert.assertArrayEquals;

@RunWith(AndroidJUnit4.class)
public class SolidMapTest {
    @Ignore
    @Test
    public void testParcelUnparcel() throws Exception {
        SolidMap map = new SolidMap(create123map234());
        assertSetEquals(map.entrySet(), ((SolidMap) ParcelFn.unmarshall(ParcelFn.marshall(map))).entrySet());
    }

    private <T> void assertSetEquals(Set<T> expected, Set<T> actual) {
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    private HashMap<Integer, Integer> create123map234() {
        return new HashMap<Integer, Integer>() {{
            put(1, 2);
            put(2, 3);
            put(3, 4);
        }};
    }
}
