package solid.collections;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import testkit.ParcelFn;

import static org.junit.Assert.assertEquals;
import static solid.stream.Stream.of;

@RunWith(AndroidJUnit4.class)
public class SolidSetTest {
    @Test
    public void testParcelable() throws Exception {
        SolidSet<Integer> set = new SolidSet<>(Arrays.asList(1, 2, 3));
        assertEquals(set, ParcelFn.unmarshall(ParcelFn.marshall(set)));
    }
}