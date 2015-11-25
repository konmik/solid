package solid.collectors;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import solid.collections.SolidEntry;
import solid.collections.SolidMap;
import solid.collections.SolidPair;
import solid.functions.Func1;
import solid.stream.Range;

import static testkit.AssertIterableEquals.assertIterableEquals;

@RunWith(AndroidJUnit4.class)
public class ToSolidMapTest {

    public static final SolidMap<String, Integer> MAP = new SolidMap<>(new HashMap<String, Integer>() {{
        put("1", 1);
        put("2", 2);
        put("3", 3);
    }});

    @Test
    public void testToSolidMap() throws Exception {
        assertIterableEquals(MAP, Range.range(1, 4).collect(ToSolidMap.toSolidMap(new Func1<Integer, String>() {
            @Override
            public String call(Integer it) {return "" + it;}
        }, new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer value) {
                return value;
            }
        })));
    }

    @Test
    public void testToSolidMapType() throws Exception {
        SolidMap<String, Integer> converted = Range.range(1, 4)
            .map(new Func1<Integer, Map.Entry<String, Integer>>() {
                @Override
                public Map.Entry<String, Integer> call(Integer it) {return new SolidEntry<>("" + it, it.intValue());}
            })
            .collect(ToSolidMap.<String, Integer>toSolidMap());
        assertIterableEquals(MAP, converted);
    }

    @Test
    public void testToSolidMapFromPairs() throws Exception {
        SolidMap<String, Integer> converted = Range.range(1, 4)
            .map(new Func1<Integer, SolidPair<String, Integer>>() {
                @Override
                public SolidPair<String, Integer> call(Integer it) {return new SolidPair<>("" + it, it.intValue());}
            })
            .collect(ToSolidMap.<String, Integer>pairsToSolidMap());

        assertIterableEquals(MAP, converted);
    }
}
