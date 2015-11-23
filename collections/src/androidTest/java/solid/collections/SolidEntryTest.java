package solid.collections;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import testkit.ParcelFn;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SolidEntryTest {
    @Test
    public void testParcelable() throws Exception {
        SolidEntry<String, Integer> set = new SolidEntry<>("2", 2);
        assertEquals(set, ParcelFn.unmarshall(ParcelFn.marshall(set)));
    }
}