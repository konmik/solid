package solid.converters;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import solid.collections.SolidEntry;
import solid.collections.SolidMap;
import solid.collections.SolidPair;
import solid.functions.Func1;
import solid.stream.Stream;

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
        assertIterableEquals(MAP, Stream.range(1, 4).collect(ToSolidMap.toSolidMap(new Func1<Long, String>() {
            @Override
            public String call(Long it) {return "" + it;}
        }, new Func1<Long, Integer>() {
            @Override
            public Integer call(Long value) {
                return value.intValue();
            }
        })));
    }

    @Test
    public void testToSolidMapType() throws Exception {
        SolidMap<String, Integer> converted = Stream.range(1, 4)
            .map(new Func1<Long, Map.Entry<String, Integer>>() {
                @Override
                public Map.Entry<String, Integer> call(Long it) {return new SolidEntry<>("" + it, it.intValue());}
            })
            .collect(ToSolidMap.<String, Integer>toSolidMap());
        assertIterableEquals(MAP, converted);
    }

    @Test
    public void testToSolidMapFromPairs() throws Exception {
        SolidMap<String, Integer> converted = Stream.range(1, 4)
            .map(new Func1<Long, SolidPair<String, Integer>>() {
                @Override
                public SolidPair<String, Integer> call(Long it) {return new SolidPair<>("" + it, it.intValue());}
            })
            .collect(ToSolidMap.<String, Integer>fromPairs());

        assertIterableEquals(MAP, converted);
    }
}
