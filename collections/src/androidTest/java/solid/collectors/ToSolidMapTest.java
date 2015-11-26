package solid.collectors;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import solid.collections.Pair;
import solid.collections.SolidMap;
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
    public void testToSolidMap2() throws Exception {
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
    public void testToSolidMap1() throws Exception {
        assertIterableEquals(MAP, Range.range(1, 4).collect(ToSolidMap.toSolidMap(new Func1<Integer, String>() {
            @Override
            public String call(Integer it) {return "" + it;}
        })));
    }

    @Test
    public void testToSolidMapType() throws Exception {
        SolidMap<String, Integer> converted = Range.range(1, 4)
            .map(new Func1<Integer, Pair<String, Integer>>() {
                @Override
                public Pair<String, Integer> call(Integer it) {return new Pair<>("" + it, it.intValue());}
            })
            .collect(ToSolidMap.<String, Integer>pairsToSolidMap());
        assertIterableEquals(MAP, converted);
    }

    @Test
    public void testToSolidMapFromPairs() throws Exception {
        SolidMap<String, Integer> converted = Range.range(1, 4)
            .map(new Func1<Integer, Pair<String, Integer>>() {
                @Override
                public Pair<String, Integer> call(Integer it) {return new Pair<>("" + it, it.intValue());}
            })
            .collect(ToSolidMap.<String, Integer>pairsToSolidMap());

        assertIterableEquals(MAP, converted);
    }
}
