package solid.converters;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class ToSolidMapTest {
    @Test
    public void testToSolidList() throws Exception {
        assertEquals(create123map234(), new ToSolidMap<Integer, Integer>().call(create123map234().entrySet()));
        assertEquals(create123map234(), ToSolidMap.<Integer, Integer>toSolidMap().call(create123map234().entrySet()));
    }

    private HashMap<Integer, Integer> create123map234() {
        return new HashMap<Integer, Integer>() {{
            put(1, 2);
            put(2, 3);
            put(3, 4);
        }};
    }
}